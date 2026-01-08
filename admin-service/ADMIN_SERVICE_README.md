# Admin Service - Technical Implementation Guide

**Service Name:** Admin Service  
**Application Name:** admin-service  
**Port:** 8084  
**Database:** admin_db  
**Spring Boot Version:** 3.5.9  

---

## Overview

The Admin Service provides administrative operations and system management capabilities for the ride-sharing platform. It offers dashboards, reports, and management endpoints for system administrators.

---

## Architecture & Components

### Application Structure

```
admin-service/
├── src/main/java/com/example/demo/
│   ├── AdminServiceApplication.java      # Main Spring Boot application
│   ├── controller/
│   │   ├── AdminController.java          # Admin REST endpoints
│   │   ├── AdminRestController.java      # Additional REST endpoints
│   │   ├── AdminPageController.java      # Web page controller
│   │   └── UserPageController.java       # User management page
│   ├── service/
│   │   └── AdminService.java             # Business logic
│   ├── entity/
│   │   ├── Admin.java                    # Admin entity
│   │   ├── UserDTO.java                  # User data transfer object
│   │   ├── CabDTO.java                   # Cab data transfer object
│   │   └── BookingDTO.java               # Booking data transfer object
│   └── repo/
│       └── AdminRepository.java          # Spring Data JPA Repository
├── src/main/resources/
│   ├── application.properties            # Configuration
│   ├── static/                           # Static files
│   └── templates/                        # HTML templates
├── src/test/java/
│   └── com/example/demo/                 # Unit tests
├── pom.xml
└── target/                               # Build output
```

---

## Entity Model

### Admin Entity

**File:** `src/main/java/com/example/demo/entity/Admin.java`

```java
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;      // Admin username
    private String role;          // Admin role

    // Getters and Setters
    // ...
}
```

### Data Transfer Objects (DTOs)

#### UserDTO

```java
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    
    // Getters and Setters
}
```

#### CabDTO

```java
public class CabDTO {
    private Long id;
    private String source;
    private String destination;
    private String type;
    private int kms;
    private double farePerKm;
    private String status;
    
    // Getters and Setters
}
```

#### BookingDTO

```java
public class BookingDTO {
    private Long id;
    private Long userId;
    private Long cabId;
    private double totalFare;
    private String status;
    private LocalDateTime bookingTime;
    
    // Getters and Setters
}
```

### Database Schema

```sql
CREATE TABLE admin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(50)
);
```

---

## Configuration

### Application Properties

**File:** `src/main/resources/application.properties`

```properties
# Spring Application Name
spring.application.name=admin-service

# Server Port
server.port=8084

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/admin_db
spring.datasource.username=root
spring.datasource.password=root

# JPA & Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Eureka Service Discovery
eureka.client.service-url.defaultZone=http://localhost:8761/eureka

# Swagger/OpenAPI Documentation
springdoc.swagger-ui.path=/swagger-ui.html

# JSP View Configuration
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp
```

---

## API Endpoints

### Base URL
```
http://localhost:8080/admin  (through gateway)
http://localhost:8084/admin  (direct access)
```

---

## Dashboard & Overview

### Get Admin Dashboard

**Request:**
```http
GET /admin/dashboard
```

**Response:**
```json
{
    "totalUsers": 150,
    "totalCabs": 45,
    "totalBookings": 1250,
    "totalRevenue": 45300.50,
    "activeBookings": 28,
    "cancelledBookings": 120,
    "completedBookings": 1102,
    "averageFarePerBooking": 36.24,
    "systemStatus": "HEALTHY",
    "lastUpdated": "2024-01-08T10:35:00Z"
}
```

**HTTP Status:** 200 OK

---

## User Management Endpoints

### List All Users

**Request:**
```http
GET /admin/users
```

**Response:**
```json
{
    "totalCount": 150,
    "users": [
        {
            "id": 1,
            "username": "john_doe",
            "email": "john@example.com",
            "role": "USER",
            "createdAt": "2024-01-01T10:00:00Z"
        },
        {
            "id": 2,
            "username": "jane_smith",
            "email": "jane@example.com",
            "role": "USER",
            "createdAt": "2024-01-02T10:00:00Z"
        }
    ]
}
```

**HTTP Status:** 200 OK

---

### Get User Details

**Request:**
```http
GET /admin/users/{id}
```

**Response:**
```json
{
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "role": "USER",
    "createdAt": "2024-01-01T10:00:00Z",
    "lastLogin": "2024-01-08T09:30:00Z",
    "totalBookings": 12,
    "totalSpent": 450.50
}
```

