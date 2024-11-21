package com.api_park.demo_api_parking.web.controller;


import com.api_park.demo_api_parking.entity.Vaga;
import com.api_park.demo_api_parking.services.VagaServices;
import com.api_park.demo_api_parking.web.controller.dto.UserResponseDto;
import com.api_park.demo_api_parking.web.controller.dto.VagaCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.VagaResponseDto;
import com.api_park.demo_api_parking.web.controller.dto.mapper.VagaMapper;
import com.api_park.demo_api_parking.web.controller.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vagas")
public class VagaController {

    private final VagaServices vagaServices;

    @Operation(
            summary = "Criar uma nova vaga",
            description = "Recurso para criar uma nova vaga !",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Vaga criada com secesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class)),
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL do recurso criado")
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Vaga já cadastrada ! ",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Dados inválidos ! ",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid VagaCreateDto vagaCreateDto){
        Vaga vaga = vagaServices.save(VagaMapper.toVaga(vagaCreateDto));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(vaga.getCode())
                .toUri();
        return ResponseEntity.created(location).build();
    }



    @Operation(
            summary = "Localizar uma vaga",
            description = "Recurso para localizar uma nova vaga !",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Vaga localizada com secesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class)),
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL do recurso criado")
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Vaga não localizaada ! ",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDto> findByCode(@PathVariable String code){
        Vaga vaga = vagaServices.findByCode(code);
        return ResponseEntity.ok(VagaMapper.toDto(vaga));
    }

}
