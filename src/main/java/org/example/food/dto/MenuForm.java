package org.example.food.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.food.domain.Menu;
import org.example.food.domain.MenuType;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class MenuForm {

    private String name;
    private int cost;
    private MenuType menuType;
    private MultipartFile image;

    public Menu toEntity() {
        return Menu.builder()
                .name(name)
                .cost(cost)
                .type(menuType)
                .build();
    }
}

