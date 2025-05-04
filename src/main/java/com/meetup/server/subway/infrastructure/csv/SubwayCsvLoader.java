package com.meetup.server.subway.infrastructure.csv;

import com.meetup.server.startpoint.domain.type.Location;
import com.meetup.server.subway.domain.Subway;
import com.meetup.server.subway.domain.SubwayConnection;
import com.meetup.server.subway.domain.TransferInfo;
import com.meetup.server.subway.infrastructure.csv.mapping.SectionTimeCsvMapping;
import com.meetup.server.subway.infrastructure.csv.mapping.SubwayCsvMapping;
import com.meetup.server.subway.infrastructure.csv.mapping.TransferInfoMapping;
import com.meetup.server.subway.persistence.SubwayRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubwayCsvLoader {

    private final SubwayRepository subwayRepository;

    private List<Subway> parseSubwayCsv() throws IOException {
        ClassPathResource resource = new ClassPathResource("csv/서울교통공사_1_8호선 역사 좌표(위경도) 정보_20241031.csv");

        try (Reader reader = Files.newBufferedReader(resource.getFile().toPath(), Charset.forName("EUC-KR"))) {
            HeaderColumnNameMappingStrategy<SubwayCsvMapping> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(SubwayCsvMapping.class);

            CsvToBean<SubwayCsvMapping> csvToBean = new CsvToBeanBuilder<SubwayCsvMapping>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> Subway.builder()
                            .name(csvLine.getName())
                            .code(csvLine.getCode())
                            .line(csvLine.getLine())
                            .location(Location.of(csvLine.getLongitude(), csvLine.getLatitude()))
                            .build()
                    ).toList();
        }
    }

    private List<SubwayConnection> parseSectionTimeCsv() throws IOException {
        ClassPathResource resource = new ClassPathResource("csv/서울교통공사 역간거리 및 소요시간_240810.csv");

        try (Reader reader = Files.newBufferedReader(resource.getFile().toPath(), Charset.forName("EUC-KR"))) {
            HeaderColumnNameMappingStrategy<SectionTimeCsvMapping> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(SectionTimeCsvMapping.class);

            CsvToBean<SectionTimeCsvMapping> csvToBean = new CsvToBeanBuilder<SectionTimeCsvMapping>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<SectionTimeCsvMapping> records = csvToBean.parse();

            Map<Integer, List<SectionTimeCsvMapping>> groupedByLine = records.stream()
                    .collect(Collectors.groupingBy(SectionTimeCsvMapping::getLine, LinkedHashMap::new, Collectors.toList()));

            List<SubwayConnection> connections = new ArrayList<>();

            for (Map.Entry<Integer, List<SectionTimeCsvMapping>> entry : groupedByLine.entrySet()) {
                int line = entry.getKey();
                List<SectionTimeCsvMapping> stations = entry.getValue();

                for (int i = 0; i < stations.size() - 1; i++) {
                    SectionTimeCsvMapping from = stations.get(i);
                    SectionTimeCsvMapping to = stations.get(i + 1);

                    Subway fromSubway = subwayRepository.findByNameAndLine(from.getName(), line).orElse(null);
                    Subway toSubway = subwayRepository.findByNameAndLine(to.getName(), line).orElse(null);

                    if (fromSubway == null || toSubway == null) {
                        log.warn("지하철 정보 없음 - from: {} / to: {} / line: {}", from.getName(), to.getName(), line);
                        continue;
                    }

                    int sectionTimeSec = parseTimeToSeconds(to.getSectionTime());

                    SubwayConnection connectionAB = SubwayConnection.builder()
                            .fromSubway(fromSubway)
                            .toSubway(toSubway)
                            .line(line)
                            .sectionTimeSec(sectionTimeSec)
                            .build();
                    connections.add(connectionAB);

                    SubwayConnection connectionBA = SubwayConnection.builder()
                            .fromSubway(toSubway)
                            .toSubway(fromSubway)
                            .line(line)
                            .sectionTimeSec(sectionTimeSec)
                            .build();
                    connections.add(connectionBA);
                }
            }

            return connections;
        }
    }

    private List<TransferInfo> parseTransferInfoCsv() throws IOException {
        ClassPathResource resource = new ClassPathResource("csv/서울교통공사_서울 도시철도 환승정보_20250319.csv");

        try (Reader reader = Files.newBufferedReader(resource.getFile().toPath(), Charset.forName("EUC-KR"))) {
            HeaderColumnNameMappingStrategy<TransferInfoMapping> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(TransferInfoMapping.class);

            CsvToBean<TransferInfoMapping> csvToBean = new CsvToBeanBuilder<TransferInfoMapping>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<TransferInfoMapping> transferInfoMappings = csvToBean.parse();

            Set<String> existingTransferKeys = new HashSet<>();
            List<TransferInfo> transferInfos = new ArrayList<>();

            for (TransferInfoMapping mapping : transferInfoMappings) {
                int fromCode, toCode;
                try {
                    fromCode = Integer.parseInt(mapping.getFromCode());
                    toCode = Integer.parseInt(mapping.getToCode());
                } catch (NumberFormatException e) {
                    log.warn("숫자 변환 실패 - 무시된 행: {}", mapping);
                    continue;
                }

                Subway fromSubway = subwayRepository.findByCode(fromCode).orElse(null);
                Subway toSubway = subwayRepository.findByCode(toCode).orElse(null);

                if (fromSubway == null || toSubway == null) {
                    log.warn("지하철 정보 없음 - from: {} (code: {}, line: {}) / to: {} (code: {}, line: {})",
                            mapping.getFromName(), mapping.getFromCode(), mapping.getFromLine(),
                            mapping.getToName(), mapping.getToCode(), mapping.getToLine());
                    continue;
                }

                String key = fromSubway.getSubwayId() + "-" + toSubway.getSubwayId();
                if (existingTransferKeys.contains(key)) {
                    log.info("중복 환승 정보 무시됨 - from: {} / to: {}", fromSubway.getSubwayId(), toSubway.getSubwayId());
                    continue;
                }

                existingTransferKeys.add(key);

                TransferInfo transferInfo = TransferInfo.builder()
                        .fromSubway(fromSubway)
                        .toSubway(toSubway)
                        .build();

                transferInfos.add(transferInfo);
            }

            return transferInfos;
        }
    }

    private int parseTimeToSeconds(String timeStr) {
        if (timeStr == null || timeStr.isBlank()) return 0;

        String[] parts = timeStr.split(":");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        return minutes * 60 + seconds;
    }
}
