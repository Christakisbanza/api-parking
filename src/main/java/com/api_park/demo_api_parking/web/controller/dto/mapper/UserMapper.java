package com.api_park.demo_api_parking.web.controller.dto.mapper;

import com.api_park.demo_api_parking.entity.User;
import com.api_park.demo_api_parking.web.controller.dto.UserCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;


public class UserMapper {

    public static User toUser(UserCreateDto userCreateDto){
        return new ModelMapper().map(userCreateDto, User.class);
    }

    public static UserResponseDto toUserDto(User user){
        ModelMapper mapper = new ModelMapper();

        PropertyMap<User, UserResponseDto> props = new PropertyMap<User, UserResponseDto>() {
            @Override
            protected void configure() {
                String role = user.getRole().name().substring("ROLE_".length());
                map().setRole(role);
            }
        };

        mapper.addMappings(props);

        return mapper.map(user,UserResponseDto.class);
    }

}
