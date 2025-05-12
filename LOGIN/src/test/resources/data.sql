-- prueba
INSERT INTO users (name, last_name, email, password, user_name, license_type, date_of_birth)
VALUES
   ('Juan', 'Pérez', 'juan.perez@example.com', 'password123', 'juanperez', 'A', '1990-05-15'),
   ('Maria', 'Gómez', 'maria.gomez@example.com', 'password456', 'mariagomez', 'B', '1985-07-25'),
   ('Carlos', 'López', 'carlos.lopez@example.com', 'password789', 'carloslopez', 'C', '1992-11-30');

-- línea inválida para forzar un error
-- INSERT INTO users (name) VALUES ('ERROR TEST');