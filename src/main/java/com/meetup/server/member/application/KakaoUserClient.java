package com.meetup.server.member.application;

import com.meetup.server.member.dto.response.KakaoLoginInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoUserClient", url = "${oauth2.kakao.info-url}")
public interface KakaoUserClient {

    @PostMapping(value = "/v2/user/me", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoLoginInfoResponse getUserInfo(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("property_keys") String propertyKeysJson
    );
}
