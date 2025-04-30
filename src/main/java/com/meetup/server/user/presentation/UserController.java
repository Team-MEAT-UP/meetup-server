package com.meetup.server.user.presentation;

import com.meetup.server.global.support.response.ApiResponse;
import com.meetup.server.user.application.UserService;
import com.meetup.server.user.dto.response.UserProfileInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "User API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 프로필 정보", description = "유저의 프로필 정보를 조회합니다")
    @GetMapping("")
    public ApiResponse<?> getUserProfileInfo(
            @AuthenticationPrincipal Long userId
    ) {
        UserProfileInfoResponse response = userService.getUserProfileInfo(userId);
        return ApiResponse.success(response);
    }
}
