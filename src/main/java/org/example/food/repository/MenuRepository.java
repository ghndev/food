package org.example.food.repository;

import org.example.food.domain.Menu;
import org.example.food.domain.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByRestaurantId(Long restaurantId);

    List<Menu> findByType(MenuType type);
}
