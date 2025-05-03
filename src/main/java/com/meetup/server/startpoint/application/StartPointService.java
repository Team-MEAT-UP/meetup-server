package com.meetup.server.startpoint.application;

import com.meetup.server.global.clients.kakao.local.KakaoLocalKeywordClient;
import com.meetup.server.global.clients.kakao.local.KakaoLocalRequest;
import com.meetup.server.global.clients.kakao.local.KakaoLocalResponse;
import com.meetup.server.startpoint.exception.StartPointErrorType;
import com.meetup.server.startpoint.exception.StartPointException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StartPointService {

    private final KakaoLocalKeywordClient kakaoLocalKeywordClient;

    public KakaoLocalResponse searchStartPoint(String textQuery) {

        KakaoLocalResponse response = kakaoLocalKeywordClient.sendRequest(
                KakaoLocalRequest.builder()
                        .query(textQuery)
                        .build()
        );

        if (response == null) {
            throw new StartPointException(StartPointErrorType.PLACE_NOT_FOUND);
        }
        return response;
    }
}
