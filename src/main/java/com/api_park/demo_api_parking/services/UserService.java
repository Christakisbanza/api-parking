package com.api_park.demo_api_parking.services;

import com.api_park.demo_api_parking.entity.User;
import com.api_park.demo_api_parking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user){
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuario não encontrado")
        );
    }

    @Transactional
    public User upDatePassWord(Long id, String password){
        User user = findById(id);
        user.setPassWord(password);
        return user;
    }

    @Transactional
    public void deleteById(Long id){
        findById(id);
        userRepository.deleteById(id);
    }
}
