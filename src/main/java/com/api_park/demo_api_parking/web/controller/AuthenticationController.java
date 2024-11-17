package com.api_park.demo_api_parking.web.controller;

import com.api_park.demo_api_parking.jwt.JwtToken;
import com.api_park.demo_api_parking.jwt.JwtUserDetailsServices;
import com.api_park.demo_api_parking.web.controller.dto.UserLoginDto;
import com.api_park.demo_api_parking.web.controller.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final JwtUserDetailsServices userDetailsServices;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/auth")
    public ResponseEntity<?> authentication(@RequestBody @Valid UserLoginDto userLoginDto, HttpServletRequest request){
        log.info("Processo de autentificação pelo login {}", userLoginDto.getUserName());
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginDto.getUserName(), userLoginDto.getPassWord());

            authenticationManager.authenticate(authenticationToken);

            JwtToken token = userDetailsServices.getTokenAuthenticated(userLoginDto.getUserName());

            return ResponseEntity.ok().body(token);
        }
        catch (AuthenticationException e ){
            log.warn("Bad credentials from username {}", userLoginDto.getUserName());
        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Bad credentials"));
    }
}
