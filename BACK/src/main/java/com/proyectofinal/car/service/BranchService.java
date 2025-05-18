package com.proyectofinal.car.service;

import com.proyectofinal.car.dto.branch.BranchDTO;
import com.proyectofinal.car.dto.branch.BranchNewCarDTO;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.repository.BranchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public List<BranchNewCarDTO> searchAllBranch(){
        List<Branch> branches = branchRepository.findAll();
        List<BranchNewCarDTO> branchesDTO = new ArrayList<>();

        branches.forEach(branch -> {
            BranchNewCarDTO branchDTO = new BranchNewCarDTO();
            branchDTO.setId(branch.getBranchId());
            branchDTO.setName(branch.getName());
            branchesDTO.add(branchDTO);
        });
        return branchesDTO;
    }

    public Branch updateBranch(Long id, BranchDTO branchDTO) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id " + id));

        branch.setName(branchDTO.getName());
        branch.setAddress(branchDTO.getAddress());
        branch.setCity(branchDTO.getCity());
        branch.setPhone(branchDTO.getPhone());

        return branchRepository.save(branch);
    }

    public BranchDTO createBranch(BranchDTO branchDTO) {
        Branch branch = new Branch();
        branch.setName(branchDTO.getName());
        branch.setAddress(branchDTO.getAddress());
        branch.setCity(branchDTO.getCity());
        branch.setPhone(branchDTO.getPhone());

        Branch savedBranch = branchRepository.save(branch);

        return new BranchDTO(
                savedBranch.getName(),
                savedBranch.getAddress(),
                savedBranch.getCity(),
                savedBranch.getPhone()
        );
    }
}