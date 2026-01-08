# Booking Service - Technical Implementation Guide

**Service Name:** Booking Service  
**Application Name:** booking-service  
**Port:** 8083  
**Database:** booking_db  
**Spring Boot Version:** 3.5.9  

---

## Overview

The Booking Service manages ride bookings, reservations, and booking lifecycle in the ride-sharing platform. It coordinates with the User Service and Cab Service to create and manage bookings.

---

## Architecture & Components

### Application Structure

```
booking-service/
├── src/main/java/com/example/demo/
│   ├── BookingServiceApplication.java  # Main Spring Boot application
│   ├── controller/
│   │   ├── BookingRestController.java  # REST API controller
│   │   └── BookingController.java      # Traditional MVC controller
│   ├── service/
│   │   └── BookingService.java         # Business logic
│   ├── entity/
│   │   └── Booking.java                # JPA Entity
│   └── repo/
│       └── BookingRepository.java      # Spring Data JPA Repository
├── src/main/resources/
│   ├── application.properties          # Configuration
│   └── static/                         # Static files
├── src/test/java/
│   └── com/example/demo/               # Unit tests
├── pom.xml
└── target/                             # Build output
```

---

## Entity Model

### Booking Entity

**File:** `src/main/java/com/example/demo/entity/Booking.java`

```java
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;       // Reference to User
    private Long cabId;        // Reference to Cab
    private double totalFare;  // Total fare amount
    private String status;     // BOOKED, CANCELLED, COMPLETED

    // Getters and Setters
    // ...
}
```

### Database Schema

```sql
CREATE TABLE booking (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    cab_id BIGINT,
    total_fare DOUBLE,
    status VARCHAR(50)
);
```

**Column Descriptions:**

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key, auto-increment |
| user_id | BIGINT | Foreign key reference to User |
| cab_id | BIGINT | Foreign key reference to Cab |
| total_fare | DOUBLE | Total booking fare |
| status | VARCHAR(50) | BOOKED, CANCELLED, COMPLETED |

---

## Configuration

### Application Properties

**File:** `src/main/resources/application.properties`

```properties
# Spring Application Name
spring.application.name=booking-service

# Server Port
server.port=8083

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/booking_db
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
http://localhost:8080/api/bookings
```

### List All Bookings

**Request:**
```http
GET /api/bookings/
```

**Response:**
```json
[
    {
        "id": 1,
        "userId": 1,
        "cabId": 1,
        "totalFare": 450.50,
        "status": "BOOKED"
    },
    {
        "id": 2,
        "userId": 2,
        "cabId": 2,
        "totalFare": 600.00,
        "status": "COMPLETED"
    }
]
```

**HTTP Status:** 200 OK

---

### Create New Booking

**Request:**
```http
POST /api/bookings/
Content-Type: application/json

{
    "userId": 1,
    "cabId": 1,
    "totalFare": 450.50,
    "status": "BOOKED"
}
```

**Response:**
```json
{
    "id": 3,
    "userId": 1,
    "cabId": 1,
    "totalFare": 450.50,
    "status": "BOOKED"
}
```

**HTTP Status:** 201 Created

---

### Get Booking by ID

**Request:**
```http
GET /api/bookings/{id}
```

**Example:**
```http
GET /api/bookings/1
```

**Response:**
```json
{
    "id": 1,
    "userId": 1,
    "cabId": 1,
    "totalFare": 450.50,
    "status": "BOOKED"
}
```

**HTTP Status:** 200 OK | 404 Not Found

---

### Get Bookings by User

**Request:**
```http
GET /api/bookings/user/{userId}
```

**Response:**
```json
[
    {
        "id": 1,
        "userId": 1,
        "cabId": 1,
        "totalFare": 450.50,
        "status": "BOOKED"
    },
    {
        "id": 3,
        "userId": 1,
        "cabId": 2,
        "totalFare": 320.00,
        "status": "COMPLETED"
    }
]
```

