# Biblioteca Oasis - Sistema Cloud GCP

Proyecto de Integracion de Servicios Cloud GCP - UTP 2026-2.
Migracion del sistema de biblioteca desde PHP hacia Spring Boot + Angular + GCP.

## Requisitos previos

Instalar las siguientes herramientas en tu PC (Windows):

| Herramienta | Version | Descarga |
|-------------|---------|----------|
| Java JDK | 17+ | https://adoptium.net |
| Maven | 3.9+ | https://maven.apache.org/download.cgi |
| Node.js | 18 LTS | https://nodejs.org |
| MySQL Server | 8.0 | https://dev.mysql.com/downloads/installer |
| Git | - | https://git-scm.com/downloads |
| IntelliJ IDEA | Community | https://jetbrains.com/idea/download |
| VS Code | - | https://code.visualstudio.com |

### Verificar instalacion

Abrir Git Bash o CMD y ejecutar:

```bash
java -version
mvn -version
node -v
git --version
```

### Instalar Angular CLI

```bash
npm install -g @angular/cli
ng version
```

---

## Configurar la base de datos

1. Abrir **MySQL Workbench**
2. Crear una nueva conexion local (puerto 3306, usuario root)
3. Ejecutar el script SQL que esta en la carpeta `scripts/`:
   ```sql
   CREATE DATABASE oasis CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   USE oasis;
   -- Luego copiar y pegar el contenido de scripts/oasis_seed.sql
   ```

La base de datos se llamara `oasis` y ya incluye datos de prueba con contrasenas encriptadas.

---

## Estructura del proyecto

```
biblioteca-cloud-utp/
├── backend/              # API REST Spring Boot
│   ├── src/main/java/    # Codigo fuente Java
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── pom.xml
│   └── Dockerfile
├── frontend/             # Aplicacion Angular (proximamente)
├── docker-compose.yml    # Levantar todo en local (proximamente)
├── k8s/                  # Manifiestos Kubernetes (proximamente)
└── docs/                 # Documentacion del proyecto
```

---

## Levantar el backend (Spring Boot)

### Opcion A: Desde IntelliJ IDEA

1. Abrir IntelliJ IDEA
2. File > Open > Seleccionar la carpeta `backend/`
3. Esperar a que Maven descargue las dependencias (barra de progreso abajo)
4. Buscar la clase `BibliotecaOasisApplication.java`
5. Clic derecho > Run
6. La API estara en: http://localhost:8080/api

### Opcion B: Desde terminal

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Probar la API

- Navegador: http://localhost:8080/api/libros
- Postman: POST http://localhost:8080/api/auth/login
  ```json
  {
    "usuario": "admin",
    "clave": "admin123"
  }
  ```

---

## Configuracion de la BD en application.properties

Si tu MySQL tiene contrasena diferente, editar:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/oasis?useSSL=false&serverTimezone=America/Lima&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=TU_CLAVE_AQUI
```

---

## Levantar el frontend (Angular)

```bash
cd frontend
npm install
ng serve
```

La aplicacion estara en: http://localhost:4200

---

## Docker (para PC3)

Instalar Docker Desktop: https://www.docker.com/products/docker-desktop

```bash
# Construir imagen del backend
cd backend
docker build -t biblioteca-backend .

# O levantar todo con docker-compose
docker-compose up --build
```

---

## GCP - Credenciales de estudiante

1. Crear cuenta en https://cloud.google.com con correo .edu
2. Activar Free Trial ($300 creditos / 90 dias)
3. Crear proyecto: `biblioteca-utp-2026`
4. Instalar gcloud CLI: https://cloud.google.com/sdk/docs/install

```bash
gcloud auth login
gcloud config set project biblioteca-utp-2026
```

---

## Equipo de desarrollo

- Proyecto: Biblioteca Oasis
- Curso: Integracion de Servicios Cloud GCP
- Universidad: UTP (Universidad Tecnologica del Peru)
- Ciclo: 2026-2

---

## Notas importantes

- El backend usa **JWT** para autenticacion. Cada peticion protegida debe incluir el header:
  ```
  Authorization: Bearer TU_TOKEN_JWT
  ```
- Las contrasenas se almacenan con **BCrypt** (no en texto plano).
- El CORS esta configurado para aceptar peticiones desde `http://localhost:4200`.
