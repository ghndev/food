package org.example.food.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private String imageUrl;

    private String provider;
    private String providerId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email, String name, String imageUrl, String provider, String providerId, Role role) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    public Member updateName(String name) {
        this.name = name;
        return this;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
