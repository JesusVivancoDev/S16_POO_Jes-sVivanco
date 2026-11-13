SET NAMES utf8mb4;
CREATE DATABASE IF NOT EXISTS empresa_seguridad
    CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE empresa_seguridad;

CREATE TABLE IF NOT EXISTS personal (
    id_personal INT AUTO_INCREMENT PRIMARY KEY,
    nombres     VARCHAR(80)  NOT NULL,
    apellidos   VARCHAR(80)  NOT NULL,
    dni         CHAR(8)      NOT NULL UNIQUE,
    telefono    VARCHAR(15),
    cargo       VARCHAR(50)  NOT NULL,
    turno       VARCHAR(20)  NOT NULL,
    estado      VARCHAR(20)  NOT NULL DEFAULT 'Activo'
);

INSERT INTO personal (nombres, apellidos, dni, telefono, cargo, turno, estado) VALUES
('Carlos', 'Quispe Mamani', '45871236', '987654321', 'Vigilante',  'Diurno',   'Activo'),
('Luis',   'Torres Ramos',  '70123458', '912345678', 'Supervisor', 'Nocturno', 'Activo'),
('María',  'Flores Díaz',   '41236987', '934567812', 'Vigilante',  'Nocturno', 'Inactivo'),
('Pedro',  'Huamán Soto',   '43219876', '956781234', 'Guardia',    'Mixto',    'Activo'),
('Ana',    'Rojas Vega',    '72345619', '945612378', 'Operador de monitoreo', 'Diurno', 'Activo');
