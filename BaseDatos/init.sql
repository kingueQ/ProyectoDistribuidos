CREATE DATABASE IF NOT EXISTS secretariadesalud;

-- Evita recrear el usuario root si ya existe
SET @existing_user = (SELECT COUNT(*) FROM mysql.user WHERE user = 'root' AND host = '%');
SET @create_user = IF(@existing_user = 0, 'CREATE USER ''root''@''%'' IDENTIFIED BY ''Blaziquen_01'';', 'SELECT ''User already exists.'';');
PREPARE stmt FROM @create_user;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

GRANT ALL PRIVILEGES ON secretariadesalud.* TO 'root'@'%';
FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS Pacientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    curp VARCHAR(18),
    fechaNacimiento DATE,
    tutor VARCHAR(100),
    pass VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS Expedientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    imagenes TEXT,
    textos TEXT,
    documentos TEXT,
    idPaciente INT,
    medicosAcceso TEXT,
    acceso BOOLEAN,
    FOREIGN KEY (idPaciente) REFERENCES Pacientes(id)
);

CREATE TABLE IF NOT EXISTS Medicos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cedula VARCHAR(20),
    nombre VARCHAR(100),
    pass VARCHAR(20), -- ¡Asegúrate de encriptar las contraseñas en un entorno real!
    especialidad VARCHAR(100)
);
