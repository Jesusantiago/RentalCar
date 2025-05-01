package com.proyectofinal.login.loginservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectofinal.login.loginservice.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerUser_shouldReturn201() throws Exception {
        User user = new User();
        user.setName("test");
        user.setLastName("example");
        user.setEmail("test@proyectofinal.com");
        user.setPassword("password123");
        user.setUserName("testValid");
        user.setLicenseType("B");
        user.setDateOfBirth(LocalDate.now());

        mockMvc.perform(post("/api/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isCreated());
    }

    @Test
    void getUserById_shouldReturn200() throws Exception {
        User user = new User();
        user.setName("test");
        user.setLastName("example");
        user.setEmail("test@proyectofinal.com");
        user.setPassword("password123");
        user.setUserName("testValid");
        user.setLicenseType("B");
        user.setDateOfBirth(LocalDate.now());

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        MvcResult resultJwt = mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\": \"test@proyectofinal.com\", \"password\": \"password123\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").isNotEmpty())
            .andReturn();

        String responseContent = resultJwt.getResponse().getContentAsString();

        String jwtToken = objectMapper.readTree(responseContent).get("token").asText();

        mockMvc.perform(get("/api/users/1")
            .header("Authorization", "Bearer " + jwtToken))
            .andExpect(status().isOk());
    }
}
