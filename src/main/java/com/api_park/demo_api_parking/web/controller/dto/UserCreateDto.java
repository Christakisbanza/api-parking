package com.api_park.demo_api_parking.web.controller.dto;

import lombok.*;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserCreateDto implements Serializable {

    private String userName;
    private String passWord;


}
