package com.meetup.server.global.clients.kakao.mobility;

import com.meetup.server.support.IntegrationTestContainer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("API 호출 시, 과금 가능성으로 인한 테스트 비활성화")
class KakaoMobilityClientTest extends IntegrationTestContainer {

    @Autowired
    private KakaoMobilityClient kakaoMobilityClient;

    @Test
    void 카카오_자동차_길찾기_조회에_성공한다() {
        // given
        KakaoMobilityRequest request = KakaoMobilityRequest.builder()
                .origin("127.10764191124568,37.402464820205246")
                .destination("127.11056336672839,37.39419693653072")
                .build();

        // when
        KakaoMobilityResponse response = kakaoMobilityClient.sendRequest(request);

        // then
        assertNotNull(response);
        assertNotNull(response.transId());
    }
}
