# Cab Service - Technical Implementation Guide

**Service Name:** Cab Service  
**Application Name:** cab-service  
**Port:** 8081  
**Database:** cab_db  
**Spring Boot Version:** 3.5.9  

---

## Overview

The Cab Service is responsible for managing the cab inventory, availability, and pricing information in the ride-sharing platform. It provides APIs to create, read, update, and delete cab records.

---

## Architecture & Components

### Application Structure

```
cab-service/
├── src/main/java/com/example/demo/
│   ├── CabServiceApplication.java      # Main Spring Boot application
│   ├── controller/
│   │   ├── CabController.java          # Traditional MVC controller
│   │   └── CabPageController.java      # Page-based controller for web UI
│   ├── service/
│   │   ├── CabService.java             # Business logic
│   │   └── package-info.java
│   ├── entity/
│   │   ├── Cab.java                    # JPA Entity
│   │   └── package-info.java
│   └── repo/
│       ├── CabRepository.java          # Spring Data JPA Repository
│       └── package-info.java
├── src/main/resources/
│   ├── application.properties          # Configuration
│   └── static/                         # Static files
├── src/test/java/
│   └── com/example/demo/               # Unit tests
├── pom.xml
└── target/                             # Build output
```

---

## Dependencies

### Core Spring Dependencies

| Dependency | Version | Purpose |
|------------|---------|---------|
| spring-boot-starter-web | 3.5.9 | REST API & Web support |
| spring-boot-starter-data-jpa | 3.5.9 | ORM & Database access |
| spring-boot-devtools | runtime | Hot reload during development |

### Database & ORM

| Dependency | Version | Purpose |
|------------|---------|---------|
| mysql-connector-j | Latest | MySQL JDBC Driver |

### Spring Cloud

| Dependency | Version | Purpose |
|------------|---------|---------|
| spring-cloud-starter-netflix-eureka-client | 4.1.2 | Service discovery |

### Testing

| Dependency | Version | Purpose |
|------------|---------|---------|
| spring-boot-starter-test | 3.5.9 | JUnit, Mockito, etc. |
| io.projectreactor:reactor-test | Latest | Reactive testing |
| mockito-inline | 4.11.0 | Advanced mocking |
| junit-jupiter | 5.10.0 | JUnit 5 API |
| junit-platform-suite | 1.10.0 | JUnit test suite |

### Additional Libraries

| Dependency | Version | Purpose |
|------------|---------|---------|
| org.json | 20240303 | JSON processing |
| tomcat-embed-jasper | Latest | JSP compilation |
| jakarta.servlet.jsp.jstl | 3.0.1+ | JSTL support |
| jakarta.el | 4.0.2 | Expression Language |

---

## Configuration

### Application Properties

**File:** `src/main/resources/application.properties`

```properties
# Spring Application Name
spring.application.name=cab-service

# Server Port
server.port=8081

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/cab_db
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

### Environment Variables (Optional)

```bash
# Set MySQL Host
export DB_HOST=localhost

# Set MySQL Port
export DB_PORT=3306

# Set MySQL Username
export DB_USER=root

# Set MySQL Password
export DB_PASS=root
```

---

## Entity Model

### Cab Entity

**File:** `src/main/java/com/example/demo/entity/Cab.java`

```java
@Entity
@Table(name = "cab")
public class Cab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String source;          // Pickup location
    private String destination;     // Dropoff location
    private String type;            // Vehicle type: sedan, suv, hatchback
    private int kms;                // Distance in kilometers
    private double farePerKm;       // Fare rate per km

    // Getters and Setters
    // ...
}
```

### Database Schema

```sql
CREATE TABLE cab (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    source VARCHAR(255),
    destination VARCHAR(255),
    type VARCHAR(50),
    kms INT,
    fare_per_km DOUBLE
);
```

**Column Descriptions:**

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key, auto-increment |
| source | VARCHAR(255) | Pickup location |
| destination | VARCHAR(255) | Dropoff location |
| type | VARCHAR(50) | Cab type (sedan, suv, hatchback) |
| kms | INT | Distance in kilometers |
| fare_per_km | DOUBLE | Rate per kilometer |

---

## API Endpoints

### Base URL
```
http://localhost:8080/api/cab
```

### List All Cabs

**Request:**
```http
GET /api/cab/
```

**Response:**
```json
[
    {
        "id": 1,
        "source": "Downtown",
        "destination": "Airport",
        "type": "sedan",
        "kms": 25,
        "farePerKm": 15.50
    },
    {
        "id": 2,
        "source": "Station",
        "destination": "Airport",
        "type": "suv",
        "kms": 30,
        "farePerKm": 20.00
    }
]
```

**HTTP Status:** 200 OK

---

### Create a New Cab

**Request:**
```http
POST /api/cab/
Content-Type: application/json

