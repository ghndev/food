package org.example.food.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double latitude;
    private double longitude;

    private boolean approved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Member owner;

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
}
