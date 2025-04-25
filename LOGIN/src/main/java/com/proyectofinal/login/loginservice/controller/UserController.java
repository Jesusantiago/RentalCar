package com.proyectofinal.login.loginservice.controller;

import com.proyectofinal.login.loginservice.dto.UserDTO;
import com.proyectofinal.login.loginservice.model.User;
import com.proyectofinal.login.loginservice.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get - Todos los usuarios.
    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUser();
    }

    // Get - Un usuario.
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<User> optionalUser = userService.getUserById(id);

        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    // Post - Crear usuario
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errors);
        }


        Optional<User> existingUser = userService.findByEmail(userRequest.getEmail());
        Optional<User> existingUsuary = userService.findByUserName(userRequest.getUserName());

        if(existingUsuary.isPresent() || existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setUserName(userRequest.getUserName());
        user.setLicenseType(userRequest.getLicenseType());
        user.setDateOfBirth(userRequest.getDateOfBirth());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
