package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.dto.RestaurantForm;
import org.example.food.service.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/register")
    public String restaurantForm() {
        return "restaurant-form";
    }

    @ResponseBody
    @PostMapping("/register")
    public String restaurantRegister(@RequestBody RestaurantForm restaurantForm) {
        restaurantService.save(restaurantForm);
        return "register success";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id) {
        restaurantService.approve(id);
        return "notification";
    }
}