**HTTP Status:** 200 OK

---

### Update Booking Status

**Request:**
```http
PUT /api/bookings/{id}
Content-Type: application/json

{
    "userId": 1,
    "cabId": 1,
    "totalFare": 450.50,
    "status": "CANCELLED"
}
```

**Response:**
```json
{
    "id": 1,
    "userId": 1,
    "cabId": 1,
    "totalFare": 450.50,
    "status": "CANCELLED"
}
```

**HTTP Status:** 200 OK | 404 Not Found

---

### Cancel Booking

**Request:**
```http
PUT /api/bookings/{id}/cancel
```

**Response:**
```json
{
    "id": 1,
    "userId": 1,
    "cabId": 1,
    "totalFare": 450.50,
    "status": "CANCELLED",
    "cancelledAt": "2024-01-08T10:35:00Z"
}
```

**HTTP Status:** 200 OK | 404 Not Found

---

### Delete Booking

**Request:**
```http
DELETE /api/bookings/{id}
```

**Response:**
```json
{
    "message": "Booking deleted successfully"
}
```

**HTTP Status:** 200 OK | 404 Not Found

---

## Service Layer

### BookingService

**Key Methods:**

```java
public List<Booking> getAllBookings()                    // Get all bookings
public Booking getBookingById(Long id)                  // Get booking by ID
public Booking createBooking(Booking booking)           // Create new booking
public Booking updateBooking(Long id, Booking booking)  // Update booking
public Booking cancelBooking(Long id)                   // Cancel booking
public void deleteBooking(Long id)                      // Delete booking
public List<Booking> getBookingsByUserId(Long userId)   // Get user's bookings
public List<Booking> getBookingsByStatus(String status) // Get by status
public double calculateFare(Long cabId, int distance)   // Calculate fare
```

### Repository Layer

```java
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByStatus(String status);
    List<Booking> findByCabId(Long cabId);
    List<Booking> findByUserIdAndStatus(Long userId, String status);
}
```

---

## Booking Workflow

### Create Booking Flow

```
1. Client submits booking request
   ├─ Validate user exists (call User Service)
   ├─ Validate cab exists (call Cab Service)
   ├─ Calculate total fare
   ├─ Create booking record
   ├─ Update cab status
   ├─ Notify user via email (optional)
   └─ Return booking confirmation
```

### Booking Status Lifecycle

```
BOOKED → COMPLETED
  ↓
CANCELLED
```

---

## Service-to-Service Communication

### Calling User Service

```java
@Service
public class BookingService {
    
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;
    
    public User validateUserExists(Long userId) {
        String url = "http://USER-SERVICE/api/users/{userId}";
        try {
            User user = restTemplate.getForObject(url, User.class, userId);
            return user;
        } catch (Exception e) {
            throw new UserNotFoundException("User not found: " + userId);
        }
    }
}
```

### Calling Cab Service

```java
public Cab validateCabExists(Long cabId) {
    String url = "http://CAB-SERVICE/api/cab/{cabId}";
    try {
        Cab cab = restTemplate.getForObject(url, Cab.class, cabId);
        return cab;
    } catch (Exception e) {
        throw new CabNotFoundException("Cab not found: " + cabId);
    }
}

public double calculateFare(Cab cab, int distanceKms) {
    return cab.getFarePerKm() * distanceKms;
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
cd booking-service

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
java -jar target/booking-service-0.0.1-SNAPSHOT.jar
```

### Verify Service is Running

```bash
# Check if port 8083 is listening
netstat -ano | findstr :8083      # Windows
lsof -i :8083                      # macOS/Linux

# Test API endpoint
curl http://localhost:8083/api/bookings/

# Check Eureka registration
curl http://localhost:8761/eureka/apps/BOOKING-SERVICE
```

---

## Testing

### Sample Unit Tests

