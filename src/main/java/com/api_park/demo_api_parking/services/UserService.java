package com.api_park.demo_api_parking.services;

import com.api_park.demo_api_parking.entity.User;
import com.api_park.demo_api_parking.exception.EntityNotFoundException;
import com.api_park.demo_api_parking.exception.UserNameUniqueViolationException;
import com.api_park.demo_api_parking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user){
        try {
            user.setPassWord(passwordEncoder.encode(user.getPassWord()));
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
                () -> new EntityNotFoundException(String.format("Usuario id : %s não encontrado", id))
        );
    }

    @Transactional
    public User upDatePassWord(Long id, String currentPassWord, String newPassWord, String confirmNewPassWord){
        if(!newPassWord.equals(confirmNewPassWord)){
            throw new RuntimeException("Confirmação da senha inválida");
        }

        User user = findById(id);

        if(!passwordEncoder.matches(currentPassWord, user.getPassWord())){
            throw new RuntimeException("Senha Inválida");
        }

        user.setPassWord(passwordEncoder.encode(newPassWord));
        return user;
    }

    @Transactional
    public void deleteById(Long id){
        findById(id);
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public User findByUserName(String name){
        return userRepository.findByUserName(name).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário %s não encontrado", name))
        );
    }

    @Transactional
    public User.Role findRoleByUserName(String name){
        return userRepository.findRoleByUserName(name);
    }

}
