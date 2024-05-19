package org.example.food.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restaurant> restaurants = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

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

    public void updateRole(Role role) {
        this.role = role;
    }
}
