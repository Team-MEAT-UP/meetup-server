package com.meetup.server.user.persistence;

import com.meetup.server.user.domain.User;

public interface UserCustomRepository {

    void deleteUser(User user);
}
