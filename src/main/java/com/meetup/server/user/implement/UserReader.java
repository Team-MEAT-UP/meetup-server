package com.meetup.server.user.implement;

import com.meetup.server.user.domain.User;
import com.meetup.server.user.exception.UserErrorType;
import com.meetup.server.user.exception.UserException;
import com.meetup.server.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    public Optional<User> readUserIfExists(Long userId) {
        if (userId == null) return Optional.empty();
        return userRepository.findById(userId);
    }

    public User read(Long userId) {
        return readUserIfExists(userId)
                .orElseThrow(() -> new UserException(UserErrorType.USER_NOT_FOUND));
    }
}
