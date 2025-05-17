package com.meetup.server.user.presentation;

import com.meetup.server.global.support.response.ApiResponse;
import com.meetup.server.user.application.UserService;
import com.meetup.server.user.dto.request.UserAgreementRequest;
import com.meetup.server.user.dto.response.UserEventHistoryResponse;
import com.meetup.server.user.dto.response.UserProfileInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "User API", description = "사용자 API")
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

    @Operation(summary = "유저 약관 동의 저장", description = "유저의 개인정보 이용 동의와 마케팅 이용 동의를 저장합니다")
    @PostMapping("/agreement")
    public ApiResponse<?> saveUserAgreement(
            @AuthenticationPrincipal Long userId,
            @RequestBody UserAgreementRequest request
    ) {
        userService.saveUserAgreement(userId, request.isPersonalInfoAgreement(), request.isMarketingAgreement());
        return ApiResponse.success();
    }

    @Operation(summary = "유저 참여 모임 리스트 조회", description = "유저가 참여한 모임 리스트를 조회합니다")
    @GetMapping("/users/{userId}/events")
    public ApiResponse<List<UserEventHistoryResponse>> getUserEventHistory(
            @AuthenticationPrincipal Long userId
    ) {
        List<UserEventHistoryResponse> response = userService.getUserEventHistory(userId);
        return ApiResponse.success(response);
    }
}
