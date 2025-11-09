# Prescription Management System

A complete Java Spring Boot web application for managing medical prescriptions with authentication, CRUD operations, reporting, and REST API.

## Technology Stack

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Spring Security**: Form-based authentication
- **Spring Data JPA**: Data persistence
- **H2 Database**: In-memory database
- **Thymeleaf**: Server-side templating
- **Maven**: Build tool
- **Lombok**: Code generation

## Features

### 1. Authentication & Security

- Spring Security with form-based login
- H2 database for user storage
- No anonymous access - all routes protected
- Redirects to `/login` for unauthenticated users
- Auto-redirect to `/dashboard` after successful login

### 2. Prescription CRUD Operations

- **Create**: Add new prescriptions with validation
- **Read**: View prescription details
- **Update**: Edit existing prescriptions
- **Delete**: Remove prescriptions with confirmation dialog

#### Entity Fields:

- `id` (Long, auto-generated)
- `prescriptionDate` (LocalDate, required)
- `patientName` (String, 2-100 chars, required)
- `patientAge` (Integer, 0-120, required)
- `patientGender` (Enum: MALE/FEMALE/OTHER, required)
- `diagnosis` (String, max 2000 chars, optional)
- `medicines` (String, max 2000 chars, optional)
- `nextVisitDate` (LocalDate, optional)

### 3. List & Filter

- Dashboard displays prescriptions in a table
- Date-range filter (defaults to current month)
- Pagination support
- Sorted by prescription date (descending)

### 4. Validation

- **Server-side**: Bean Validation annotations
- **Client-side**: HTML5 + JavaScript validation
- Clear error messages on form submission

### 5. Reporting

- Day-wise prescription count for selected month
- Shows `[day, count]` data in tabular format
- Filter by year and month

### 6. REST API

- **Endpoint**: `GET /api/v1/prescriptions`
- Paginated JSON response
- DTOs exclude internal fields (createdDate, modifiedDate)
- Consistent error response format

### 7. External API Integration

- **RxNav API**: Drug interaction lookup
- **Endpoint**: `GET /api/v1/rxnav/interactions/{rxcui}`
- Example: `/api/v1/rxnav/interactions/341248`

## Project Structure

```
src/main/java/com/prescription/management/
├── PrescriptionManagementApplication.java
├── config/
│   └── DataInitializer.java
├── controller/
│   ├── api/
│   │   ├── PrescriptionRestController.java
│   │   └── RxNavRestController.java
│   └── web/
│       ├── AuthController.java
│       ├── DashboardController.java
│       ├── PrescriptionController.java
│       └── ReportController.java
├── dto/
│   ├── PrescriptionDTO.java
│   ├── DayCountDTO.java
│   └── ErrorResponseDTO.java
├── entity/
│   ├── User.java
│   ├── Prescription.java
│   └── Gender.java
├── exception/
│   └── GlobalExceptionHandler.java
├── mapper/
│   └── PrescriptionMapper.java
├── repository/
│   ├── UserRepository.java
│   └── PrescriptionRepository.java
├── security/
│   ├── SecurityConfig.java
│   └── CustomUserDetailsService.java
└── service/
    ├── UserService.java
    ├── PrescriptionService.java
    └── RxNavService.java

src/main/resources/
├── application.properties
├── templates/
│   ├── login.html
│   ├── dashboard.html
│   ├── prescription-form.html
│   ├── prescription-view.html
│   └── report-daywise.html
└── static/
    ├── css/
    │   └── style.css
    └── js/
        └── validation.js
```

## Building and Running

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Build

```bash
mvn clean install
```

### Run

```bash
mvn spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/prescription-management-1.0.0.jar
```

### Access

- **Application**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:prescriptiondb`
  - Username: `sa`
  - Password: (leave blank)

## Demo Credentials

| Username | Password  | Role |
| -------- | --------- | ---- |
| admin    | admin123  | USER |
| doctor   | doctor123 | USER |

## API Endpoints

### REST API

- `GET /api/v1/prescriptions?page=0&size=10&sortBy=prescriptionDate&sortDir=desc`

  - Returns paginated prescriptions in JSON format

- `GET /api/v1/prescriptions/{id}`

  - Returns a single prescription by ID

- `GET /api/v1/rxnav/interactions/{rxcui}`
  - Returns drug interactions from RxNav API

### Web Endpoints

- `GET /login` - Login page
- `GET /dashboard` - Main dashboard with prescription list
- `GET /prescriptions/new` - Create prescription form
- `GET /prescriptions/{id}/edit` - Edit prescription form
- `GET /prescriptions/{id}/view` - View prescription details
- `POST /prescriptions/{id}/delete` - Delete prescription
- `GET /reports/daywise` - Day-wise prescription report

## Validation Rules

### Prescription Form

- Prescription Date: Required
- Patient Name: Required, 2-100 characters
- Patient Age: Required, 0-120
- Patient Gender: Required (MALE/FEMALE/OTHER)
- Diagnosis: Optional, max 2000 characters
- Medicines: Optional, max 2000 characters
- Next Visit Date: Optional

## Architecture

### Clean Architecture Pattern

```
Controller Layer → Service Layer → Repository Layer → Entity Layer
```

- **Controllers**: Handle HTTP requests and responses
- **Services**: Business logic and transaction management
- **Repositories**: Data access using Spring Data JPA
- **Entities**: Database models with JPA annotations
- **DTOs**: Data transfer objects for API responses
- **Mappers**: Convert between entities and DTOs

### Security

- BCrypt password encoding
- Spring Security filter chain
- Form-based authentication
- Session management

## Database Schema

### Users Table

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL
);
```

### Prescriptions Table

```sql
CREATE TABLE prescriptions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    prescription_date DATE NOT NULL,
    patient_name VARCHAR(100) NOT NULL,
    patient_age INTEGER NOT NULL,
    patient_gender VARCHAR(10) NOT NULL,
    diagnosis VARCHAR(2000),
    medicines VARCHAR(2000),
    next_visit_date DATE,
    created_date DATE NOT NULL,
    modified_date DATE NOT NULL
);
```

## Sample Data

The application initializes with:

- 2 demo users (admin, doctor)
- 5 sample prescriptions with realistic data

## Error Handling

Global exception handler provides consistent error responses:

```json
{
  "timestamp": "2025-11-06T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation error message",
  "details": ["Field errors"]
}
```
