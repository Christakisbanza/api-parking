package com.api_park.demo_api_parking.web.controller.dto;


import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserResponseDto {
    private Long id;
    private String userName;
    private String role;
}
