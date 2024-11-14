package com.api_park.demo_api_parking.web.controller.dto.mapper;

import com.api_park.demo_api_parking.entity.User;
import com.api_park.demo_api_parking.web.controller.dto.UserCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;


public class UserMapper {

    public static User toUser(UserCreateDto userCreateDto){
        return new ModelMapper().map(userCreateDto, User.class);
    }

    public static UserResponseDto toUserDto(User user){
        return new ModelMapper().map(user, UserResponseDto.class);
    }

    public static List<UserResponseDto> toUserDto(List<User> users){
        ModelMapper mapper = new ModelMapper();
        return users.stream()
                .map(user -> mapper.map(user, UserResponseDto.class))
                .toList();
    }

}
