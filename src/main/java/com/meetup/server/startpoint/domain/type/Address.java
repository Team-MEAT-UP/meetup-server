package com.meetup.server.startpoint.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Address {

    @Column(name = "address", length = 255, nullable = false)
    private String address;    //지번주소

    @Column(name = "road_address", length = 255, nullable = false)
    private String roadAddress; //도로명주소

    public static Address of(String address, String roadAddress) {
        return new Address(address, roadAddress);
    }
}
