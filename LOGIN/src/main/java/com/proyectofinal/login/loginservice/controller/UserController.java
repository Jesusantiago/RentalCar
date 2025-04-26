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

/**
 * Entrada del servidor.
 *
 * @RequestMapping: Ruta http
 */

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get - Todos los usuarios.
    @GetMapping
    public List<User> getUsers() {
        System.out.println("Entrando al obtener todos");
        return userService.getAllUser();
    }

    // Get - Un usuario.
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        System.out.println("Obtener uno solo");

        Optional<User> optionalUser = userService.getUserById(id);

        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    // Post - Crear usuario
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userRequest,@Valid BindingResult result) {

        // Verifica los datos ingresados con el BindingResult, donde si hay error, nos los presenta y no crea el usuario
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errors);
        }

        // Función encargada de lanzar una excepcion si el email o userName ya exiten.
        // Esta función puede pasar el sistema.
        userService.checkIfUserExists(userRequest.getEmail(), userRequest.getUserName());

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
