package com.api_park.demo_api_parking.web.controller;


import com.api_park.demo_api_parking.entity.Vaga;
import com.api_park.demo_api_parking.services.VagaServices;
import com.api_park.demo_api_parking.web.controller.dto.VagaCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.VagaResponseDto;
import com.api_park.demo_api_parking.web.controller.dto.mapper.VagaMapper;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDto> findByCode(@PathVariable String code){
        Vaga vaga = vagaServices.findByCode(code);
        return ResponseEntity.ok(VagaMapper.toDto(vaga));
    }

}
