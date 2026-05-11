-- Base de datos CRM Instituto Xtart - MySQL
-- Esta es la base que usa la aplicacion Java

CREATE DATABASE IF NOT EXISTS crm_xtart;
USE crm_xtart;

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    telefono VARCHAR(15),
    direccion VARCHAR(200)
);

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    rol VARCHAR(15) NOT NULL,
    password_hash VARCHAR(64)
);

CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(240),
    precio DECIMAL(8,2) NOT NULL,
    categoria VARCHAR(20) NOT NULL
);

CREATE TABLE ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    usuario_id INT NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(15) NOT NULL,
    total DECIMAL(10,2),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE detalle_venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venta_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(8,2) NOT NULL,
    FOREIGN KEY (venta_id) REFERENCES ventas(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);

INSERT INTO clientes (nombre, email, telefono, direccion) VALUES
('Omar Yamani', 'omar.yamani@estudiante.xtart.com', '666111222', 'Calle Mayor 1, Madrid'),
('Daniel Vargas', 'daniel.vargas@estudiante.xtart.com', '666333444', 'Av. Libertad 23, Sevilla'),
('Alvaro Mendez', 'alvaro.mendez@estudiante.xtart.com', '655987321', 'Plaza Espana 5, Valencia'),
('Berta Ramirez', 'berta.ramirez@estudiante.xtart.com', '677456123', 'Calle Sol 14, Madrid'),
('Lihuak Romero', 'lihuak.romero@estudiante.xtart.com', '688234567', 'Av. Andalucia 8, Malaga');

INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES
('Nuria Soto', 'nuria.soto@xtart.com', 'COMERCIAL', 'hash_nuria'),
('Daniel Jaen', 'daniel.jaen@xtart.com', 'ORIENTADOR', 'hash_daniel'),
('Alberto Nieto', 'alberto.nieto@xtart.com', 'ADMIN', 'hash_alberto'),
('Sonia Prieto', 'sonia.prieto@xtart.com', 'SECRETARIA', 'hash_sonia'),
('Aitor Gallego', 'aitor.gallego@xtart.com', 'COMERCIAL', 'hash_aitor');

INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES
('DAM', 'Desarrollo de Aplicaciones Multiplataforma', 1200, 'GRADO_SUPERIOR'),
('SMR', 'Sistemas Microinformaticos y Redes', 900, 'GRADO_MEDIO'),
('DAW', 'Desarrollo de Aplicaciones Web', 1200, 'GRADO_SUPERIOR'),
('Ingles B2', 'Preparacion oficial nivel B2', 350, 'IDIOMAS'),
('Ciberseguridad', 'Curso de especializacion en ciberseguridad', 800, 'ESPECIALIZACION');

INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES
(1, 1, '2026-01-15', 'PAGADA', 1200),
(2, 5, '2026-02-03', 'CONFIRMADA', 900),
(3, 1, '2026-02-20', 'PAGADA', 1550),
(4, 5, '2026-03-10', 'PENDIENTE', 1200),
(5, 2, '2026-03-22', 'PAGADA', 350);

INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES
(1, 1, 1, 1200),
(2, 2, 1, 900),
(3, 3, 1, 1200),
(3, 4, 1, 350),
(5, 4, 1, 350);
