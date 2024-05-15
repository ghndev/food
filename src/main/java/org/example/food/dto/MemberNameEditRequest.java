package org.example.food.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
public class MemberNameEditRequest {

    @NotBlank
    @Length(max = 20, min = 1)
    String memberName;
}
