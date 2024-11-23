package com.api_park.demo_api_parking.web.controller;


import com.api_park.demo_api_parking.entity.ClientVaga;
import com.api_park.demo_api_parking.jwt.JwtUserDetails;
import com.api_park.demo_api_parking.repository.projection.ClienteVagaProjection;
import com.api_park.demo_api_parking.services.ClientVagaServices;
import com.api_park.demo_api_parking.services.EstacionamentoServices;
import com.api_park.demo_api_parking.web.controller.dto.EstacionamentoCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.EstacionamentoResponseDto;
import com.api_park.demo_api_parking.web.controller.dto.PageableDto;
import com.api_park.demo_api_parking.web.controller.dto.mapper.ClientVagaMapper;
import com.api_park.demo_api_parking.web.controller.dto.mapper.PageableMapper;
import com.api_park.demo_api_parking.web.controller.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estacionamentos")
public class EstacionamentoController {

    private final EstacionamentoServices estacionamentoServices;
    private final ClientVagaServices clientVagaServices;

    @Operation(
            summary = "Operação de check-in",
            description = "Recurso para dar entrada em um veiculo para o estacionamento",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Recurso criado com sucesso",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL de acesso ao recurso criado"),
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EstacionamentoResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cpf não cadastrado ou vaga indisponivel",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Dados inválidos",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acesso negado ao cliente",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDto> checkIn(@RequestBody @Valid EstacionamentoCreateDto estacionamentoCreateDto){
        ClientVaga clientVaga = ClientVagaMapper.toClientVaga(estacionamentoCreateDto);
        estacionamentoServices.checkIn(clientVaga);

        EstacionamentoResponseDto dto = ClientVagaMapper.toDto(clientVaga);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{recibo}")
                .buildAndExpand(clientVaga.getRecibo())
                .toUri();
        return ResponseEntity.created(location).body(dto);
    }

    @GetMapping("/check-in/{recibo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<EstacionamentoResponseDto> findByRecibo(@PathVariable String recibo){
        ClientVaga clientVaga = clientVagaServices.findByRecibo(recibo);
        return ResponseEntity.ok(ClientVagaMapper.toDto(clientVaga));
    }

    @PutMapping("/check-out/{recibo}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDto> checkOut(@PathVariable String recibo){
        ClientVaga clientVaga = estacionamentoServices.checkOut(recibo);
        return ResponseEntity.ok(ClientVagaMapper.toDto(clientVaga));
    }

    @GetMapping("/cpf/{cpf}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PageableDto> findAllEstacionamentoByCpf(@PathVariable String cpf,
                                                                        @PageableDefault(size = 5, sort = "dataEntrada", direction = Sort.Direction.ASC)Pageable pageable)
    {
        Page<ClienteVagaProjection> projections = clientVagaServices.findAllByCpf(cpf, pageable);
        PageableDto dto = PageableMapper.toDto2(projections);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<PageableDto> findAllEstacionamentoById(@AuthenticationPrincipal JwtUserDetails user,
                                                                               @PageableDefault(size = 5, sort = "dataEntrada", direction = Sort.Direction.ASC)Pageable pageable )
    {
        Page<ClienteVagaProjection> projections = clientVagaServices.findAllById(user.getId(), pageable);
        PageableDto dto = PageableMapper.toDto2(projections);
        return ResponseEntity.ok(dto);
    }

}
