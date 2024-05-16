CREATE DATABASE IF NOT EXISTS secretariadesalud;
USE secretariadesalud;

GRANT ALL PRIVILEGES ON *.* TO 'root'@'proyectodistribuidos-servidor-1' IDENTIFIED BY 'Blaziquen_01' WITH GRANT OPTION;

CREATE TABLE Pacientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    curp VARCHAR(18),
    fechaNacimiento DATE,
    tutor VARCHAR(100),
    pass VARCHAR(20)
);

CREATE TABLE Expedientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    imagenes TEXT,
    textos TEXT,
    documentos TEXT,
    idPaciente INT,
    medicosAcceso TEXT,
    acceso BOOLEAN,
    FOREIGN KEY (idPaciente) REFERENCES Pacientes(id)
);

CREATE TABLE Medicos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cedula VARCHAR(20),
    nombre VARCHAR(100),
    pass VARCHAR(20), -- ¡Asegúrate de encriptar las contraseñas en un entorno real!
    especialidad VARCHAR(100)
);