package com.proyectofinal.car.service;

import com.proyectofinal.car.dto.rental.RentalRequestDTO;
import com.proyectofinal.car.dto.rental.RentalResponseDTO;
import com.proyectofinal.car.model.Branch;
import com.proyectofinal.car.model.Car;
import com.proyectofinal.car.model.Rental;
import com.proyectofinal.car.repository.BranchRepository;
import com.proyectofinal.car.repository.CarRepository;
import com.proyectofinal.car.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BranchRepository branchRepository;

    public RentalResponseDTO getRentalDTOById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        return new RentalResponseDTO(
                rental.getRentalId(),
                rental.getStartDate(),
                rental.getEndDate(),
                rental.getPrice(),
                rental.getStatus(),
                rental.getCar().getCarId(),
                rental.getClientId(),
                rental.getBranchFrom().getBranchId(),
                rental.getBranchTo().getBranchId()
        );
    }

    public List<RentalResponseDTO> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentals.stream().map(rental -> new RentalResponseDTO(
                rental.getRentalId(),
                rental.getStartDate(),
                rental.getEndDate(),
                rental.getPrice(),
                rental.getStatus(),
                rental.getCar().getCarId(),
                rental.getClientId(),
                rental.getBranchFrom().getBranchId(),
                rental.getBranchTo().getBranchId()
        )).collect(Collectors.toList());
    }

    public Rental createRental(RentalRequestDTO request) {
        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));

        Branch branchFrom = branchRepository.findById(request.getBranchFromId())
                .orElseThrow(() -> new RuntimeException("BranchFrom not found"));

        Branch branchTo = branchRepository.findById(request.getBranchToId())
                .orElseThrow(() -> new RuntimeException("BranchTo not found"));

        Rental rental = new Rental();
        rental.setStartDate(request.getStartDate());
        rental.setEndDate(request.getEndDate());
        rental.setPrice(request.getPrice());
        rental.setStatus(request.getStatus());
        rental.setCar(car);
        rental.setClientId(request.getClientId());
        rental.setBranchFrom(branchFrom);
        rental.setBranchTo(branchTo);

        return rentalRepository.save(rental);
    }

    public Rental updateRental(Long id, RentalRequestDTO request) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));

        Branch branchFrom = branchRepository.findById(request.getBranchFromId())
                .orElseThrow(() -> new RuntimeException("Branch From not found"));

        Branch branchTo = branchRepository.findById(request.getBranchToId())
                .orElseThrow(() -> new RuntimeException("Branch To not found"));

        rental.setStartDate(request.getStartDate());
        rental.setEndDate(request.getEndDate());
        rental.setPrice(request.getPrice());
        rental.setStatus(request.getStatus());
        rental.setCar(car);
        rental.setClientId(request.getClientId());
        rental.setBranchFrom(branchFrom);
        rental.setBranchTo(branchTo);

        return rentalRepository.save(rental);
    }

    public void deleteRental(Long id) {
        if (!rentalRepository.existsById(id)) {
            throw new RuntimeException("Rental not found");
        }
        rentalRepository.deleteById(id);
    }
}

