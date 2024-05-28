package org.example.food.service;

import org.example.food.domain.MenuType;
import org.example.food.dto.MenuForm;
import org.example.food.dto.MenuResponse;

import java.io.IOException;
import java.util.List;

public interface MenuService {
    void save(MenuForm menuForm, Long restaurantId) throws IOException;
    List<MenuResponse> findMenusByRestaurantId(Long restaurantId);

    List<MenuResponse> findMenusByType(MenuType type);

    MenuResponse findById(Long id);
}
