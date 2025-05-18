package com.proyectofinal.car.repository;

import com.proyectofinal.car.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByNameContainingIgnoreCase(String name);
    Optional<Branch> findByAddressContainingIgnoreCase(String address);
    Optional<Branch> findByCityContainingIgnoreCase(String city);
    Optional<Branch> findByPhone(String phone);
}

