package com.proyectofinal.car.util;

import com.proyectofinal.car.enums.StatusCar;
import com.proyectofinal.car.model.Car;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CarSpecifications {

    public static Specification<Car> filterBy(
            String brand,
            String model,
            String branch,
            Integer carYear,
            StatusCar status,
            Long client_id
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(client_id != null) {
                predicates.add(criteriaBuilder.equal(root.get("client_id"), client_id));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (brand != null && !brand.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("brand")), brand.toLowerCase()));
            }

            if (model != null && !model.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("model")), model.toLowerCase()));
            }

            if (branch != null && !branch.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("branch").get("name")), branch.toLowerCase()));
            }

            if (carYear != null) {
                predicates.add(criteriaBuilder.equal(root.get("carYear"), carYear));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}