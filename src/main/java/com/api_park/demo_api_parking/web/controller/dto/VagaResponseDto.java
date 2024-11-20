package com.api_park.demo_api_parking.web.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class VagaResponseDto {

    private Long id;
    private String code;
    private String status;


}
