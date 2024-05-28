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
    private String image;

    private String provider;
    private String providerId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restaurant> restaurants = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Basket basket;

    @Builder
    public Member(String email, String name, String image, String provider, String providerId, Role role) {
        this.email = email;
        this.name = name;
        this.image = image;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    public Member updateName(String name) {
        this.name = name;
        return this;
    }

    public void updateImage(String image) {
        this.image = image;
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }
}
