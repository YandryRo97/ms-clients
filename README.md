# ms-clients

Microservicio **Clientes** desarrollado en **Java 21** y **Spring Boot 3.4.9**, con persistencia en **PostgreSQL**, comunicación asíncrona vía **RabbitMQ**, y empaquetado en contenedor **Docker**.  

Este servicio forma parte del sistema bancario y expone operaciones CRUD para la gestión de clientes, además de integración para creación de cuentas.

---

## Tecnologías usadas

- **Java**: 21  
- **Spring Boot**: 3.4.9  
  - Spring WebFlux (reactivo)  
  - Spring Data JPA (persistencia)  
  - Spring Validation (validaciones de entidades)  
- **PostgreSQL**: 16+  
- **RabbitMQ**: 3.13+  
- **Docker**: 24+  
- **Gradle**: 8.10+  
- **Lombok**: para reducir boilerplate  
- **MapStruct**: para mapeo de entidades a DTOs  

---

## ⚙️ Configuración de la base de datos

El microservicio requiere una base de datos PostgreSQL con credenciales:

- **Base de datos**: `bank-clients`  
- **Usuario**: `postgres`  
- **Password**: `tarpuqPass`  
- **Host**: `localhost` (o `host.docker.internal` si se levanta desde Docker)  
- **Puerto**: `5432`

## 📦 Compilación y empaquetado

./gradlew clean bootJar

## 🐳 Docker

docker build -t ms-clients:latest .

## 📡 Endpoints principales
Crear cliente

POST /clientes
{
  "nombre": "Jose Lema",
  "genero": "M",
  "edad": 30,
  "identificacion": "1234567890",
  "direccion": "Otavalo sn y principal",
  "telefono": "098254785",
  "contrasena": "1234",
  "estado": true
}

Crear cliente con cuenta

POST /clientes/cuenta


{
  "cliente": {
    "nombre": "Marianela Montalvo",
    "genero": "F",
    "edad": 28,
    "identificacion": "9876543210",
    "direccion": "Amazonas y NNUU",
    "telefono": "097548965",
    "contrasena": "5678",
    "estado": true
  },
  "numeroCuenta": "123-456-789",
  "tipoCuenta": "AHORROS",
  "saldoInicial": 1000.0,
  "estado": true
}


Listar clientes

GET /clientes

Buscar por clienteId

GET /clientes/{clienteId}

Actualizar cliente

PUT /clientes/{clienteId}

Eliminar cliente

DELETE /clientes/{clienteId}

## ✨ Notas

El campo clienteId se genera automáticamente en el backend si no se envía.

Las contraseñas se manejan en texto plano para fines de prueba, pero deben ser hasheadas en un entorno real.

RabbitMQ se usa para enviar eventos cuando se crea un cliente con cuenta.

Redis está configurado en application.properties pero puede omitirse si no lo usas.