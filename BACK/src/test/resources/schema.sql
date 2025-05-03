DROP TABLE IF EXISTS car;
DROP TABLE IF EXISTS branch;

-- Crear tabla Branch
CREATE TABLE branch (
    branch_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    city VARCHAR(255),
    phone BIGINT
);

-- Crear tabla Car
CREATE TABLE car (
     car_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     brand VARCHAR(255) NOT NULL,
     model VARCHAR(255) NOT NULL,
     car_year INT NOT NULL,
     license_plate VARCHAR(20) NOT NULL UNIQUE,
     status VARCHAR(50) NOT NULL, -- Este puede ser un valor enum que se mapea como texto
     branch_id BIGINT NOT NULL,
     FOREIGN KEY (branch_id) REFERENCES branch(branch_id)
);

-- Crear tabla Rental
CREATE TABLE rental (
        rental_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        start_date TIMESTAMP NOT NULL,
        end_date TIMESTAMP,
        price DECIMAL(10, 2),
        status VARCHAR(50) NOT NULL, -- Este también puede ser un enum mapeado como texto
        car_id BIGINT NOT NULL,
        cliente_id BIGINT NOT NULL,
        branch_from_id BIGINT NOT NULL,
        branch_to_id BIGINT NOT NULL,
        FOREIGN KEY (car_id) REFERENCES car(car_id),
        FOREIGN KEY (branch_from_id) REFERENCES branch(branch_id),
        FOREIGN KEY (branch_to_id) REFERENCES branch(branch_id)
);