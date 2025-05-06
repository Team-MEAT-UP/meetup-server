package com.meetup.server.subway.infrastructure.csv.mapping;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

@Getter
public class SubwayCsvMapping {

    @CsvBindByName(column = "호선")
    private int line;

    @CsvBindByName(column = "고유역번호(외부역코드)")
    private int code;

    @CsvBindByName(column = "역명")
    private String name;

    @CsvBindByName(column = "경도")
    private double longitude;

    @CsvBindByName(column = "위도")
    private double latitude;
}
