package com.meetup.server.user.implement;

import com.meetup.server.user.domain.User;
import com.meetup.server.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStore {

    private final UserRepository userRepository;

    public void delete(User user) {
        userRepository.deleteUser(user);
    }
}
