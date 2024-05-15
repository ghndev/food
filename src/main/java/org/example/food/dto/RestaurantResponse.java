package org.example.food.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.food.domain.Restaurant;

@Getter
@Builder
public class RestaurantResponse {

    private Long id;

    private String name;

    private double latitude;
    private double longitude;

    private boolean approved;

    public static RestaurantResponse of(Restaurant restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .latitude(restaurant.getLatitude())
                .longitude(restaurant.getLongitude())
                .approved(restaurant.isApproved())
                .build();
    }
}
