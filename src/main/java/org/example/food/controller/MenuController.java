package org.example.food.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.domain.MenuType;
import org.example.food.dto.MenuForm;
import org.example.food.dto.MenuResponse;
import org.example.food.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/restaurant/{id}")
    public String menuForm(@ModelAttribute MenuForm menuForm, @PathVariable Long id) throws IOException {
        menuService.save(menuForm, id);
        return "redirect:/";
    }

    @GetMapping("/burger")
    public String burger(Model model) {
        List<MenuResponse> menus = menuService.findMenusByType(MenuType.HAMBURGER);
        model.addAttribute("menus", menus);
        return "menu/burger";
    }

    @GetMapping("/{id}")
    public String menu(@PathVariable Long id, Model model) {
        MenuResponse menuResponse = menuService.findById(id);
        model.addAttribute("menuResponse", menuResponse);
        return "menu-detail";
    }
}
