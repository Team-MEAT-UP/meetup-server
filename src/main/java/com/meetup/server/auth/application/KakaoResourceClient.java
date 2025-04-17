package com.meetup.server.auth.application;

import com.meetup.server.auth.dto.response.KakaoResourceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoResourceClient", url = "${oauth2.kakao.info-url}")
public interface KakaoResourceClient {

    @PostMapping(value = "/v2/user/me", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoResourceResponse getUserInfo(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("property_keys") String propertyKeysJson
    );
}