**HTTP Status:** 200 OK | 404 Not Found

---

### Delete User

**Request:**
```http
DELETE /admin/users/{id}
```

**Response:**
```json
{
    "message": "User deleted successfully",
    "deletedUserId": 999
}
```

**HTTP Status:** 200 OK | 404 Not Found

---

## Cab Management Endpoints

### List All Cabs

**Request:**
```http
GET /admin/cabs
```

**Response:**
```json
{
    "totalCount": 45,
    "cabs": [
        {
            "id": 1,
            "source": "Downtown",
            "destination": "Airport",
            "type": "sedan",
            "kms": 25,
            "farePerKm": 15.50,
            "status": "ACTIVE"
        }
    ]
}
```

**HTTP Status:** 200 OK

---

### Get Cab Details

**Request:**
```http
GET /admin/cabs/{id}
```

**Response:**
```json
{
    "id": 1,
    "source": "Downtown",
    "destination": "Airport",
    "type": "sedan",
    "kms": 25,
    "farePerKm": 15.50,
    "status": "ACTIVE",
    "totalTrips": 250,
    "totalRevenue": 9750.00,
    "lastTrip": "2024-01-08T09:15:00Z"
}
```

**HTTP Status:** 200 OK | 404 Not Found

---

### Toggle Cab Status

**Request:**
```http
PUT /admin/cabs/{id}/status
Content-Type: application/json

{
    "status": "INACTIVE"
}
```

**Response:**
```json
{
    "id": 1,
    "status": "INACTIVE",
    "message": "Cab status updated"
}
```

**HTTP Status:** 200 OK

---

## Booking Management Endpoints

### List All Bookings

**Request:**
```http
GET /admin/bookings
```

**Response:**
```json
{
    "totalCount": 1250,
    "bookings": [
        {
            "id": 1,
            "userId": 1,
            "cabId": 1,
            "totalFare": 450.50,
            "status": "COMPLETED",
            "bookingTime": "2024-01-08T10:00:00Z"
        }
    ]
}
```

**HTTP Status:** 200 OK

---

### Get Booking Details

**Request:**
```http
GET /admin/bookings/{id}
```

**Response:**
```json
{
    "id": 1,
    "userId": 1,
    "cabId": 1,
    "userDetails": {
        "username": "john_doe",
        "email": "john@example.com"
    },
    "cabDetails": {
        "type": "sedan",
        "source": "Downtown",
        "destination": "Airport"
    },
    "totalFare": 450.50,
    "status": "COMPLETED",
    "bookingTime": "2024-01-08T10:00:00Z",
    "completedTime": "2024-01-08T10:45:00Z"
}
```

**HTTP Status:** 200 OK

---

### Filter Bookings

**Request:**
```http
GET /admin/bookings?status=COMPLETED&startDate=2024-01-01&endDate=2024-01-31
```

**Query Parameters:**
- `status` - BOOKED, COMPLETED, CANCELLED
- `startDate` - Start date (ISO format)
- `endDate` - End date (ISO format)
- `userId` - Filter by user
- `cabId` - Filter by cab

**Response:** List of filtered bookings

**HTTP Status:** 200 OK

---

## Reports & Analytics

### Revenue Report

**Request:**
```http
GET /admin/reports/revenue?period=MONTHLY&year=2024&month=1
```

**Response:**
```json
{
    "period": "MONTHLY",
    "year": 2024,
    "month": 1,
    "totalRevenue": 45300.50,
    "completedBookings": 1102,
    "averageFarePerBooking": 36.24,
    "revenueByType": {
        "sedan": 28000.00,
        "suv": 12300.50,
        "hatchback": 5000.00
    }
}
```

**HTTP Status:** 200 OK

---

### User Activity Report

**Request:**
```http
GET /admin/reports/user-activity
```

**Response:**
```json
{
    "totalUsers": 150,
    "activeUsers": 125,
    "newUserToday": 5,
    "newUserThisWeek": 28,
    "newUserThisMonth": 120,
    "usersByRole": {
        "ADMIN": 5,
        "USER": 145
    }
}
```

**HTTP Status:** 200 OK

---

## Service Layer

### AdminService

**Key Methods:**

