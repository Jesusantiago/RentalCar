package com.proyectofinal.car.repository;

import com.proyectofinal.car.model.Branch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class BranchRepositoryTest {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CarRepository carRepository;

    @Test
    void returnABranchByIdTest() {
        Optional<Branch> branch = branchRepository.findById(1L);
        assertThat(branch).isPresent();

        Branch b = branch.get();
        assertThat(b.getName()).isEqualTo("AutoCon");
        assertThat(b.getAddress()).isEqualTo("Av. Principal 123");
        assertThat(b.getCity()).isEqualTo("Merida");
        assertThat(b.getPhone()).isEqualTo(123456789);
    }

    @Test
    void returnAllBranchesTest() {
        List<Branch> branches = branchRepository.findAll();
        assertThat(branches).isNotNull();
        assertThat(branches.size()).isEqualTo(4);
    }

    @Test
    void returnABranchByNameTest() {
        Optional<Branch> result = branchRepository.findByNameContainingIgnoreCase("autocon");
        assertThat(result).isPresent();

        assertThat(result.get().getName()).isEqualTo("AutoCon");
        assertThat(result.get().getAddress()).isEqualTo("Av. Principal 123");
        assertThat(result.get().getCity()).isEqualTo("Merida");
    }

    @Test
    void returnABranchByAddressTest() {
        Optional<Branch> result = branchRepository.findByAddressContainingIgnoreCase("principal");
        assertThat(result).isPresent();

        assertThat(result.get().getName()).isEqualTo("AutoCon");
        assertThat(result.get().getAddress()).isEqualTo("Av. Principal 123");
    }

    @Test
    void returnABranchByCityTest() {
        Optional<Branch> result = branchRepository.findByCityContainingIgnoreCase("merida");
        assertThat(result).isPresent();

        assertThat(result.get().getName()).isEqualTo("AutoCon");
        assertThat(result.get().getAddress()).isEqualTo("Av. Principal 123");
        assertThat(result.get().getCity()).isEqualTo("Merida");
    }

    @Test
    void returnABranchByPhoneTest() {
        Optional<Branch> result = branchRepository.findByPhone(123456789);
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("AutoCon");
        assertThat(result.get().getAddress()).isEqualTo("Av. Principal 123");
        assertThat(result.get().getCity()).isEqualTo("Merida");
        assertThat(result.get().getPhone()).isEqualTo(123456789);
    }

    @Test
    void saveBranchTest() {
        Branch b = new Branch();
        b.setName("WildCar");
        b.setAddress("Av. terciaria 789");
        b.setCity("Madrid");
        b.setPhone(456321789);
        branchRepository.save(b);

        Optional<Branch> result = branchRepository.findById(b.getBranchId());
        assertThat(result).isPresent();

        assertThat(result.get().getBranchId()).isEqualTo(b.getBranchId());
        assertThat(result.get().getName()).isEqualTo("WildCar");
        assertThat(result.get().getAddress()).isEqualTo("Av. terciaria 789");
        assertThat(result.get().getCity()).isEqualTo("Madrid");
        assertThat(result.get().getPhone()).isEqualTo(456321789);
    }

    @Test
    void updateBranchTest() {
        Optional<Branch> result = branchRepository.findByNameContainingIgnoreCase("wildcar");
        assertThat(result).isPresent();

        Branch b = result.get();
        b.setName("YourNewCar");
        branchRepository.save(b);

        Optional<Branch> result2 = branchRepository.findByNameContainingIgnoreCase("YourNewCar");
        assertThat(result2).isPresent();
        assertThat(result2.get().getName()).isEqualTo("YourNewCar");
        assertThat(result2.get().getAddress()).isEqualTo("Av. terciaria 789");
    }

    @Test
    void deleteBranchTest() {
        Optional<Branch> result = branchRepository.findByNameContainingIgnoreCase("drivefast");
        assertThat(result).isPresent();

        branchRepository.delete(result.get());
        Optional<Branch> result2 = branchRepository.findByNameContainingIgnoreCase("drivefast");
        assertThat(result2).isNotPresent();
    }

}
