# 👥 UserManagementAPI

A secure and scalable RESTful API for user management built with **Spring Boot** and **Spring Security**. This API supports user registration, authentication, role-based access control, and CRUD operations on users.

---

## 🚀 Features

- ✅ User Registration & Login
- 🔐 Secure Authentication (HTTP Basic Auth)
- 👤 Role-based Authorization (`USER`, `ADMIN`)
- 🔒 Password Encryption using BCrypt
- 🌍 CORS Configuration for cross-origin requests
- 🔁 Stateless Session Management
- 📄 REST API with clear endpoints
- 📦 Modular codebase with services, controllers, DTOs

---

## 🧱 Project Structure

  userManagementAPI/
├── config/ # Spring Security Configuration
├── controller/ # REST Controllers
├── service/ # Business Logic Layer
├── repository/ # JPA Repositories
├── model/ # Entity Classes (User, Role)
├── dto/ # DTOs for Requests & Responses
└── UserManagementApiApplication.java



---

## 🔧 Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- H2 / MySQL
- Lombok
- Postman (for API testing)
- Git + GitHub

---

## 🧪 API Endpoints

| Method | Endpoint                  | Role Access    | Description                  |
|--------|---------------------------|----------------|------------------------------|
| POST   | `/api/users/register`     | Public         | Register a new user          |
| GET    | `/api/users`              | ADMIN          | Fetch all users              |
| GET    | `/api/users/{id}`         | USER, ADMIN    | Fetch user by ID             |
| PUT    | `/api/users/{id}`         | USER, ADMIN    | Update user by ID            |
| DELETE | `/api/users/{id}`         | ADMIN          | Delete user by ID            |
| GET    | `/api/users/page`         | Public         | Paginated user fetch (test)  |









