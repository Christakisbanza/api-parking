package com.api_park.demo_api_parking.web.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserPassWordDto {

    @NotBlank
    @Size(min = 6, max = 6)
    private String currentPassWord;

    @NotBlank
    @Size(min = 6, max = 6)
    private String newPassWord;

    @NotBlank
    @Size(min = 6, max = 6)
    private String confirmNewPassWord;
}
