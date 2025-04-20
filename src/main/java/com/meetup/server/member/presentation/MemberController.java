package com.meetup.server.member.presentation;

import com.meetup.server.global.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/test")
    public ApiResponse<?> test(
            @AuthenticationPrincipal Long memberId
    ) {
        return ApiResponse.success(memberId);
    }
}
