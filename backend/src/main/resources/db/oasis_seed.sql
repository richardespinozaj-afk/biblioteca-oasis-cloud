DROP DATABASE IF EXISTS oasis;
CREATE DATABASE oasis CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE oasis;

-- =========================
-- TABLA USUARIOS (CON ROLES)
-- =========================
CREATE TABLE usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  usuario VARCHAR(50) NOT NULL UNIQUE,
  nombre VARCHAR(150) NOT NULL,
  correo VARCHAR(100) NOT NULL UNIQUE,
  clave VARCHAR(255) NOT NULL,
  rol ENUM('admin','bibliotecario','estudiante') DEFAULT 'estudiante',
  estado TINYINT(1) DEFAULT 1,
  fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- TABLA EMPRESA
-- =========================
CREATE TABLE empresa (
  id INT AUTO_INCREMENT PRIMARY KEY,
  ruc VARCHAR(20),
  nombre VARCHAR(150),
  telefono VARCHAR(20),
  correo VARCHAR(100),
  direccion VARCHAR(255)
);

-- =========================
-- TABLAS CATALOGOS
-- =========================
CREATE TABLE autor (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL
);

CREATE TABLE editorial (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL
);

CREATE TABLE materias (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL
);

-- =========================
-- TABLA LIBROS (INVENTARIO)
-- =========================
CREATE TABLE libros (
  id INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL,
  id_editorial INT,
  id_autor INT,
  id_materia INT,
  cantidad INT DEFAULT 0,
  stock_minimo INT DEFAULT 5,
  num_pag INT,
  anio_edicion INT,
  fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,

  FOREIGN KEY (id_editorial) REFERENCES editorial(id) ON DELETE SET NULL,
  FOREIGN KEY (id_autor) REFERENCES autor(id) ON DELETE SET NULL,
  FOREIGN KEY (id_materia) REFERENCES materias(id) ON DELETE SET NULL
);

-- =========================
-- TABLA ESTUDIANTES
-- =========================
CREATE TABLE estudiantes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  documento VARCHAR(20) UNIQUE,
  codigo VARCHAR(20),
  nombre VARCHAR(150),
  telefono VARCHAR(20),
  correo VARCHAR(100),
  carrera VARCHAR(150)
);

-- =========================
-- TABLA PRESTAMOS
-- =========================
CREATE TABLE prestamos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_estudiante INT,
  id_libro INT,
  cantidad INT NOT NULL,
  fecha_prestamo DATE,
  fecha_devolucion DATE,
  estado ENUM('activo','devuelto','retrasado') DEFAULT 'activo',

  FOREIGN KEY (id_estudiante) REFERENCES estudiantes(id) ON DELETE CASCADE,
  FOREIGN KEY (id_libro) REFERENCES libros(id) ON DELETE CASCADE
);

-- =========================
-- TABLA MOVIMIENTOS (AUDITORIA)
-- =========================
CREATE TABLE movimientos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_libro INT,
  tipo ENUM('prestamo','devolucion','venta','ajuste') NOT NULL,
  cantidad INT NOT NULL,
  fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
  id_usuario INT,

  FOREIGN KEY (id_libro) REFERENCES libros(id),
  FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- =========================
-- TABLA VENTAS
-- =========================
CREATE TABLE ventas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
  total DECIMAL(10,2) DEFAULT 0,
  id_usuario INT,

  FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- =========================
-- DETALLE DE VENTAS
-- =========================
CREATE TABLE detalle_ventas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_venta INT,
  id_libro INT,
  cantidad INT,
  precio DECIMAL(10,2),

  FOREIGN KEY (id_venta) REFERENCES ventas(id) ON DELETE CASCADE,
  FOREIGN KEY (id_libro) REFERENCES libros(id)
);

-- =====================================================
-- DATOS DE PRUEBA (CONTRASENAS ENCRIPTADAS CON BCRYPT)
-- =====================================================

