package org.example.food.service;

import org.example.food.dto.RestaurantForm;

public interface RestaurantService {
    void save(RestaurantForm restaurantForm);

    void approve(Long restaurantId);
}
