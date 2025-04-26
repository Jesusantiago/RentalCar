package com.proyectofinal.login.loginservice.controller;

import com.proyectofinal.login.loginservice.dto.JsonResponseDTO;
import com.proyectofinal.login.loginservice.dto.LoginRequest;
import com.proyectofinal.login.loginservice.dto.LoginResponseDTO;
import com.proyectofinal.login.loginservice.exception.UserNotFoundException;
import com.proyectofinal.login.loginservice.model.User;
import com.proyectofinal.login.loginservice.security.JwtUtil;
import com.proyectofinal.login.loginservice.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Entrando al login...");
        try{
            User user = userService.findByEmail(loginRequest.getEmail());

            System.out.println("User: " + user);

            if (! passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña no validos.");
            }



            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok().body(new LoginResponseDTO(token, user.getId(), user.getUserName(), user.getLicenseType()));

        } catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
        }
    }

    @PostMapping("/logout")
    private ResponseEntity<String> logout() {
        return ResponseEntity.status(HttpStatus.OK).body("Cierre de sesión exitoso.");
    }

}