{
    "source": "Downtown",
    "destination": "Airport",
    "type": "sedan",
    "kms": 25,
    "farePerKm": 15.50
}
```

**Response:**
```json
{
    "id": 3,
    "source": "Downtown",
    "destination": "Airport",
    "type": "sedan",
    "kms": 25,
    "farePerKm": 15.50
}
```

**HTTP Status:** 201 Created

---

### Get Cab by ID

**Request:**
```http
GET /api/cab/{id}
```

**Example:**
```http
GET /api/cab/1
```

**Response:**
```json
{
    "id": 1,
    "source": "Downtown",
    "destination": "Airport",
    "type": "sedan",
    "kms": 25,
    "farePerKm": 15.50
}
```

**HTTP Status:** 200 OK | 404 Not Found

---

### Update Cab

**Request:**
```http
PUT /api/cab/{id}
Content-Type: application/json

{
    "source": "Downtown",
    "destination": "Airport",
    "type": "premium-sedan",
    "kms": 25,
    "farePerKm": 18.00
}
```

**Response:**
```json
{
    "id": 1,
    "source": "Downtown",
    "destination": "Airport",
    "type": "premium-sedan",
    "kms": 25,
    "farePerKm": 18.00
}
```

**HTTP Status:** 200 OK | 404 Not Found

---

### Delete Cab

**Request:**
```http
DELETE /api/cab/{id}
```

**Response:**
```json
{
    "message": "Cab deleted successfully"
}
```

**HTTP Status:** 200 OK | 404 Not Found

---

## Service Layer

### CabService

**File:** `src/main/java/com/example/demo/service/CabService.java`

Provides business logic for cab operations:

**Key Methods:**

```java
public List<Cab> getAllCabs()                // Get all cabs
public Cab getCabById(Long id)              // Get cab by ID
public Cab createCab(Cab cab)               // Create new cab
public Cab updateCab(Long id, Cab cab)      // Update existing cab
public void deleteCab(Long id)              // Delete cab
public List<Cab> searchCabs(String source)  // Search by source location
```

### Repository Layer

**File:** `src/main/java/com/example/demo/repo/CabRepository.java`

Spring Data JPA interface for database operations:

```java
public interface CabRepository extends JpaRepository<Cab, Long> {
    List<Cab> findBySource(String source);
    List<Cab> findByType(String type);
    List<Cab> findBySourceAndDestination(String source, String destination);
}
```

---

## Controller Layer

### CabController (REST API)

**File:** `src/main/java/com/example/demo/controller/CabController.java`

Handles REST API requests for cab operations.

### CabPageController (Web UI)

**File:** `src/main/java/com/example/demo/controller/CabPageController.java`

Handles traditional web page requests for server-side rendering.

---

## Build & Run

### Prerequisites

- Java 21 JDK
- Maven 3.8+
- MySQL 8.0+
- Discovery Service running on port 8761

### Building

```bash
cd cab-service

# Clean build
mvn clean install

# Skip tests
mvn clean install -DskipTests

# Build with specific profile (if configured)
mvn clean install -P production
```

### Running

**Option 1: Maven Spring Boot Plugin**
```bash
mvn spring-boot:run
```

**Option 2: Run JAR file**
```bash
# Build JAR first
mvn clean package

# Run the JAR
java -jar target/cab-service-0.0.1-SNAPSHOT.jar
```

**Option 3: Debug Mode**
```bash
mvn spring-boot:run -X
```

### Verify Service is Running

```bash
# Check if port 8081 is listening
netstat -ano | findstr :8081      # Windows
lsof -i :8081                      # macOS/Linux

# Test API endpoint
curl http://localhost:8081/api/cab/

