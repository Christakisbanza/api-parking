package com.api_park.demo_api_parking.web.controller.dto.mapper;

import com.api_park.demo_api_parking.repository.projection.ClientProjection;
import com.api_park.demo_api_parking.repository.projection.ClienteVagaProjection;
import com.api_park.demo_api_parking.web.controller.dto.PageableDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

    public static PageableDto toDto(Page<ClientProjection> page){
        return new ModelMapper().map(page, PageableDto.class);
    }

    public static PageableDto toDto2(Page<ClienteVagaProjection> page){
        return new ModelMapper().map(page, PageableDto.class);
    }
}
