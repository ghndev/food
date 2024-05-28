package org.example.food.service;

import org.example.food.dto.RestaurantEditForm;
import org.example.food.dto.RestaurantForm;
import org.example.food.dto.RestaurantResponse;

import java.util.List;

public interface RestaurantService {
    void save(RestaurantForm restaurantForm);

    void approve(Long restaurantId);

    List<RestaurantResponse> findByOwnerId(Long ownerId);

    RestaurantResponse findById(Long id);

    void updateRestaurant(Long restaurantId, RestaurantEditForm restaurantEditForm);
}
