package com.meetup.server.auth.presentation;

import com.meetup.server.auth.application.AuthService;
import com.meetup.server.auth.dto.response.ReissueTokenResponse;
import com.meetup.server.global.support.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "access token 재발급", description = "access token 재발급을 진행합니다.")
    @GetMapping("/reissue")
    public ApiResponse<?> reIssueToken(
            @CookieValue(name = "refreshToken") String refreshToken
    ) {
        ReissueTokenResponse reissueTokenResponse = authService.reIssueToken(refreshToken);
        return ApiResponse.success(reissueTokenResponse);
    }
}
