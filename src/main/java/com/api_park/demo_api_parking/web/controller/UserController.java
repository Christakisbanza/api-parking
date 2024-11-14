package com.api_park.demo_api_parking.web.controller;

import com.api_park.demo_api_parking.entity.User;
import com.api_park.demo_api_parking.services.UserService;
import com.api_park.demo_api_parking.web.controller.dto.UserCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.UserResponseDto;
import com.api_park.demo_api_parking.web.controller.dto.mapper.UserMapper;
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
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateDto userCreateDto){
        User newUser = userService.save(UserMapper.toUser(userCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserDto(newUser));
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> upDatePassWord(@PathVariable Long id, @RequestBody User user){
        User updateUserPassword = userService.upDatePassWord(id, user.getPassWord());
        return ResponseEntity.ok().body(updateUserPassword);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