```java
public AdminDashboard getDashboard()                        // Get dashboard data
public List<UserDTO> getAllUsers()                         // Get all users
public UserDTO getUserById(Long userId)                    // Get user details
public void deleteUser(Long userId)                        // Delete user
public List<CabDTO> getAllCabs()                          // Get all cabs
public CabDTO getCabById(Long cabId)                      // Get cab details
public void toggleCabStatus(Long cabId, String status)    // Toggle cab status
public List<BookingDTO> getAllBookings()                  // Get all bookings
public BookingDTO getBookingById(Long bookingId)          // Get booking details
public RevenueReport getRevenueReport(String period)      // Get revenue report
public List<BookingDTO> filterBookings(BookingFilter filter)  // Filter bookings
```

---

## Integration with Other Services

### Calling User Service

```java
@Service
public class AdminService {
    
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;
    
    public List<UserDTO> getAllUsers() {
        String url = "http://USER-SERVICE/api/users/";
        List<UserDTO> users = restTemplate.getForObject(url, List.class);
        return users;
    }
    
    public void deleteUser(Long userId) {
        String url = "http://USER-SERVICE/api/users/{userId}";
        restTemplate.delete(url, userId);
    }
}
```

### Calling Cab Service

```java
public List<CabDTO> getAllCabs() {
    String url = "http://CAB-SERVICE/api/cab/";
    List<CabDTO> cabs = restTemplate.getForObject(url, List.class);
    return cabs;
}
```

### Calling Booking Service

```java
public List<BookingDTO> getAllBookings() {
    String url = "http://BOOKING-SERVICE/api/bookings/";
    List<BookingDTO> bookings = restTemplate.getForObject(url, List.class);
    return bookings;
}
```

---

## Build & Run

### Prerequisites

- Java 21 JDK
- Maven 3.8+
- MySQL 8.0+
- Discovery Service running on port 8761

### Building

```bash
cd admin-service

# Clean build
mvn clean install

# Skip tests
mvn clean install -DskipTests
```

### Running

**Option 1: Maven Spring Boot Plugin**
```bash
mvn spring-boot:run
```

**Option 2: Run JAR file**
```bash
mvn clean package
java -jar target/admin-service-0.0.1-SNAPSHOT.jar
```

### Verify Service is Running

```bash
# Check if port 8084 is listening
netstat -ano | findstr :8084      # Windows
lsof -i :8084                      # macOS/Linux

# Test API endpoint
curl http://localhost:8084/admin/dashboard

# Check Eureka registration
curl http://localhost:8761/eureka/apps/ADMIN-SERVICE
```

---

## Testing

### Sample Unit Tests

```java
@SpringBootTest
public class AdminServiceTest {
    
    @Autowired
    private AdminService adminService;
    
    @MockBean
    private RestTemplate restTemplate;
    
    @Test
    public void testGetDashboard() {
        AdminDashboard dashboard = adminService.getDashboard();
        assertNotNull(dashboard);
        assertTrue(dashboard.getTotalUsers() >= 0);
    }
    
    @Test
    public void testGetAllUsers() {
        List<UserDTO> users = adminService.getAllUsers();
        assertNotNull(users);
    }
    
    @Test
    public void testGetAllBookings() {
        List<BookingDTO> bookings = adminService.getAllBookings();
        assertNotNull(bookings);
    }
}
```

---

## Error Handling

### Common Exceptions

| Exception | HTTP Status | Description |
|-----------|------------|-------------|
| UnauthorizedException | 403 | Not authorized for admin operations |
| ResourceNotFoundException | 404 | Resource not found |
| ServiceUnavailableException | 503 | Dependent service unavailable |
| DataIntegrityViolationException | 400 | Database constraint violation |

---

## Logging & Monitoring

### Log Configuration

```properties
logging.level.com.example.demo=DEBUG
logging.level.org.springframework.web=DEBUG
logging.file.name=logs/admin-service.log
logging.file.max-size=10MB
```

### Health Check

```bash
curl http://localhost:8084/actuator/health
```

---

## Security Considerations

### Admin Authentication

```java
@Configuration
@EnableWebSecurity
public class AdminSecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
            .formLogin();
        return http.build();
    }
}
```

### Access Control

- Only users with ADMIN role can access admin endpoints
- All admin actions should be logged
- Implement rate limiting for sensitive operations

---

## Deployment Checklist

- [ ] Database (admin_db) created
- [ ] MySQL connection verified
- [ ] application.properties configured
- [ ] All dependent services reachable
- [ ] All unit tests passing
- [ ] Service registers with Eureka
- [ ] Dashboard endpoints tested
- [ ] Reporting endpoints tested
- [ ] Security configured
- [ ] Logging enabled

---

## References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Actuator Documentation](https://spring.io/guides/gs/actuator-service/)
- [RESTful Web Services](https://spring.io/guides/gs/rest-service/)

