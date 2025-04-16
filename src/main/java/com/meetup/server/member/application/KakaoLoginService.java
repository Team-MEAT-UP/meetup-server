package com.meetup.server.member.application;

import com.meetup.server.member.domain.Member;
import com.meetup.server.member.dto.response.KakaoLoginInfoResponse;
import com.meetup.server.member.dto.response.KakaoLoginResponse;
import com.meetup.server.member.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final MemberRepository memberRepository;
    private final KakaoProperties kakaoProperties;
    private final KakaoLoginClient kakaoLoginClient;
    private final KakaoUserClient kakaoUserClient;

    public void kakaoLogin(String code) {

        KakaoLoginResponse response = kakaoLoginClient.getTokenFromCode(

                "authorization_code",
                kakaoProperties.clientId(),
                kakaoProperties.redirectUri(),
                code,
                kakaoProperties.clientSecret()
        );

        saveMemberInfo(response);
    }

    public void saveMemberInfo(KakaoLoginResponse response) {
        KakaoLoginInfoResponse userInfo = kakaoUserClient.getUserInfo(
                "Bearer " + response.accessToken(),
                "[\"kakao_account.profile\"]"
        );

        String userProfileImage = userInfo.kakaoAccount().profile().userProfileImage();
        String profileImage = (userProfileImage != null) ? userProfileImage : userInfo.kakaoAccount().profile().defaultProfileImage();

        memberRepository.save(Member.builder()
                .profileImage(profileImage)
                .nickname(userInfo.kakaoAccount().profile().nickname())
                .build());
    }
}
