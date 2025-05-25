package com.meetup.server.user.persistence;

import com.meetup.server.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {

    Optional<User> findBySocialId(String socialId);
}
