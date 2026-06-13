# Hospital Management System

A role-based Hospital Management System built using Spring Boot, Spring Security, Spring Data JPA, Thymeleaf, and MySQL. The application enables patients, doctors, and receptionists to manage appointments, prescriptions, schedules, and hospital operations through dedicated portals.

## Features

### Patient Portal
- Patient authentication and login
- Book appointments with doctors
- View appointment history
- Cancel appointments
- View prescriptions issued by doctors

### Doctor Portal
- Doctor authentication and login
- View assigned appointments
- Manage appointment schedules
- Create and update prescriptions
- Calendar-based schedule management

### Receptionist Portal
- Receptionist authentication and login
- View all appointments
- Confirm patient appointments
- Generate invoices
- Manage appointment workflow

### Calendar Management
- Interactive scheduling system
- Doctor availability management
- Event scheduling and tracking
- Appointment calendar integration

### Security
- Spring Security-based authentication
- Role-based authorization
- Separate access control for Patients, Doctors, and Receptionists

---

## Technology Stack

### Backend
- Java 8
- Spring Boot 2.2.4
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate

### Frontend
- Thymeleaf
- HTML
- CSS
- JavaScript
- FullCalendar

### Database
- MySQL 8

### Build Tool
- Maven

---

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/company/varnaa/
│   │       ├── Controllers
│   │       ├── Services
│   │       ├── Repositories
│   │       ├── Entities
│   │       └── Security Configuration
│   │
│   └── resources/
│       ├── templates/
│       ├── static/
│       └── application.properties
```

## Database Configuration

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=create
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

---

## Installation & Setup

### 1. Clone Repository

```bash
git clone https://github.com/yourusername/hospital-management-system.git
cd HospitalManagementSystem
```

### 2. Create Database

```sql
CREATE DATABASE hospital;
```

### 3. Configure MySQL

Update credentials inside:

```text
src/main/resources/application.properties
```

### 4. Build Project

```bash
mvn clean install
```

### 5. Run Application

```bash
mvn spring-boot:run
```

Application will start at:

```text
http://localhost:8080
```

---

## User Roles

| Role | Features |
|--------|----------|
| Patient | Book appointments, view prescriptions, cancel appointments |
| Doctor | View appointments, create prescriptions, manage schedules |
| Receptionist | Confirm appointments, manage appointments, create invoices |

---

## Key Modules

### Appointment Management
- Appointment booking
- Appointment confirmation
- Appointment cancellation
- Doctor-wise appointment tracking

### Prescription Management
- Create prescriptions
- View prescriptions
- Associate prescriptions with appointments

### Schedule Management
- Calendar integration
- Event management
- Doctor schedule tracking

### Invoice Management
- Invoice generation
- Patient billing records

---

## Screenshots

### Home Page
Role-based login portal for Patients, Doctors, and Receptionists.

### Patient Dashboard
Manage appointments and view prescriptions.

### Doctor Dashboard
Access schedules and manage prescriptions.

### Receptionist Dashboard
Confirm appointments and generate invoices.

### Patient Registration
Patient signup and onboarding form.

---

## Learning Outcomes

- Spring Boot Application Development
- Spring Security Authentication & Authorization
- MVC Architecture
- RESTful Design Principles
- Hibernate ORM
- MySQL Database Integration
- Role-Based Access Control
- Maven Build Management

---

## Future Enhancements

- JWT Authentication
- Email Notifications
- Online Payments
- Patient Medical History
- Doctor Availability Analytics
- Responsive UI Design
- Docker Deployment
- Cloud Deployment (AWS)

---

## Author

**Paarth Karingula**

GitHub: https://github.com/paarthkaringula
