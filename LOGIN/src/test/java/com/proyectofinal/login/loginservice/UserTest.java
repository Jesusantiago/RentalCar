package com.proyectofinal.login.loginservice;
import com.proyectofinal.login.loginservice.model.User;
import com.proyectofinal.login.loginservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void findUserByEmail() {
        String email = "juan.perez@example.com";

        Optional<User> result = repository.findByEmail(email);

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo(email);
    }
}
