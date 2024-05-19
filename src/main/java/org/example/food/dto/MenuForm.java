package org.example.food.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class MenuForm {

    private String name;
    private int cost;
    private MenuTypeDto menuTypeDto;
    private MultipartFile image;
}

