package com.meetup.server.member.domain;

import com.meetup.server.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "nickname", length = 255, nullable = false)
    private String nickname;

    @Column(name = "profile_image", length = 255, nullable = true)
    private String profileImage;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Builder
    public Member(String nickname, String profileImage, String email) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.email = email;
    }

    public void updateProfile(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}
