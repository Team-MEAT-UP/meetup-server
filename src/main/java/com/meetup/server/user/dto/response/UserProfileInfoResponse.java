package com.meetup.server.user.dto.response;

import com.meetup.server.user.domain.User;
import lombok.Builder;

@Builder
public record UserProfileInfoResponse(
        Long userId,
        String nickname,
        String profileImageUrl,
        boolean agreement
) {
    public static UserProfileInfoResponse from(User user) {

        return UserProfileInfoResponse.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImage())
                .agreement(user.isAgreement())
                .build();
    }
}
