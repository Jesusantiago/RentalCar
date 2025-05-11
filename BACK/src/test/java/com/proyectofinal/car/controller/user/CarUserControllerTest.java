package com.proyectofinal.car.controller.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(properties = "spring.datasource.initialize=false")
@AutoConfigureMockMvc
public class CarUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAvailableCars_returns200AndCarsList() throws Exception {
        mockMvc.perform(get("/user/available")
                .param("page", "0")
                .param("size", "10")
                .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(8));
    }

    @Test
    void getACars_returns200AndCarsList() throws Exception {
        mockMvc.perform(get("/user/available/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(1))
            .andExpect(jsonPath("brand").value("Toyota"))
            .andExpect(jsonPath("model").value("Supra"))
            .andExpect(jsonPath("carYear").value(2020))
            .andExpect(jsonPath("licensePlate").value("123-ABC"))
            .andExpect(jsonPath("branch.id").value(1));

    }

    @Test
    void getCarById_notFound_returnsErrorMessage() throws Exception {
        mockMvc.perform(get("/user/available/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCarsWithFilterByBrand_returns200AndCarsList() throws Exception {
        mockMvc.perform(get("/user/available/search")
                .param("brand", "Toyota"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void getCarsWithFilterByModel_returns200AndCarsList() throws Exception {
        mockMvc.perform(get("/user/available/search")
                .param("model", "Civic"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void getCarsWithFilterByCarYear_returns200AndCarsList() throws Exception {
        mockMvc.perform(get("/user/available/search")
                .param("carYear", Integer.toString(2020)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(4));
    }

    @Test
    void getCarsWithFilterByCarYearAndSort_returns200AndCarsList() throws Exception {
        mockMvc.perform(get("/user/available/search")
                        .param("sortBy", "carYear")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(8));
    }

    @Test
    void getCarsWithFilterByBranch_returns200AndCarsList() throws Exception {
        mockMvc.perform(get("/user/available/search")
            .param("branch", "AutoCon"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void getCarsWithNonExistentBrand_returnsEmptyList() throws Exception {
        mockMvc.perform(get("/user/available/search")
            .param("brand", "Ferrari"))
            .andExpect(status().isNotFound());
    }

    @Test
    void getCarsWithNonExistentModel_returnsEmptyList() throws Exception {
        mockMvc.perform(get("/user/available/search")
                        .param("model", "Camaro"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCarsWithNonExistentCarYear_returnsEmptyList() throws Exception {
        mockMvc.perform(get("/user/available/search")
            .param("carYear", "1999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void getCarsWithNonExistentBranch_returnsEmptyList() throws Exception {
        mockMvc.perform(get("/user/available/search")
            .param("branch", "AutoMax"))
            .andExpect(status().isNotFound());
    }

    @Test
    void getCarsWithValidFiltersButNoMatch_returnsEmptyList() throws Exception {
        mockMvc.perform(get("/user/available/search")
            .param("brand", "Toyota")
            .param("model", "Civic")
            .param("carYear", "2021")
            .param("branch", "CarMax"))
            .andExpect(status().isNotFound());
    }


}
