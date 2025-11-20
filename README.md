# 👋 Bienvenido al proyecto API Rest Demo

Este repositorio contiene una API Rest desarrollada con **Spring Boot**, **Spring Security** y **JWT**, como parte de la guía práctica de la materia *Ingeniería en Desarrollo de Software*. El proyecto fue construido en **IntelliJ IDEA**, probado con **Postman**, y conectado a una base de datos **MariaDB local**.

---

## 🧠 ¿Qué hace esta API?

La API permite:

- Registrar usuarios
- Autenticarlos mediante login con JWT
- Consultar productos
- Registrar nuevos productos
- Cerrar sesión (logout)

Todo esto se realiza mediante endpoints REST protegidos por autenticación JWT.

---

## 🛠️ Requisitos previos

- Java 17 o superior
- IntelliJ IDEA (Community Edition)
- MariaDB instalado en Windows 11
- Postman (para pruebas)
- Git (para clonar el repositorio)

---

## 📦 Clonar y correr el proyecto en Windows 11

### 🔧 Paso 1: Clonar el repositorio

1. Abre Git Bash o CMD en Windows.
2. Ejecuta:

```bash
git clone https://github.com/tuusuario/apirestdemo.git
cd apirestdemo

🔧 Abrir el proyecto en IntelliJ IDEA

    Abre IntelliJ IDEA.

    Selecciona Open y busca la carpeta apirestdemo.

    Espera a que se sincronicen las dependencias Maven.

    Si aparece el mensaje “Trust Project”, haz clic en Trust.

🗃️ Crear la base de datos local en Windows 11 (MariaDB)
🔧 Paso 1: Instalar MariaDB

    Descarga desde: https://mariadb.org/download

    Instala y abre el cliente de consola o HeidiSQL.
Ejecutar el script SQL

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

⚙️ Configuración del archivo application.properties

Ubicado en src/main/resources/application.properties:

server.port=8085
server.servlet.context-path=/api/v1/demoapirestdam235

spring.datasource.url=jdbc:mariadb://localhost:3306/DBTmp
spring.datasource.username=dam235
spring.datasource.password=D@m235U35.
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

🚀 Ejecutar la aplicación

    En IntelliJ, abre la clase ApirestdemoApplication.java.

    Haz clic en el botón verde ▶️ para iniciar el servidor.

    Verifica que aparezca Tomcat started on port 8085.

📲 Endpoints disponibles
Método	Endpoint	      Descripción	                Requiere Token
POST	  /auth/register	Registro de usuario	              ❌
POST	  /auth/login	    Login y generación de token	      ❌
GET	    /products	      Consulta de productos	            ✅
POST	  /products	      Registro de producto	            ✅
POST	  /auth/logout	  Cierre de sesión	                ✅

🧪 Flujo de uso recomendado (Postman)

    Registrar usuario: POST /auth/register

    Login: POST /auth/login → copiar token

    Consultar productos: GET /products con token

    Agregar producto: POST /products con token

    Logout: POST /auth/logout con token

    Verificación: GET /products con token → debe fallar
