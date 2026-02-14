# Spring Security Practice

A RESTful API built with Spring Boot, Spring Security, PostgreSQL, MyBatis, Redis sessions, and Lombok.

## Features

- User authentication (signup, login, logout)
- User profile management
- Session management with Redis
- PostgreSQL database with Flyway migrations
- RESTful API endpoints

## Tech Stack

- **Spring Boot 3.2.0** - Application framework
- **Spring Security** - Authentication and authorization
- **PostgreSQL** - Relational database
- **MyBatis** - SQL mapper framework
- **Redis** - Session store
- **Flyway** - Database migrations
- **Lombok** - Reduce boilerplate code
- **Maven** - Build tool

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+ running on localhost:5432
- Redis running on localhost:6379

## Setup

### 1. Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE security_practice;
```

The application will automatically run Flyway migrations on startup to create the necessary tables.

### 2. Redis Setup

Make sure Redis is running:

```bash
redis-server
```

### 3. Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080/api`

## API Endpoints

### Authentication

#### Signup
```http
POST /api/auth/signup
Content-Type: application/json

{
  "username": "johndoe",
  "password": "password123"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "johndoe",
  "password": "password123"
}
```

#### Logout
```http
POST /api/auth/logout
```

### Profile Management (Requires Authentication)

#### Get Profile
```http
GET /api/profile
```

Response:
```json
{
  "success": true,
  "message": "Profile retrieved successfully",
  "data": {
    "username": "johndoe",
    "favoriteTechnologies": ["Java", "Spring Boot", "PostgreSQL"],
    "favoriteVideoGames": ["The Witcher 3", "Elden Ring"],
    "favoriteBooks": ["Clean Code", "Design Patterns"]
  }
}
```

#### Update Profile
```http
PUT /api/profile
Content-Type: application/json

{
  "favoriteTechnologies": ["Java", "Spring Boot", "PostgreSQL", "Redis"],
  "favoriteVideoGames": ["The Witcher 3", "Elden Ring", "Baldur's Gate 3"],
  "favoriteBooks": ["Clean Code", "Design Patterns", "Refactoring"]
}
```

## Database Schema

### Users Table
- `id` - Primary key
- `username` - Unique username
- `password` - Encrypted password
- `enabled` - Account status
- `created_at` - Creation timestamp
- `updated_at` - Last update timestamp

### Profiles Table
- `id` - Primary key
- `user_id` - Foreign key to users table
- `favorite_technologies` - Array of technologies
- `favorite_video_games` - Array of video games
- `favorite_books` - Array of books
- `created_at` - Creation timestamp
- `updated_at` - Last update timestamp

## Configuration

The application can be configured via `src/main/resources/application.yml`:

- Database connection settings
- Redis connection settings
- Session timeout
- Server port

## Security

- Passwords are encrypted using BCrypt
- Sessions are stored in Redis
- CSRF protection is enabled using cookie-based tokens
- All endpoints except signup and login require authentication
- CSRF token is available in the `XSRF-TOKEN` cookie and should be included in the `X-XSRF-TOKEN` header for state-changing requests