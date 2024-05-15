package org.example.food.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.food.domain.Member;
import org.example.food.domain.Restaurant;

@Getter @Setter
public class RestaurantForm {
    private String name;
    private double latitude;
    private double longitude;

    public Restaurant toEntity(Member member) {
        return Restaurant.builder()
                .name(name)
                .latitude(latitude)
                .longitude(longitude)
                .owner(member)
                .build();
    }
}
