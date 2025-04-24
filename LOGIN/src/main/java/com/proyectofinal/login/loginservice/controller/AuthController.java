package com.proyectofinal.login.loginservice.controller;

import com.proyectofinal.login.loginservice.dto.LoginRequest;
import com.proyectofinal.login.loginservice.model.User;
import com.proyectofinal.login.loginservice.security.JwtUtil;
import com.proyectofinal.login.loginservice.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("hola bb");
        User user = userService.findByEmail(loginRequest.getEmail()).orElse(null);


        if (user == null ||! passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Correo o contraseña no validos.");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok().body(token);
    }
}
