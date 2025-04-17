package com.meetup.server.auth.presentation;

import com.meetup.server.auth.application.AuthService;
import com.meetup.server.global.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "카카오 로그인 API",
            description = "카카오 로그인을 통해 엑세스 토큰을 발급받습니다."
    )
    @PostMapping("/login/kakao")
    public ApiResponse<?> kakaoLogin(
            @RequestParam(value = "code", required = true) String code
    ) {
        String response = authService.loginWithKakao(code);
        return ApiResponse.success(response);
    }
}
