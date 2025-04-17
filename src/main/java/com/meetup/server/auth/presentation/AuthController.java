package com.meetup.server.auth.presentation;

import com.meetup.server.auth.application.AuthService;
import com.meetup.server.global.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login-kakao")
    public ApiResponse<?> kakaoLogin(
            @RequestParam(value = "code", required = true) String code
    ) {
        String response = authService.loginWithKakao(code);
        return ApiResponse.success(response);
    }
}
