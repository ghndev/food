package org.example.food.service;

import org.example.food.domain.Basket;

public interface BasketService {
    void addMenuToBasket(Long memberId, Long menuId, int quantity);
    Basket findByMemberId(Long memberId);
    void removeMenuFromBasket(Long memberId, Long menuId);
    int calculateTotalPrice(Long memberId);

    void save(Basket basket);
}
