package com.api_park.demo_api_parking.web.controller;

import com.api_park.demo_api_parking.entity.User;
import com.api_park.demo_api_parking.services.UserService;
import com.api_park.demo_api_parking.web.controller.dto.UserCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.UserPassWordDto;
import com.api_park.demo_api_parking.web.controller.dto.UserResponseDto;
import com.api_park.demo_api_parking.web.controller.dto.mapper.UserMapper;
import com.api_park.demo_api_parking.web.controller.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário. ")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Criar um novo usuário",
            description = "Recurso para criar um novo usuário",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Usuario já cadastrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto userCreateDto){
        User newUser = userService.save(UserMapper.toUser(userCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserDto(newUser));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(UserMapper.toUserDto(users));
    }

    @Operation(
            summary = "Recuperar por Id",
            description = "Recurso para procurar um novo usuário",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso não encontrado ",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(UserMapper.toUserDto(user));
    }

    @Operation(
            summary = "Atualizar senha",
            description = "Atualizar senha",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Senha atualizada com secesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Senha atual não válida",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Campos inválidos o mal formatadas",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }

    )
    @PatchMapping("/{id}")
    public ResponseEntity<Void> upDatePassWord(@PathVariable Long id, @Valid @RequestBody UserPassWordDto userPassWordDto){
        User updateUserPassword = userService.upDatePassWord(id, userPassWordDto.getCurrentPassWord(), userPassWordDto.getNewPassWord(), userPassWordDto.getConfirmNewPassWord());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
