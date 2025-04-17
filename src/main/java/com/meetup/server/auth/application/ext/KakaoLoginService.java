package com.meetup.server.auth.application.ext;

import com.meetup.server.auth.dto.response.KakaoAuthResponse;
import com.meetup.server.auth.dto.response.KakaoResourceResponse;
import com.meetup.server.auth.exception.AuthErrorType;
import com.meetup.server.auth.exception.AuthException;
import com.meetup.server.member.domain.Member;
import com.meetup.server.member.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final MemberRepository memberRepository;
    private final KakaoAuthProperties kakaoAuthProperties;
    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoResourceClient kakaoResourceClient;

    public Member getToken(String code) {
        KakaoAuthResponse response = kakaoAuthClient.getTokenWithCode(

                "authorization_code",
                kakaoAuthProperties.clientId(),
                kakaoAuthProperties.redirectUri(),
                code,
                kakaoAuthProperties.clientSecret()
        );

        return getMemberInfo(response);
    }

    private Member getMemberInfo(KakaoAuthResponse response) {
        KakaoResourceResponse memberInfo = kakaoResourceClient.getUserInfo(
                "Bearer " + response.accessToken(),
                kakaoAuthProperties.propertyKeys()
        );

        if (memberInfo.kakaoAccount().email().isEmpty()) {
            throw new AuthException(AuthErrorType.NOT_INVALID_KAKAO);
        }

        return saveOrUpdateMemberInfo(memberInfo);
    }

    private Member saveOrUpdateMemberInfo(KakaoResourceResponse userInfo) {
        String userProfileImage = userInfo.kakaoAccount().profile().userProfileImage();
        String defaultProfileImage = userInfo.kakaoAccount().profile().defaultProfileImage();
        String nickname = userInfo.kakaoAccount().profile().nickname();
        String email = userInfo.kakaoAccount().email();

        String profileImage = (userProfileImage.isEmpty()) ? defaultProfileImage : userProfileImage;

        return memberRepository.findByEmail(email)
                .map(member -> {
                    member.updateProfile(nickname, profileImage);
                    return member;
                })
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .profileImage(profileImage)
                        .nickname(nickname)
                        .email(email)
                        .build()));
    }
}
