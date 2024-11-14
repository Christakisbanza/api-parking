package com.api_park.demo_api_parking.services;

import com.api_park.demo_api_parking.entity.User;
import com.api_park.demo_api_parking.exception.UserNameUniqueViolationException;
import com.api_park.demo_api_parking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return userRepository.save(user);
        }catch (DataIntegrityViolationException e ){
            throw new UserNameUniqueViolationException(String.format("UserName {%s} já cadastrado", user.getUserName()));
        }
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
    public User upDatePassWord(Long id, String currentPassWord, String newPassWord, String confirmNewPassWord){
        if(!newPassWord.equals(confirmNewPassWord)){
            throw new RuntimeException("Confirmação da senha inválida");
        }

        User user = findById(id);

        if(!user.getPassWord().equals(currentPassWord)){
            throw new RuntimeException("Senha Inválida");
        }

        user.setPassWord(newPassWord);
        return user;
    }

    @Transactional
    public void deleteById(Long id){
        findById(id);
        userRepository.deleteById(id);
    }

}
