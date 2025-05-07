package com.meetup.server.global.clients.odsay;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("h2")
@SpringBootTest
class OdsayClientTest {

    @Autowired
    private OdsayTransitRouteSearchClient odsayTransitRouteSearchClient;

    @Disabled("API 호출 시, IP 등록이 필요하여 테스트 비활성화")
    @Test
    void 오디세이_대중교통_길찾기_조회에_성공한다() {
        // given
        OdsayTransitRouteSearchRequest odsayTransitRouteSearchRequest = OdsayTransitRouteSearchRequest.builder()
                .sx("127.03888065075954")
                .sy("37.49539510378606")
                .ex("127.01766387258931")
                .ey("37.4849124526053")
                .build();

        // when
        OdsayTransitRouteSearchResponse response = odsayTransitRouteSearchClient.sendRequest(odsayTransitRouteSearchRequest);

        // then
        assertNotNull(response);
        assertNotNull(response.data());
    }
}
