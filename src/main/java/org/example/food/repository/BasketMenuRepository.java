package org.example.food.repository;


import org.example.food.domain.Basket;
import org.example.food.domain.BasketMenu;
import org.example.food.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketMenuRepository extends JpaRepository<BasketMenu, Long> {
    Optional<BasketMenu> findByBasketAndMenu(Basket basket, Menu menu);
}
