package com.proyectofinal.car.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class BranchRepositoryTest {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CarRepository carRepository;


}
