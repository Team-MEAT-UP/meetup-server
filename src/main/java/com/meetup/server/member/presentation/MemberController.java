package com.meetup.server.member.presentation;

import com.meetup.server.global.support.response.ApiResponse;
import com.meetup.server.global.support.response.ResultType;
import com.meetup.server.member.application.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/login-kakao")
    public ApiResponse<?> kakaoLogin(
            @RequestParam(value = "code", required = true) String code
    ) {
        kakaoLoginService.kakaoLogin(code);
        return ApiResponse.success(ResultType.SUCCESS);
    }
}
