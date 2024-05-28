package org.example.food.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.Basket;
import org.example.food.domain.Member;
import org.example.food.domain.Menu;
import org.example.food.repository.BasketRepository;
import org.example.food.repository.MemberRepository;
import org.example.food.repository.MenuRepository;
import org.example.food.service.BasketService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;

    @Override
    public void addMenuToBasket(Long memberId, Long menuId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + menuId));

        Basket basket = member.getBasket();
        if (basket == null) {
            basket = Basket.builder()
                        .member(member)
                        .build();
            basket.setMember(member);
            member.setBasket(basket);
        }

        basket.getMenus().add(menu);
        basketRepository.save(basket);
    }

    @Override
    public Basket findByMemberId(Long memberId) {
        return basketRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Basket not found for member id: " + memberId));
    }
}
