package org.example.food.service;

import org.example.food.domain.Basket;

public interface BasketService {
    void addMenuToBasket(Long memberId, Long menuId);

    Basket findByMemberId(Long memberId);
}
