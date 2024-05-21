package org.example.food.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String image;
    private int cost;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private MenuType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Builder
    public Menu(MenuType type, String name, int cost, int quantity) {
        this.type = type;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
    }

    public void setRestaurant(Restaurant restaurant) {
        if (this.restaurant != null) {
            this.restaurant.getMenus().remove(this);
        }

        this.restaurant = restaurant;
        restaurant.getMenus().add(this);
    }

    public void updateImage(String image) {
        this.image = image;
    }
}