```java
@SpringBootTest
public class BookingServiceTest {
    
    @Autowired
    private BookingService bookingService;
    
    @MockBean
    private RestTemplate restTemplate;
    
    @Test
    public void testCreateBooking() {
        Booking booking = new Booking();
        booking.setUserId(1L);
        booking.setCabId(1L);
        booking.setTotalFare(450.50);
        booking.setStatus("BOOKED");
        
        Booking created = bookingService.createBooking(booking);
        assertNotNull(created.getId());
        assertEquals("BOOKED", created.getStatus());
    }
    
    @Test
    public void testCancelBooking() {
        Booking cancelled = bookingService.cancelBooking(1L);
        assertEquals("CANCELLED", cancelled.getStatus());
    }
    
    @Test
    public void testGetBookingsByUser() {
        List<Booking> bookings = bookingService.getBookingsByUserId(1L);
        assertNotNull(bookings);
        assertTrue(bookings.size() > 0);
    }
}
```

---

## Error Handling

### Common Exceptions

| Exception | HTTP Status | Description |
|-----------|------------|-------------|
| BookingNotFoundException | 404 | Booking not found |
| UserNotFoundException | 404 | Referenced user not found |
| CabNotFoundException | 404 | Referenced cab not found |
| InvalidBookingStatusException | 400 | Invalid status transition |
| DataIntegrityViolationException | 400 | Database constraint violation |

### Example Error Response

```json
{
    "error": "User Not Found",
    "message": "User with ID 999 not found",
    "status": 404,
    "timestamp": "2024-01-08T10:30:45.123Z"
}
```

---

## Database Operations

### Create Test Data

```sql
INSERT INTO booking (user_id, cab_id, total_fare, status) VALUES
(1, 1, 450.50, 'BOOKED'),
(2, 2, 600.00, 'COMPLETED'),
(1, 3, 320.00, 'CANCELLED');
```

### Common Queries

```sql
-- Get all bookings for a user
SELECT * FROM booking WHERE user_id = 1;

-- Get completed bookings
SELECT * FROM booking WHERE status = 'COMPLETED';

-- Get total revenue
SELECT SUM(total_fare) as total_revenue FROM booking WHERE status = 'COMPLETED';

-- Get bookings by date range
SELECT * FROM booking WHERE created_at BETWEEN '2024-01-01' AND '2024-01-31';

-- Get average fare per booking
SELECT AVG(total_fare) as avg_fare FROM booking;
```

---

## Logging & Monitoring

### Log Configuration

```properties
logging.level.com.example.demo=DEBUG
logging.level.org.springframework.cloud=DEBUG
logging.file.name=logs/booking-service.log
logging.file.max-size=10MB
logging.file.max-history=10
```

### Health Check

```bash
curl http://localhost:8083/actuator/health
```

---

## Performance Optimization

### Database Indexing

```sql
CREATE INDEX idx_user_id ON booking(user_id);
CREATE INDEX idx_cab_id ON booking(cab_id);
CREATE INDEX idx_status ON booking(status);
CREATE INDEX idx_user_status ON booking(user_id, status);
```

### Query Optimization

```java
@Query("SELECT b FROM Booking b WHERE b.userId = ?1 ORDER BY b.id DESC")
List<Booking> findRecentBookingsByUser(Long userId);

@Query("SELECT b.status, COUNT(b) FROM Booking b GROUP BY b.status")
List<Object[]> getBookingsByStatusCount();
```

---

## Deployment Checklist

- [ ] Database (booking_db) created
- [ ] MySQL connection verified
- [ ] application.properties configured
- [ ] User Service dependency verified
- [ ] Cab Service dependency verified
- [ ] All unit tests passing
- [ ] Service registers with Eureka
- [ ] API endpoints tested
- [ ] Logging enabled
- [ ] Error handling configured

---

## References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [RestTemplate Documentation](https://spring.io/guides/gs/consuming-rest/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Microservices Communication Patterns](https://microservices.io/patterns/communication-style/rpc.html)

