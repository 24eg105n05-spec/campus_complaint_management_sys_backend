# Campus Complaint Management System — Backend

Spring Boot REST API backend for the Campus Complaint Management System.

## Tech Stack

- **Java 21** + **Spring Boot 4.1.1**
- **Spring Security** + **JWT** (jjwt 0.12.6)
- **Spring Data JPA** + **MySQL**
- **Docker** (multi-stage build)

## API Endpoints

### Auth (Public)
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT |

### Complaints (JWT Required)
| Method | URL | Role | Description |
|--------|-----|------|-------------|
| GET | `/api/complaints` | Any | Students: own complaints; Maintenance: all |
| GET | `/api/complaints/{id}` | Any | Get single complaint |
| POST | `/api/complaints` | STUDENT | Submit new complaint |
| PUT | `/api/complaints/{id}/status` | MAINTENANCE | Update status |
| DELETE | `/api/complaints/{id}` | STUDENT | Delete own complaint |

### Complaint Status Values
`Pending` → `In Progress` → `Resolved`

## Local Development

### Prerequisites
- Java 21
- Maven 3.x
- MySQL 8.x

```bash
# Create the database
mysql -u root -p -e "CREATE DATABASE campus_complaint_db;"

# Set environment variables (or use defaults in application.properties)
export DB_URL=jdbc:mysql://localhost:3306/campus_complaint_db
export DB_USERNAME=root
export DB_PASSWORD=yourpassword
export JWT_SECRET=YourSecretKeyMustBeAtLeast32CharsLong

# Run the application
mvn spring-boot:run
```

## Deployment on Render

### Docker Web Service

| Setting | Value |
|---------|-------|
| **Environment** | Docker |
| **Dockerfile Path** | `./Dockerfile` |

### Required Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `DB_URL` | MySQL JDBC URL | `jdbc:mysql://host:3306/campus_complaint_db` |
| `DB_USERNAME` | MySQL username | `root` |
| `DB_PASSWORD` | MySQL password | `secret` |
| `JWT_SECRET` | JWT signing secret (min 32 chars) | `CampusCareProductionSecretKey2026!!` |
| `FRONTEND_URL` | Frontend origin for CORS | `https://your-frontend.onrender.com` |
| `PORT` | Server port (Render sets this) | `8080` |

> **Tip**: Use [Render's MySQL](https://render.com/docs/databases) or an external MySQL provider like [PlanetScale](https://planetscale.com) or [Railway](https://railway.app).

## Project Structure

```
src/main/java/com/campuscomplaint/campuscomplaintbackend/
├── config/
│   ├── JwtAuthenticationFilter.java
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   └── ComplaintController.java
├── dto/
│   ├── LoginRequest.java
│   └── RegisterRequest.java
├── entity/
│   ├── Complaint.java
│   └── User.java
├── repository/
│   ├── ComplaintRepository.java
│   └── UserRepository.java
├── service/
│   ├── AuthService.java
│   ├── ComplaintService.java
│   └── JwtService.java
└── CampusComplaintBackendApplication.java
```
