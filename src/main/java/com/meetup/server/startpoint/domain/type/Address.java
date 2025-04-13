package com.meetup.server.startpoint.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class Address {

    @Column(name = "road_name", length = 255, nullable = false)
    private String roadName;    //출발지명

    @Column(name = "road_address", length = 255, nullable = false)
    private String roadAddress; //도로명주소
}
