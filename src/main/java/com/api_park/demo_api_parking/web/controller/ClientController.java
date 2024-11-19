package com.api_park.demo_api_parking.web.controller;


import com.api_park.demo_api_parking.entity.Client;
import com.api_park.demo_api_parking.jwt.JwtUserDetails;
import com.api_park.demo_api_parking.services.ClientServices;
import com.api_park.demo_api_parking.services.UserService;
import com.api_park.demo_api_parking.web.controller.dto.ClientCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.ClientResponseDto;
import com.api_park.demo_api_parking.web.controller.dto.mapper.ClientMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientServices clientServices;

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> create(@RequestBody @Valid ClientCreateDto clientCreateDto, @AuthenticationPrincipal JwtUserDetails userDetails){
        Client client = ClientMapper.toClient(clientCreateDto);
        client.setUser(userService.findById(userDetails.getId()));
        clientServices.save(client);
        return ResponseEntity.status(201).body(ClientMapper.toClientResponseDto(client));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> findAll(){
        List<Client> listClient = clientServices.findAll();
        return ResponseEntity.ok().body(ClientMapper.toClientResponseDto(listClient));
    }
    
}
