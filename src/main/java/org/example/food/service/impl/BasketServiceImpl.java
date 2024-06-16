package org.example.food.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.Basket;
import org.example.food.domain.BasketMenu;
import org.example.food.domain.Member;
import org.example.food.domain.Menu;
import org.example.food.repository.BasketMenuRepository;
import org.example.food.repository.BasketRepository;
import org.example.food.repository.MemberRepository;
import org.example.food.repository.MenuRepository;
import org.example.food.service.BasketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BasketMenuRepository basketMenuRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public void addMenuToBasket(Long memberId, Long menuId, int quantity) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found"));

        Basket basket = basketRepository.findByMember(member).orElse(null);
        if (basket == null) {
            basket = Basket.builder()
                    .member(member)
                    .build();
            basketRepository.save(basket);
        }

        Optional<BasketMenu> optionalBasketMenu = basketMenuRepository.findByBasketAndMenu(basket, menu);

        if (optionalBasketMenu.isPresent()) {
            BasketMenu basketMenu = optionalBasketMenu.get();
            basketMenu.setQuantity(basketMenu.getQuantity() + quantity);
        } else {
            BasketMenu basketMenu = BasketMenu.builder()
                    .basket(basket)
                    .menu(menu)
                    .quantity(quantity)
                    .build();
            basket.addBasketMenu(basketMenu);
        }

        basketRepository.save(basket);
    }

    @Override
    public Basket findByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        return basketRepository.findByMember(member).orElseThrow(() -> new RuntimeException("Basket not found"));
    }

    @Override
    @Transactional
    public void removeMenuFromBasket(Long memberId, Long menuId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found"));

        Basket basket = basketRepository.findByMember(member).orElseThrow(() -> new RuntimeException("Basket not found"));
        BasketMenu basketMenu = basketMenuRepository.findByBasketAndMenu(basket, menu).orElseThrow(() -> new RuntimeException("Basket menu not found"));

        basket.removeBasketMenu(basketMenu);
        basketMenuRepository.delete(basketMenu);

        if (basket.getBasketMenus().isEmpty()) {
            basketRepository.delete(basket);
        }
    }

    @Override
    public int calculateTotalPrice(Long memberId) {
        Basket basket = findByMemberId(memberId);
        return basket.getBasketMenus().stream()
                .mapToInt(basketMenu -> basketMenu.getMenu().getCost() * basketMenu.getQuantity())
                .sum();
    }

    @Override
    public void save(Basket basket) {
        basketRepository.save(basket);
    }
}
