package com.api_park.demo_api_parking;


import com.api_park.demo_api_parking.jwt.JwtToken;
import com.api_park.demo_api_parking.web.controller.dto.UserLoginDto;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String userName, String password){
        String token = client
                .post()
                .uri("/api/v1/auth")
                .bodyValue(new UserLoginDto(userName, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody().getToken();
        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer" + token);
    }
}
