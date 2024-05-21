package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.dto.RestaurantForm;
import org.example.food.dto.RestaurantResponse;
import org.example.food.security.PrincipalDetails;
import org.example.food.service.RestaurantService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/register")
    public String register() {
        return "restaurant-form";
    }

    @ResponseBody
    @PostMapping("/register")
    public String restaurantForm(@RequestBody RestaurantForm restaurantForm) {
        restaurantService.save(restaurantForm);
        return "register success";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        restaurantService.approve(id);
        return "redirect:/notification";
    }

    @GetMapping("/my")
    public String myRestaurants(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        List<RestaurantResponse> restaurantResponses = restaurantService.findByOwnerId(principalDetails.getMember().getId());
        model.addAttribute("restaurants", restaurantResponses);
        return "my-restaurants";
    }
}
