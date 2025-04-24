package com.proyectofinal.login.loginservice;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.proyectofinal.login.loginservice.model.User;
import com.proyectofinal.login.loginservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldGetAUser() {

        ResponseEntity<String> response = restTemplate.getForEntity("/api/users/1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(1);
    }

        @Autowired
        private UserRepository userRepository;

        @Test
        void shouldFindUserFromDataSql() {
            Optional<User> optionalUser = userRepository.findById(1L);

            assertThat(optionalUser).isPresent();

            User user = optionalUser.get();
            assertThat(user.getEmail()).isEqualTo("john@example.com");
            assertThat(user.getName()).isEqualTo("John");
        }
    }


