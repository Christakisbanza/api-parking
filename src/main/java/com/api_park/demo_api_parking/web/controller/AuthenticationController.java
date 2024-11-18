package com.api_park.demo_api_parking.web.controller;

import com.api_park.demo_api_parking.jwt.JwtToken;
import com.api_park.demo_api_parking.jwt.JwtUserDetailsServices;
import com.api_park.demo_api_parking.web.controller.dto.UserLoginDto;
import com.api_park.demo_api_parking.web.controller.dto.UserResponseDto;
import com.api_park.demo_api_parking.web.controller.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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


@Tag(name = "Authentication", description = "Recurso para proceder com a autenticação na API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final JwtUserDetailsServices userDetailsServices;
    private final AuthenticationManager authenticationManager;


    @Operation(
            summary = "Authentication API",
            description = "Recurso de autentifacação na API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Autenticação realizada com secesso e retorno de bearer token",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Credenciais inválidas",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Compos inválidos",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
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
