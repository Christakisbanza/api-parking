package com.api_park.demo_api_parking.web.controller.dto;


import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ClientResponseDto {

    private Long id;
    private String name;
    private String cpf;

}