-- Usuarios de prueba
-- admin123    -> $2b$10$SiE45yfnU8bqdQ7EAMKH5OkX8xl8mqyT3KSyD6VjHsgLSMf21CkvC
-- estudiante123 -> $2b$10$M8NOQ6E/N/KgMhataM1tE.F9lgV6vICDQVkX9B0egwJKkgw5.kuS6
-- bibliotecario123 -> $2b$10$UzQE/GNS3S2Od5hcqpMrfukZeJQdOfYTnSx1XjT5DlDVQcyk7RIMu

INSERT INTO usuarios (usuario, nombre, correo, clave, rol) VALUES
('admin', 'Administrador', 'admin@biblioteca.com', '$2b$10$SiE45yfnU8bqdQ7EAMKH5OkX8xl8mqyT3KSyD6VjHsgLSMf21CkvC', 'admin'),
('bibliotecario', 'Bibliotecario Principal', 'biblio@biblioteca.com', '$2b$10$UzQE/GNS3S2Od5hcqpMrfukZeJQdOfYTnSx1XjT5DlDVQcyk7RIMu', 'bibliotecario'),
('estudiante', 'Juan Perez', 'juan@utp.edu.pe', '$2b$10$M8NOQ6E/N/KgMhataM1tE.F9lgV6vICDQVkX9B0egwJKkgw5.kuS6', 'estudiante'),
('maria', 'Maria Garcia', 'maria@utp.edu.pe', '$2b$10$M8NOQ6E/N/KgMhataM1tE.F9lgV6vICDQVkX9B0egwJKkgw5.kuS6', 'estudiante');

-- Empresa
INSERT INTO empresa (ruc, nombre, telefono, correo, direccion) VALUES
('20548796532', 'Biblioteca Oasis - UTP', '01-555-1234', 'contacto@biblioteca-oasis.edu.pe', 'Av. Javier Prado Este 1234, Lima');

-- Autores
INSERT INTO autor (nombre) VALUES
('Robert C. Martin'), ('Donald Knuth'), ('Ian Sommerville'), ('Martin Fowler'),
('Andrew Tanenbaum'), ('James Clear'), ('Stephen Hawking'), ('Gabriel Garcia Marquez'),
('J.K. Rowling'), ('George Orwell');

-- Editoriales
INSERT INTO editorial (nombre) VALUES
('Pearson'), ('McGraw-Hill'), ('O Reilly Media'), ('Addison-Wesley'),
('Springer'), ('Planeta'), ('Penguin Random House'), ('Anagrama');

-- Materias
INSERT INTO materias (nombre) VALUES
('Algoritmos'), ('Ingenieria de Software'), ('Redes'), ('Ciencia de Datos'),
('Fisica'), ('Productividad'), ('Literatura'), ('Historia');

-- Libros
INSERT INTO libros (titulo, id_editorial, id_autor, id_materia, cantidad, stock_minimo, num_pag, anio_edicion) VALUES
('Clean Code', 1, 1, 2, 12, 3, 464, 2008),
('The Art of Computer Programming Vol 1', 2, 2, 1, 8, 2, 672, 1997),
('Software Engineering', 1, 3, 2, 15, 5, 792, 2015),
('Refactoring', 4, 4, 2, 10, 3, 431, 1999),
('Computer Networks', 1, 5, 3, 20, 5, 960, 2010),
('Atomic Habits', 6, 6, 6, 30, 10, 320, 2018),
('A Brief History of Time', 5, 7, 5, 18, 5, 256, 1988),
('Cien años de soledad', 7, 8, 7, 25, 8, 417, 1967),
('Harry Potter y la piedra filosofal', 8, 9, 7, 40, 10, 223, 1997),
('1984', 7, 10, 7, 22, 5, 328, 1949);

-- Estudiantes
INSERT INTO estudiantes (documento, codigo, nombre, telefono, correo, carrera) VALUES
('12345678', 'EST001', 'Juan Perez', '987654321', 'juan@utp.edu.pe', 'Ingenieria Informatica'),
('87654321', 'EST002', 'Maria Garcia', '912345678', 'maria@utp.edu.pe', 'Ingenieria de Sistemas'),
('45678912', 'EST003', 'Carlos Lopez', '934567890', 'carlos@utp.edu.pe', 'Ingenieria Informatica');
