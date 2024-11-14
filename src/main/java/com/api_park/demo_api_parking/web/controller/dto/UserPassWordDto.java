package com.api_park.demo_api_parking.web.controller.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserPassWordDto {
    private String currentPassWord;
    private String newPassWord;
    private String confirmNewPassWord;
}
