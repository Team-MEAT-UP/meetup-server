package com.meetup.server.subway.infrastructure.csv;

import com.meetup.server.startpoint.domain.type.Location;
import com.meetup.server.subway.domain.Subway;
import com.meetup.server.subway.infrastructure.csv.mapping.SubwayCsvMapping;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

@Component
public class SubwayCsvLoader {

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
}
