package com.meetup.server.auth.presentation;

import com.meetup.server.auth.application.AuthService;
import com.meetup.server.global.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Test 를 위한 Access Token 발급", description = "Swagger Test 를 위한 Access Token을 발급합니다.")
    @GetMapping("/test/{userId}")
    public String getAccessToken(@PathVariable Long userId) {
        return authService.createAccessTokenForUser(userId);
    }

    @Operation(summary = "로그아웃 API", description = "사용자 로그아웃을 진행합니다.")
    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ApiResponse.success();
    }
}
