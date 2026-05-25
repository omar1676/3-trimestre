CREATE TABLE clientes (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(75) NOT NULL,
    email VARCHAR2(120) NOT NULL UNIQUE,
    telefono VARCHAR2(15),
    direccion VARCHAR2(180)
);

CREATE TABLE usuarios (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(75) NOT NULL,
    email VARCHAR2(120) NOT NULL UNIQUE,
    rol VARCHAR2(12) NOT NULL,
    password_hash VARCHAR2(64)
);

CREATE TABLE productos (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(50) NOT NULL,
    descripcion VARCHAR2(240),
    precio NUMBER(8,2) NOT NULL,
    categoria VARCHAR2(20) NOT NULL
);

CREATE TABLE ventas (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cliente_id NUMBER NOT NULL,
    usuario_id NUMBER NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR2(12) NOT NULL,
    total NUMBER(10,2),
    CONSTRAINT fk_venta_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    CONSTRAINT fk_venta_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE detalle_venta (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    venta_id NUMBER NOT NULL,
    producto_id NUMBER NOT NULL,
    cantidad NUMBER(4) NOT NULL,
    precio_unitario NUMBER(8,2) NOT NULL,
    CONSTRAINT fk_detalle_venta FOREIGN KEY (venta_id) REFERENCES ventas(id),
    CONSTRAINT fk_detalle_producto FOREIGN KEY (producto_id) REFERENCES productos(id)
);

INSERT INTO clientes (nombre, email, telefono, direccion) VALUES ('Omar Yamani', 'omar.yamani@estudiante.xtart.com', '666111222', 'Calle Mayor 1, Madrid');
INSERT INTO clientes (nombre, email, telefono, direccion) VALUES ('Daniel Vargas', 'daniel.vargas@estudiante.xtart.com', '666333444', 'Av. Libertad 23, Sevilla');
INSERT INTO clientes (nombre, email, telefono, direccion) VALUES ('Alvaro Mendez', 'alvaro.mendez@estudiante.xtart.com', '655987321', 'Plaza Espana 5, Valencia');
INSERT INTO clientes (nombre, email, telefono, direccion) VALUES ('Berta Ramirez', 'berta.ramirez@estudiante.xtart.com', '677456123', 'Calle Sol 14, Madrid');
INSERT INTO clientes (nombre, email, telefono, direccion) VALUES ('Lihuak Romero', 'lihuak.romero@estudiante.xtart.com', '688234567', 'Av. Andalucia 8, Malaga');
INSERT INTO clientes (nombre, email, telefono, direccion) VALUES ('Diego Fuentes', 'diego.fuentes@estudiante.xtart.com', '699112233', 'Calle Luna 22, Bilbao');
INSERT INTO clientes (nombre, email, telefono, direccion) VALUES ('Renzo Aguilar', 'renzo.aguilar@estudiante.xtart.com', '644556677', 'Paseo del Mar 3, Alicante');
INSERT INTO clientes (nombre, email, telefono, direccion) VALUES ('Noemi Cabrera', 'noemi.cabrera@estudiante.xtart.com', '633778899', 'Calle Real 41, Granada');
INSERT INTO clientes (nombre, email, telefono, direccion) VALUES ('Laura Iglesias', 'laura.iglesias@estudiante.xtart.com', '622334455', 'Av. Constitucion 12, Zaragoza');
INSERT INTO clientes (nombre, email, telefono, direccion) VALUES ('Alejandro Pardo', 'alejandro.pardo@estudiante.xtart.com', '611223344', 'Calle Nueva 7, Murcia');

INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES ('Nuria Soto', 'nuria.soto@xtart.com', 'COMERCIAL', 'hash_nuria');
INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES ('Daniel Jaen', 'daniel.jaen@xtart.com', 'ORIENTADOR', 'hash_daniel');
INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES ('Alberto Nieto', 'alberto.nieto@xtart.com', 'ADMIN', 'hash_alberto');
INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES ('Sonia Prieto', 'sonia.prieto@xtart.com', 'SECRETARIA', 'hash_sonia');
INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES ('Aitor Gallego', 'aitor.gallego@xtart.com', 'COMERCIAL', 'hash_aitor');
INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES ('Lorena Sanz', 'lorena.sanz@xtart.com', 'ORIENTADOR', 'hash_lorena');
INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES ('Marcos Gil', 'marcos.gil@xtart.com', 'ADMIN', 'hash_marcos');
INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES ('Elena Cano', 'elena.cano@xtart.com', 'SECRETARIA', 'hash_elena');

INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('DAM', 'Desarrollo de Aplicaciones Multiplataforma', 1200, 'GRADO_SUPERIOR');
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('SMR', 'Sistemas Microinformaticos y Redes', 900, 'GRADO_MEDIO');
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('DAW', 'Desarrollo de Aplicaciones Web', 1200, 'GRADO_SUPERIOR');
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('ASIR', 'Administracion de Sistemas Informaticos en Red', 1150, 'GRADO_SUPERIOR');
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('Ingles B2', 'Preparacion oficial nivel B2', 350, 'IDIOMAS');
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('Ciberseguridad', 'Curso de especializacion en ciberseguridad', 800, 'ESPECIALIZACION');
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('Ofimatica', 'Curso corto de ofimatica avanzada', 200, 'CURSO_CORTO');
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('Big Data e IA', 'Especializacion en inteligencia artificial', 850, 'ESPECIALIZACION');
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('Aleman A2', 'Curso de aleman nivel A2', 320, 'IDIOMAS');
INSERT INTO productos (nombre, descripcion, precio, categoria) VALUES ('Marketing Digital', 'Curso corto de marketing digital', 250, 'CURSO_CORTO');

INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (1, 1, TO_DATE('2026-01-15','YYYY-MM-DD'), 'PAGADA', 1200);
INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (2, 5, TO_DATE('2026-02-03','YYYY-MM-DD'), 'CONFIRMADA', 900);
INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (3, 1, TO_DATE('2026-02-20','YYYY-MM-DD'), 'PAGADA', 1550);
INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (4, 5, TO_DATE('2026-03-10','YYYY-MM-DD'), 'PENDIENTE', 1200);
INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (5, 2, TO_DATE('2026-03-22','YYYY-MM-DD'), 'PAGADA', 350);
INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (6, 1, TO_DATE('2026-04-05','YYYY-MM-DD'), 'CONFIRMADA', 800);
INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (7, 5, TO_DATE('2026-04-18','YYYY-MM-DD'), 'CANCELADA', 200);
INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (8, 2, TO_DATE('2026-05-02','YYYY-MM-DD'), 'PENDIENTE', 850);
INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (9, 3, TO_DATE('2026-05-08','YYYY-MM-DD'), 'PAGADA', 320);
INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total) VALUES (10, 1, TO_DATE('2026-05-15','YYYY-MM-DD'), 'CONFIRMADA', 250);

INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (1, 1, 1, 1200);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (2, 2, 1, 900);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (3, 3, 1, 1200);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (3, 5, 1, 350);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (4, 1, 1, 1200);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (5, 5, 1, 350);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (6, 6, 1, 800);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (7, 7, 1, 200);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (8, 8, 1, 850);
INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (9, 9, 1, 320);

COMMIT;
