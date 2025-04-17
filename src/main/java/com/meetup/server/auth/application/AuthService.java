package com.meetup.server.auth.application;

import com.meetup.server.global.support.jwt.JwtTokenProvider;
import com.meetup.server.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoLoginService kakaoLoginService;
    private final JwtTokenProvider jwtTokenProvider;

    public String loginWithKakao(String code) {
        Member member = kakaoLoginService.getToken(code);

        return jwtTokenProvider.createAccessToken(member);
    }
}

