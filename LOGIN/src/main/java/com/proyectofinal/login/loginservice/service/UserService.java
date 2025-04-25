package com.proyectofinal.login.loginservice.service;

import com.proyectofinal.login.loginservice.exception.UserNameNotFoundException;
import com.proyectofinal.login.loginservice.exception.UserNotFoundException;
import com.proyectofinal.login.loginservice.model.User;
import com.proyectofinal.login.loginservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuario con email: " + email + " no encontrado."));
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username)
                .orElseThrow(() ->
                        new UserNameNotFoundException("Usuario con nombre de usuario: " + username + " no encontrado"));
    }

}
