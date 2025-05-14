package com.proyectofinal.car.controller;

import com.proyectofinal.car.dto.branch.BranchNewCarDTO;
import com.proyectofinal.car.repository.BranchRepository;
import com.proyectofinal.car.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class BranchAdminController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private BranchRepository branchRepository;

    @GetMapping("/branchs")
    public ResponseEntity<List<BranchNewCarDTO>> getAllBranches() {
        List<BranchNewCarDTO> allBranch = branchService.searchAllBranch();

        if (allBranch.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(allBranch);
    }
}
