package com.api_park.demo_api_parking.web.controller.dto.mapper;


import com.api_park.demo_api_parking.entity.Client;
import com.api_park.demo_api_parking.web.controller.dto.ClientCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.ClientResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {


    public static Client toClient(ClientCreateDto clientCreateDto){
        return new ModelMapper().map(clientCreateDto, Client.class);
    }

    public static ClientResponseDto toClientResponseDto(Client client){
        return new ModelMapper().map(client, ClientResponseDto.class);
    }

    public static List<ClientResponseDto> toClientResponseDto(List<Client> clients){
        return clients.stream()
                .map(client -> new ModelMapper().map(client, ClientResponseDto.class))
                .toList();
    }




}
