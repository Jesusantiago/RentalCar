-- data.sql para insertar datos en H2 para pruebas

-- Insertar Branches
INSERT INTO branch (name, address, city, phone) VALUES
    ('AutoCon', 'Av. Principal 123', 'Merida', 123456789),
    ('CarMax', 'Calle Secundaria 456', 'Caracas', 987654321),
    ('DriveFast', 'Avenida Libertador 789', 'Valencia', 564738291),
    ('Wheelz', 'Calle Larga 101', 'Maracay', 349856472);

-- Insertar Cars
INSERT INTO car (brand, model, car_year, license_plate, status, branch_id) VALUES
   ('Toyota', 'Supra', 2020, '123-ABC', 'AVAILABLE', 1),
   ('Toyota', 'Supra', 2021, '124-ABC', 'MAINTENANCE', 1),
   ('Honda', 'Civic', 2018, '456-DEF', 'AVAILABLE', 2),
   ('Honda', 'Civic', 2019, '457-DEF', 'AVAILABLE', 2),
   ('Honda', 'Accord', 2020, '458-GHI', 'AVAILABLE', 3),
   ('Honda', 'Accord', 2021, '459-GHI', 'MAINTENANCE', 3),
   ('Ford', 'Focus', 2019, '789-GHI', 'AVAILABLE', 4),
   ('Ford', 'Focus', 2020, '790-JKL', 'AVAILABLE', 4),
   ('Ford', 'Mustang', 2021, '791-KLM', 'AVAILABLE', 1),
   ('Toyota', 'Corolla', 2020, '792-NOP', 'AVAILABLE', 2);

-- Insertar Rentals
INSERT INTO rental (start_date, end_date, price, status, car_id, cliente_id, branch_from_id, branch_to_id) VALUES
   ('2025-05-01 10:00:00', '2025-05-07 10:00:00', 250.00, 'RENTAL', 1, 101, 1, 2),
   ('2025-05-02 11:00:00', '2025-05-08 11:00:00', 180.00, 'RENTAL', 2, 102, 1, 3),
   ('2025-05-03 12:00:00', '2025-05-09 12:00:00', 200.00, 'RENTAL', 3, 103, 2, 4),
   ('2025-05-04 13:00:00', '2025-05-10 13:00:00', 300.00, 'RENTAL', 4, 104, 3, 1),
   ('2025-05-05 14:00:00', '2025-05-11 14:00:00', 150.00, 'RENTAL', 5, 105, 4, 2),
   ('2025-05-06 15:00:00', '2025-05-12 15:00:00', 220.00, 'RENTAL', 6, 106, 1, 4);