package com.proyectofinal.car.controller;

import com.proyectofinal.car.dto.branch.BranchDTO;
import com.proyectofinal.car.dto.branch.BranchNewCarDTO;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.repository.BranchRepository;
import com.proyectofinal.car.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class BranchAdminController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private BranchRepository branchRepository;

    @GetMapping("/branches")
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        List<Branch> branches = branchRepository.findAll();

        List<BranchDTO> branchDTO = branches
                .stream()
                .map(branch -> new BranchDTO(
                        branch.getName(),
                        branch.getAddress(),
                        branch.getCity(),
                        branch.getPhone()
                )).toList();

        return ResponseEntity.ok(branchDTO);
    }

    @GetMapping("/branchsCar")
    public ResponseEntity<List<BranchNewCarDTO>> getAllBranchesForNewCar() {
        List<BranchNewCarDTO> allBranch = branchService.searchAllBranch();

        if (allBranch.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(allBranch);
    }

    @GetMapping("/branch/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        System.out.println(branchRepository.findAll());
        Optional<Branch> branchOpt = branchRepository.findById(id);
        if (branchOpt.isPresent()) {
            Branch branch = branchOpt.get();
            return ResponseEntity.ok(branch);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/branch")
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) {
        BranchDTO createdBranch = branchService.createBranch(branchDTO);
        return new ResponseEntity<>(createdBranch, HttpStatus.CREATED);
    }

    @PutMapping("/branches/{id}")
    public ResponseEntity<BranchDTO> updateBranch(
            @PathVariable Long id,
            @RequestBody BranchDTO branchDTO) {

        Branch updatedBranch = branchService.updateBranch(id, branchDTO);

        BranchDTO responseDTO = new BranchDTO(
                updatedBranch.getName(),
                updatedBranch.getAddress(),
                updatedBranch.getCity(),
                updatedBranch.getPhone()
        );

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/branches/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        if (!branchRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        branchRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
