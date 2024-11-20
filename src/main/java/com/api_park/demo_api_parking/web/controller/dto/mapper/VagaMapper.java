package com.api_park.demo_api_parking.web.controller.dto.mapper;


import com.api_park.demo_api_parking.entity.Vaga;
import com.api_park.demo_api_parking.web.controller.dto.VagaCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.VagaResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVaga(VagaCreateDto vagaCreateDto){
        return new ModelMapper().map(vagaCreateDto, Vaga.class);
    }

    public static VagaResponseDto toDto(Vaga vaga){
        return new ModelMapper().map(vaga, VagaResponseDto.class);
    }

}
