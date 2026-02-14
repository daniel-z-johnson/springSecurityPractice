# API Testing Guide

This guide provides example `curl` commands to test the API endpoints.

## Prerequisites

1. Start PostgreSQL and Redis using Docker Compose:
```bash
docker-compose up -d
```

2. Start the Spring Boot application:
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080/api`

## Testing the API

### 1. Sign Up a New User

```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "password": "password123"
  }'
```

Expected Response:
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": null
}
```

### 2. Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -c cookies.txt \
  -d '{
    "username": "johndoe",
    "password": "password123"
  }'
```

Expected Response:
```json
{
  "success": true,
  "message": "Login successful",
  "data": null
}
```

**Important**: The `-c cookies.txt` flag saves the session cookie to a file. This cookie will be used for authenticated requests.

### 3. Get Profile (Authenticated)

```bash
curl -X GET http://localhost:8080/api/profile \
  -b cookies.txt \
  -H "X-XSRF-TOKEN: $(grep XSRF-TOKEN cookies.txt | cut -f7)"
```

Expected Response:
```json
{
  "success": true,
  "message": "Profile retrieved successfully",
  "data": {
    "username": "johndoe",
    "favoriteTechnologies": [],
    "favoriteVideoGames": [],
    "favoriteBooks": []
  }
}
```

### 4. Update Profile (Authenticated)

```bash
curl -X PUT http://localhost:8080/api/profile \
  -H "Content-Type: application/json" \
  -b cookies.txt \
  -H "X-XSRF-TOKEN: $(grep XSRF-TOKEN cookies.txt | cut -f7)" \
  -d '{
    "favoriteTechnologies": ["Java", "Spring Boot", "PostgreSQL", "Redis"],
    "favoriteVideoGames": ["The Witcher 3", "Elden Ring", "Baldurs Gate 3"],
    "favoriteBooks": ["Clean Code", "Design Patterns", "Refactoring"]
  }'
```

Expected Response:
```json
{
  "success": true,
  "message": "Profile updated successfully",
  "data": {
    "username": "johndoe",
    "favoriteTechnologies": ["Java", "Spring Boot", "PostgreSQL", "Redis"],
    "favoriteVideoGames": ["The Witcher 3", "Elden Ring", "Baldurs Gate 3"],
    "favoriteBooks": ["Clean Code", "Design Patterns", "Refactoring"]
  }
}
```

### 5. Get Updated Profile

```bash
curl -X GET http://localhost:8080/api/profile \
  -b cookies.txt \
  -H "X-XSRF-TOKEN: $(grep XSRF-TOKEN cookies.txt | cut -f7)"
```

### 6. Logout (Authenticated)

```bash
curl -X POST http://localhost:8080/api/auth/logout \
  -b cookies.txt \
  -H "X-XSRF-TOKEN: $(grep XSRF-TOKEN cookies.txt | cut -f7)"
```

Expected Response:
```json
{
  "success": true,
  "message": "Logout successful",
  "data": null
}
```

## Using Postman

If you prefer using Postman:

1. **Enable Cookie Handling**: Postman automatically handles cookies between requests in the same collection.

2. **CSRF Token**: After login, copy the `XSRF-TOKEN` value from the cookies and add it as a header:
   - Header Name: `X-XSRF-TOKEN`
   - Header Value: `<token-value>`

3. **Request Sequence**:
   - POST `/api/auth/signup` - Create account
   - POST `/api/auth/login` - Login (cookie and CSRF token will be set)
   - GET `/api/profile` - View profile (include CSRF token header)
   - PUT `/api/profile` - Update profile (include CSRF token header)
   - POST `/api/auth/logout` - Logout (include CSRF token header)

## Error Scenarios

### Username Already Exists (409 Conflict)
```bash
# Try to signup with an existing username
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "password": "password456"
  }'
```

### Invalid Credentials (401 Unauthorized)
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "password": "wrongpassword"
  }'
```

### Accessing Protected Endpoint Without Authentication (401 Unauthorized)
```bash
curl -X GET http://localhost:8080/api/profile
```

## Notes

- All requests to state-changing endpoints (POST, PUT, DELETE) require the CSRF token in the `X-XSRF-TOKEN` header
- The CSRF token is automatically set in the `XSRF-TOKEN` cookie after login
- Session cookies expire after 30 minutes of inactivity (configurable in `application.yml`)
- Session data is stored in Redis, so sessions persist across application restarts
