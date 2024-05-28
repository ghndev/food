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
public class Restaurant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int tables = 0;

    private double latitude;
    private double longitude;

    private boolean approved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Member owner;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @Builder
    public Restaurant(String name, double latitude, double longitude, Member owner) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.approved = false;

        if (this.owner != null) {
            this.owner.getRestaurants().remove(this);
        }

        this.owner = owner;
        owner.getRestaurants().add(this);
    }

    public void setApproved(boolean b) {
        this.approved = b;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateTables(int tables) {
        this.tables = tables;
    }
}
