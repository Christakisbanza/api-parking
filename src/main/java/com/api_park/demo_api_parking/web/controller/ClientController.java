package com.api_park.demo_api_parking.web.controller;


import com.api_park.demo_api_parking.entity.Client;
import com.api_park.demo_api_parking.jwt.JwtUserDetails;
import com.api_park.demo_api_parking.repository.projection.ClientProjection;
import com.api_park.demo_api_parking.services.ClientServices;
import com.api_park.demo_api_parking.services.UserService;
import com.api_park.demo_api_parking.web.controller.dto.ClientCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.ClientResponseDto;
import com.api_park.demo_api_parking.web.controller.dto.PageableDto;
import com.api_park.demo_api_parking.web.controller.dto.mapper.ClientMapper;
import com.api_park.demo_api_parking.web.controller.dto.mapper.PageableMapper;
import com.api_park.demo_api_parking.web.controller.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Clientes", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um Cliente. ")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientServices clientServices;

    private final UserService userService;

    @Operation(
            summary = "Criar um novo Cliente",
            description = "Recurso para criar um novo Cliente",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Cliente já cadastrado",
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
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> create(@RequestBody @Valid ClientCreateDto clientCreateDto, @AuthenticationPrincipal JwtUserDetails userDetails){
        Client client = ClientMapper.toClient(clientCreateDto);
        client.setUser(userService.findById(userDetails.getId()));
        clientServices.save(client);
        return ResponseEntity.status(201).body(ClientMapper.toClientResponseDto(client));
    }


    @Operation(
            summary = "Recuperar lista de clientes",
            description = "Recurso para listar todos os Cliente",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acesso negado ao Cliente",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> findAll(Pageable pageable){
        Page<ClientProjection> clients = clientServices.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(clients));
    }


    @Operation(
            summary = "Localizar um Cliente",
            description = "Recurso para localizar um Cliente",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente não encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Recursos não permitidos ao perfil de Cliente",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDto> findById(@PathVariable Long id){
        Client client = clientServices.findById(id);
        return ResponseEntity.ok().body(ClientMapper.toClientResponseDto(client));
    }


    @GetMapping("/details")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> getDetails(@AuthenticationPrincipal JwtUserDetails userDetails){
        Client client = clientServices.findUserId(userDetails.getId());
        return ResponseEntity.ok(ClientMapper.toClientResponseDto(client));
    }

}
