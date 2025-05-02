package com.meetup.server.user.implement;

import com.meetup.server.fixture.UserFixture;
import com.meetup.server.user.domain.User;
import com.meetup.server.user.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserReaderTest {

    @Autowired
    private UserReader userReader;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(UserFixture.getUser());
    }

    @Test
    void 비로그인_사용자를_조회한다() {
        Optional<User> nonLoginUser = userReader.readUserIfExists(null);
        assertThat(nonLoginUser).isEmpty();
    }

    @Test
    void 로그인_사용자를_조회한다() {
        Optional<User> loginUser = userReader.readUserIfExists(user.getUserId());
        assertThat(loginUser).isPresent();
        assertThat(loginUser.get().getUserId()).isEqualTo(user.getUserId());
    }
}
