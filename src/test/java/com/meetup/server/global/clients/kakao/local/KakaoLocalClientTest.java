package com.meetup.server.global.clients.kakao.local;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class KakaoLocalClientTest {

    @Autowired
    private KakaoLocalCategoryClient kakaoLocalCategoryClient;

    @Autowired
    private KakaoLocalKeywordClient kakaoLocalKeywordClient;

    @Test
    void 카카오_카테고리_조회에_성공한다() {
        // given
        KakaoLocalRequest request = KakaoLocalRequest.builder()
                .categoryGroupCode(CategoryGroupCode.PM9)
                .radius(20000)
                .build();

        // when
        KakaoLocalResponse response = kakaoLocalCategoryClient.sendRequest(request);

        // then
        assertNotNull(response);
        assertTrue(response.kakaoSearchResponses().length > 0);
    }

    @Test
    void 카카오_키워드_조회에_성공한다() {
        // given
        KakaoLocalRequest request = KakaoLocalRequest.builder()
                .query("카카오프렌즈")
                .x("127.06283102249932")
                .y("37.514322572335935")
                .build();

        // when
        KakaoLocalResponse response = kakaoLocalKeywordClient.sendRequest(request);

        // then
        assertNotNull(response);
        assertTrue(response.kakaoSearchResponses().length > 0);
    }
}
