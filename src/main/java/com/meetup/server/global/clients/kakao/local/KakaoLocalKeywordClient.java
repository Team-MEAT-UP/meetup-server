package com.meetup.server.global.clients.kakao.local;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KakaoLocalKeywordClient {

    private final WebClient kakaoLocalWebClient;

    public KakaoLocalResponse sendRequest(KakaoLocalRequest request) {
        return kakaoLocalWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/keyword.json")
                        .queryParam("query", request.query())
                        .queryParamIfPresent("category_group_code", Optional.ofNullable(request.categoryGroupCode()))
                        .queryParamIfPresent("x", Optional.ofNullable(request.x()))
                        .queryParamIfPresent("y", Optional.ofNullable(request.y()))
                        .queryParamIfPresent("radius", Optional.ofNullable(request.radius()))
                        .queryParamIfPresent("rect", Optional.ofNullable(request.rect()))
                        .queryParamIfPresent("page", Optional.ofNullable(request.page()))
                        .queryParamIfPresent("size", Optional.ofNullable(request.size()))
                        .queryParamIfPresent("sort", Optional.ofNullable(request.sort()))
                        .build())
                .retrieve()
                .bodyToMono(KakaoLocalResponse.class)
                .block();
    }
}
