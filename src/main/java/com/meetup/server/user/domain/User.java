package com.meetup.server.user.domain;

import com.meetup.server.global.domain.BaseEntity;
import com.meetup.server.user.domain.type.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "nickname", length = 255, nullable = false)
    private String nickname;

    @Column(name = "profile_image", length = 255, nullable = true)
    private String profileImage;

    @Column(name = "social_id", length = 255, nullable = false, unique = true)
    private String socialId;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "agreement", nullable = false)
    private boolean agreement;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 10, nullable = false)
    private Role role;


    @Builder
    public User(String nickname, String profileImage, String email, String socialId, Role role, boolean agreement) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.email = email;
        this.socialId = socialId;
        this.agreement = agreement;
        this.role = role;
    }
}
