package com.meetup.server.subway.infrastructure.csv.mapping;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

@Getter
public class TransferInfoMapping {

    @CsvBindByName(column = "환승시작역")
    private String fromName;

    @CsvBindByName(column = "환승시작 코드")
    private String fromCode;

    @CsvBindByName(column = "환승시작 호선")
    private String fromLine;

    @CsvBindByName(column = "환승종료역")
    private String toName;

    @CsvBindByName(column = "환승종료역 코드")
    private String toCode;

    @CsvBindByName(column = "환승종료 호선")
    private String toLine;
}
