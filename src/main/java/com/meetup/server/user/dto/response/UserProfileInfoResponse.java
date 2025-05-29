package com.meetup.server.user.dto.response;

import com.meetup.server.user.domain.User;
import lombok.Builder;

@Builder
public record UserProfileInfoResponse(
        Long userId,
        String nickname,
        String profileImageUrl,
        String email,
        boolean personalInfoAgreement,
        boolean marketingAgreement
) {
    public static UserProfileInfoResponse from(User user) {

        return UserProfileInfoResponse.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImage())
                .email(user.getEmail())
                .personalInfoAgreement(user.isPersonalInfoAgreement())
                .marketingAgreement(user.isMarketingAgreement())
                .build();
    }
}
