package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.Order;
import org.example.food.security.PrincipalDetails;
import org.example.food.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String getAllOrders(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long restaurantId = principalDetails.getMember().getRestaurants().get(0).getId();
        List<Order> orders = orderService.getPendingOrdersByRestaurantId(restaurantId);
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/complete/{id}")
    public String completeOrder(@PathVariable Long id) {
        orderService.completeOrder(id);
        return "redirect:/orders";
    }
}