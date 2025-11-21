-- 🎯 Script para crear la base de datos local en MariaDB
-- ⚠️ Ejecutar en Windows 11 con cliente MariaDB o HeidiSQL

CREATE DATABASE DBTmp;
USE DBTmp;

CREATE TABLE User (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(100),
                      lastName VARCHAR(100),
                      email VARCHAR(100) UNIQUE,
                      password VARCHAR(255),
                      active BOOLEAN
);

CREATE TABLE Product (
                         Code INT PRIMARY KEY AUTO_INCREMENT,
                         Name VARCHAR(100),
                         Status BOOLEAN
);

CREATE USER 'dam235'@'localhost' IDENTIFIED BY 'D@m235U35.';
GRANT ALL PRIVILEGES ON DBTmp.* TO 'dam235'@'localhost';
FLUSH PRIVILEGES;
