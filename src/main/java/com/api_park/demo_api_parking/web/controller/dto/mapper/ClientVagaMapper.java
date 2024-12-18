package com.api_park.demo_api_parking.web.controller.dto.mapper;


import com.api_park.demo_api_parking.entity.ClientVaga;
import com.api_park.demo_api_parking.web.controller.dto.EstacionamentoCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.EstacionamentoResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientVagaMapper {

    public static ClientVaga toClientVaga(EstacionamentoCreateDto estacionamentoCreateDto){
        return new ModelMapper().map(estacionamentoCreateDto, ClientVaga.class);
    }

    public static EstacionamentoResponseDto toDto(ClientVaga clientVaga){
        return new ModelMapper().map(clientVaga, EstacionamentoResponseDto.class);
    }
}
