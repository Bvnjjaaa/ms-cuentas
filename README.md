# ms-cuentas — Microservicio de Cuentas

Microservicio correspondiente al **caso NovaBank** (Banca digital / fintech) de la **Evaluación Parcial N°2**.

| | |
|---|---|
| **Asignatura** | JVY0101 — Java: Diseño y Construcción de Soluciones Nativas en Nube |
| **Stack** | Spring Boot 3.2.5 · Java 17 · Maven Wrapper · Spring Data JPA · H2 Database |
| **Arquitectura** | Capas (Controller, Service, Repository, Model) |
| **Puerto** | 8082 |

## Responsabilidad (SRP)

Administra las cuentas bancarias de NovaBank: su creación (con número único y saldo inicial no negativo), la consulta por número o por RUT del titular, la actualización de saldo mediante operaciones DEBITO/CREDITO (validando saldo insuficiente y montos positivos) y el cambio de estado entre ACTIVA y BLOQUEADA. Su base de datos es una **H2 en memoria** (`jdbc:h2:mem:novabank_accounts_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`), cumpliendo aislamiento de datos por dominio.

## Consola y Persistencia

Al ejecutar el servicio en el puerto `8082`, se puede acceder a la consola de persistencia H2:

- **H2 Console**: `http://localhost:8082/h2-console`
- **JDBC URL**: `jdbc:h2:mem:novabank_accounts_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
- **Usuario**: `sa`
- **Contraseña**: *(en blanco)*

## Endpoints Principales (`/api/cuentas`)

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/cuentas` | Crea una cuenta validando campos obligatorios, número único y saldo inicial no negativo; responde 201. |
| GET | `/api/cuentas` | Lista todas las cuentas registradas. |
| GET | `/api/cuentas/{numeroCuenta}` | Obtiene una cuenta por su número; lanza 404 si no existe. |
| GET | `/api/cuentas/titular/{rut}` | Lista todas las cuentas asociadas a un RUT de titular. |
| PUT | `/api/cuentas/{numeroCuenta}/saldo` | Actualiza el saldo aplicando una operación (`monto`, `operacion=DEBITO` o `CREDITO`); valida monto mayor a cero y saldo suficiente en débitos. |
| PUT | `/api/cuentas/{numeroCuenta}/estado` | Cambia el estado de la cuenta a `ACTIVA` o `BLOQUEADA` (valida el estado recibido). |

## Instrucciones de Clonación, Compilación y Ejecución Local

1. **Clonar el repositorio:**
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd ms-cuentas
   ```

2. **Compilar y empaquetar con Maven Wrapper:**
   ```bash
   .\mvnw.cmd clean package -DskipTests
   ```

3. **Ejecutar el ejecutable .jar generado:**
   ```bash
   java -jar target/ms-cuentas-1.0.0.jar
   ```

## Cómo ejecutar las pruebas

```bash
.\mvnw.cmd test
```
