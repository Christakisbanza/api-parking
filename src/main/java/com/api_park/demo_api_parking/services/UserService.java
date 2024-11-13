package com.api_park.demo_api_parking.services;

import com.api_park.demo_api_parking.entity.User;
import com.api_park.demo_api_parking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user){
        return userRepository.save(user);
    }
}
