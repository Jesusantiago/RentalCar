package com.proyectofinal.login.loginservice.service;

import com.proyectofinal.login.loginservice.model.User;
import com.proyectofinal.login.loginservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email){
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

}
