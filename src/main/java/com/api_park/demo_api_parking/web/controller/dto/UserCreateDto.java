package com.api_park.demo_api_parking.web.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserCreateDto implements Serializable {

    @NotBlank
    @Email(message = "Formato do email inválido")
    private String userName;

    @NotBlank
    @Size(min = 6, max = 6)
    private String passWord;


}
