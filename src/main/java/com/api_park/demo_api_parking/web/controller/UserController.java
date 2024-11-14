package com.api_park.demo_api_parking.web.controller;

import com.api_park.demo_api_parking.entity.User;
import com.api_park.demo_api_parking.services.UserService;
import com.api_park.demo_api_parking.web.controller.dto.UserCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.UserPassWordDto;
import com.api_park.demo_api_parking.web.controller.dto.UserResponseDto;
import com.api_park.demo_api_parking.web.controller.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto userCreateDto){
        User newUser = userService.save(UserMapper.toUser(userCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserDto(newUser));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(UserMapper.toUserDto(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(UserMapper.toUserDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> upDatePassWord(@PathVariable Long id, @Valid @RequestBody UserPassWordDto userPassWordDto){
        User updateUserPassword = userService.upDatePassWord(id, userPassWordDto.getCurrentPassWord(), userPassWordDto.getNewPassWord(), userPassWordDto.getConfirmNewPassWord());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
