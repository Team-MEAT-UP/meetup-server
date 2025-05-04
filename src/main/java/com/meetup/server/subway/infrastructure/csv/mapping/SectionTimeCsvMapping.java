package com.meetup.server.subway.infrastructure.csv.mapping;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

@Getter
public class SectionTimeCsvMapping {

        @CsvBindByName(column = "호선")
        private int line;

        @CsvBindByName(column = "역명")
        private String name;

        @CsvBindByName(column = "소요시간")
        private String sectionTime;
}
