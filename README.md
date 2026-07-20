# CeluCheck

## Documentación técnica

- [Informe técnico completo](docs/Documentacion_Tecnica_CeluCheck.docx)

Comparador de precios de celulares desarrollado con Java 17, Spring Boot, MySQL, Flyway, Spring Security, Thymeleaf y Tailwind CSS.

## Funcionalidades

- Catálogo responsive con búsqueda, marca y precio máximo.
- Comparación de ofertas de Falabella, Ripley y Oechsle.
- Detalle técnico, ahorro, redirección a tienda y medición de clicks.
- Registro, inicio de sesión, roles `USER`/`ADMIN` y favoritos.
- Asistente de recomendación por presupuesto, cámara, batería y uso.
- Panel administrativo con métricas, CRUD lógico de equipos y gestión de usuarios.
- Esquema, índices, relaciones y datos iniciales versionados con Flyway.

## Requisitos

- Java 17+
- Maven 3.9+
- MySQL 8.0+ (la instancia local actual es MySQL 9.5)

## Inicio rápido con MySQL

La aplicación usa estos valores por defecto:

```text
base: celucheck
usuario: root
contraseña: root
puerto: 3306
```

Ejecuta:

```powershell
mvn spring-boot:run
```

Luego abre `http://localhost:8080`. En el primer arranque, Flyway ejecuta automáticamente `V1__create_schema.sql` y `V2__seed_catalog.sql`.

Para otras credenciales:

```powershell
$env:DB_URL='jdbc:mysql://localhost:3306/celucheck?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Lima'
$env:DB_USERNAME='root'
$env:DB_PASSWORD='tu-clave'
mvn spring-boot:run
```

## Perfil demo sin MySQL

```powershell
mvn spring-boot:run "-Dspring-boot.run.profiles=demo"
```

## Cuentas iniciales

- Administrador: `admin@celucheck.pe` / `Admin123!`
- Usuario: `mirko@correo.com` / `Celu123!`

Todas las contraseñas, incluidas las cuentas demo, se almacenan con BCrypt y factor de trabajo 12. Las credenciales iniciales deben rotarse antes de un despliegue público.

## Construcción de estilos

El CSS de Tailwind se entrega compilado y minificado; la aplicación no usa el Play CDN. Para regenerarlo después de modificar plantillas:

```powershell
npm install
npm run build:css
```

## Pruebas

```powershell
mvn test
```

## Swagger / OpenAPI

- Interfaz Swagger UI: `http://localhost:8080/swagger-ui.html`
- Especificación JSON: `http://localhost:8080/v3/api-docs`
- Producción: `https://celucheck.onrender.com/swagger-ui.html`

Swagger documenta las operaciones REST de catálogo, asistente y favoritos. La operación de favoritos requiere una sesión autenticada y protección CSRF.

> Los precios y enlaces precargados son referenciales. Un despliegue real debe incorporar un proceso autorizado de sincronización con las tiendas.
