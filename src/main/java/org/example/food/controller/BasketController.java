package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.Basket;
import org.example.food.security.PrincipalDetails;
import org.example.food.service.BasketService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    @GetMapping
    public String viewBasket(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Long memberId = principalDetails.getMember().getId();
        Basket basket = basketService.findByMemberId(memberId);
        int totalPrice = basketService.calculateTotalPrice(memberId);
        model.addAttribute("basket", basket);
        model.addAttribute("totalPrice", totalPrice);
        return "basket";
    }
}
