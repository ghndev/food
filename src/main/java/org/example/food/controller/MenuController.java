package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.dto.MenuForm;
import org.example.food.dto.MenuResponse;
import org.example.food.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/restaurant/my/{id}")
    public String getMenus(@PathVariable Long id, Model model) {
        model.addAttribute("restaurantId", id);
        return "menu-form";
    }

    @PostMapping("/restaurant/{id}")
    public String menuForm(@ModelAttribute MenuForm menuForm, @PathVariable Long id) throws IOException {
        menuService.save(menuForm, id);
        return "redirect:/";
    }
}
