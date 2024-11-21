package com.api_park.demo_api_parking.web.controller;


import com.api_park.demo_api_parking.entity.ClientVaga;
import com.api_park.demo_api_parking.services.EstacionamentoServices;
import com.api_park.demo_api_parking.web.controller.dto.EstacionamentoCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.EstacionamentoResponseDto;
import com.api_park.demo_api_parking.web.controller.dto.mapper.ClientVagaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estacionamentos")
public class EstacionamentoController {

    private final EstacionamentoServices estacionamentoServices;


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
}