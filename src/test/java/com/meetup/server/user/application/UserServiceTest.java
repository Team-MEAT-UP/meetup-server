package com.meetup.server.user.application;


import com.meetup.server.fixture.UserFixture;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.dto.response.UserProfileInfoResponse;
import com.meetup.server.user.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("h2")
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(UserFixture.getUser());
    }

    @Test
    void 사용자_정보를_조회한다() {
        Long userId = user.getUserId();

        UserProfileInfoResponse userProfileInfoResponse = userRepository.findById(userId)
                .map(UserProfileInfoResponse::from)
                .orElseThrow(() -> new RuntimeException("User not found"));

        assertThat(userProfileInfoResponse).isNotNull();
        assertThat(userProfileInfoResponse.userId()).isEqualTo(user.getUserId());
        assertThat(userProfileInfoResponse.nickname()).isEqualTo(user.getNickname());
    }
}

