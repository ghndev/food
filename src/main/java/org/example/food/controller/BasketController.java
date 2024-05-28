package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.Basket;
import org.example.food.security.PrincipalDetails;
import org.example.food.service.BasketService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/{menuId}")
    public String add(@PathVariable Long menuId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        basketService.addMenuToBasket(principalDetails.getMember().getId(), menuId);
        return "/";
    }

    @GetMapping
    public String viewBasket(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Basket basket = basketService.findByMemberId(principalDetails.getMember().getId());
        model.addAttribute("basket", basket);
        return "basket";
    }
}