# Check Eureka registration
curl http://localhost:8761/eureka/apps/CAB-SERVICE
```

---

## Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=CabServiceTest

# Run with coverage
mvn clean test jacoco:report
```

### Test Structure

```
src/test/java/com/example/demo/
├── CabServiceApplicationTests.java
├── controller/
│   └── CabControllerTest.java
├── service/
│   └── CabServiceTest.java
└── repo/
    └── CabRepositoryTest.java
```

### Example Test

```java
@SpringBootTest
public class CabServiceTest {
    
    @Autowired
    private CabService cabService;
    
    @Test
    public void testGetAllCabs() {
        List<Cab> cabs = cabService.getAllCabs();
        assertNotNull(cabs);
    }
    
    @Test
    public void testCreateCab() {
        Cab cab = new Cab();
        cab.setSource("Downtown");
        cab.setDestination("Airport");
        
        Cab created = cabService.createCab(cab);
        assertNotNull(created.getId());
    }
}
```

---

## Logging

### Log Configuration

**File:** `application.properties`

```properties
# Root logging level
logging.level.root=INFO

# Application logging
logging.level.com.example.demo=DEBUG

# Spring Data JPA logging
logging.level.org.springframework.data=DEBUG
logging.level.org.hibernate=DEBUG

# Log file
logging.file.name=logs/cab-service.log
logging.file.max-size=10MB
logging.file.max-history=10
```

### Example Logs

```
2024-01-08 10:30:45.123 INFO  [main] DiscoveryClient - Getting all instance registrations
2024-01-08 10:30:45.456 DEBUG [main] CabService - Creating new cab: Downtown -> Airport
2024-01-08 10:30:45.789 INFO  [main] CabServiceApplication - Started CabServiceApplication in 5.234 seconds
```

---

## Error Handling

### Exception Types

| Exception | HTTP Status | Meaning |
|-----------|------------|---------|
| EntityNotFoundException | 404 | Cab not found |
| DataIntegrityViolationException | 400 | Invalid data |
| DatabaseException | 500 | Database error |

### Example Error Response

```json
{
    "error": "Not Found",
    "message": "Cab with ID 999 not found",
    "status": 404,
    "timestamp": "2024-01-08T10:30:45.123Z"
}
```

---

## Performance Optimization

### Database Indexing

```sql
-- Create indexes for common queries
CREATE INDEX idx_source ON cab(source);
CREATE INDEX idx_destination ON cab(destination);
CREATE INDEX idx_type ON cab(type);
CREATE INDEX idx_source_dest ON cab(source, destination);
```

### Query Optimization

Use Spring Data JPA query methods:

```java
// Efficient: Returns only needed data
@Query("SELECT c FROM Cab c WHERE c.source = ?1")
List<Cab> findBySourceOptimized(String source);
```

### Caching

Enable caching for frequently accessed data:

```java
@Cacheable("cabs")
public List<Cab> getAllCabs() {
    return cabRepository.findAll();
}

@CacheEvict(value = "cabs", allEntries = true)
public Cab createCab(Cab cab) {
    return cabRepository.save(cab);
}
```

---

## Service to Service Communication

### Calling Other Services

```java
@Service
public class CabService {
    
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;
    
    public void notifyBooking(Long cabId, Long bookingId) {
        String url = "http://BOOKING-SERVICE/api/bookings/confirm/{bookingId}";
        restTemplate.postForObject(url, null, String.class, bookingId);
    }
}
```

### Discovery Pattern

The service is automatically discovered by:
1. Registering with Eureka on startup
2. Periodic heartbeat to keep registration alive
3. Gateway routes requests using service name (CAB-SERVICE)
4. Load balancing across multiple instances

---

## Deployment Checklist

- [ ] Database created and configured
- [ ] application.properties updated with correct values
- [ ] Discovery Service running (port 8761)
- [ ] MySQL connection tested
- [ ] All tests passing
- [ ] Service builds successfully
- [ ] Service registers with Eureka
- [ ] API endpoints respond correctly
- [ ] No Spring startup errors

---

## References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/projects/spring-data-jpa)
- [Eureka Client Documentation](https://cloud.spring.io/spring-cloud-netflix/reference/html/)
- [REST API Best Practices](https://restfulapi.net/)

