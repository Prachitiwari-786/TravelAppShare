# Gateway Service - Technical Implementation Guide

**Service Name:** API Gateway Service  
**Application Name:** gateway-service  
**Port:** 8080  
**Type:** Spring Cloud Gateway  
**Spring Boot Version:** 3.5.9  
**Spring Cloud Version:** 2025.0.1  

---

## Overview

The Gateway Service provides a single entry point for all client requests in the ride-sharing microservices platform. It handles request routing, load balancing, and request/response filtering using Spring Cloud Gateway.

---

## Architecture & Components

### Application Structure

```
gateway-service/
├── src/main/java/com/example/demo/
│   └── GatewayServiceApplication.java      # Main Spring Cloud Gateway application
├── src/main/resources/
│   ├── application.yml                     # Configuration (YAML format)
│   └── static/                             # Static files
├── src/test/java/
│   └── com/example/demo/                   # Unit tests
├── pom.xml
└── target/                                 # Build output
```

---

## Key Dependencies

| Dependency | Version | Purpose |
|------------|---------|---------|
| spring-cloud-starter-gateway | Latest | API Gateway implementation |
| spring-cloud-starter-netflix-eureka-client | 4.1.2 | Service discovery client |
| spring-boot-starter-actuator | 3.5.9 | Monitoring and health checks |
| spring-cloud-dependencies | 2025.0.1 | Cloud dependency management |
| lombok | Latest | Reduce boilerplate code (optional) |

---

## Configuration

### Application Configuration

**File:** `src/main/resources/application.yml`

```yaml
server:
  port: 8080

spring:
  application:
    name: gateway-service
  
  cloud:
    gateway:
      routes:
        # Cab Service Route
        - id: cab-service
          uri: lb://CAB-SERVICE
          predicates:
            - Path=/api/cab/**
          filters:
            - StripPrefix=1

        # User Service Route
        - id: user-service
          uri: lb://USER-SERVICE
          predicates:
            - Path=/api/users/**
          filters:
            - StripPrefix=1

        # Booking Service Route
        - id: booking-service
          uri: lb://BOOKING-SERVICE
          predicates:
            - Path=/api/bookings/**
          filters:
            - StripPrefix=1

        # Admin Service Route (optional)
        - id: admin-service
          uri: lb://ADMIN-SERVICE
          predicates:
            - Path=/admin/**
          filters:
            - StripPrefix=0

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/

# Optional: Actuator endpoints
management:
  endpoints:
    web:
      exposure:
        include: health,routes,metrics
```

### Configuration Explanation

#### Route Definition

Each route consists of:

1. **id** - Unique identifier for the route
2. **uri** - Backend service URI
   - `lb://SERVICE-NAME` - Load balanced URI using Eureka discovery
3. **predicates** - Conditions to match requests
   - `Path=/api/cab/**` - Match request paths
4. **filters** - Request/response transformations
   - `StripPrefix=1` - Remove first path segment before forwarding

#### Filter: StripPrefix

**Before StripPrefix:**
```
Client Request: http://localhost:8080/api/cab/list
↓
Forwarded to: http://CAB-SERVICE:8081/api/cab/list
```

**After StripPrefix=1:**
```
Client Request: http://localhost:8080/api/cab/list
↓
Forwarded to: http://CAB-SERVICE:8081/cab/list
```

---

## Application Code

### GatewayServiceApplication

**File:** `src/main/java/com/example/demo/GatewayServiceApplication.java`

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
```

---

## API Gateway Routing

### URL Mapping

| Client Request | Route Predicate | Backend Service | Forwarded To |
|---|---|---|---|
| `GET http://localhost:8080/api/cab/list` | /api/cab/** | CAB-SERVICE | `http://localhost:8081/cab/list` |
| `GET http://localhost:8080/api/users/all` | /api/users/** | USER-SERVICE | `http://localhost:8082/users/all` |
| `POST http://localhost:8080/api/bookings/create` | /api/bookings/** | BOOKING-SERVICE | `http://localhost:8083/bookings/create` |

---

## Gateway Management Endpoints

### List All Routes

**Request:**
```http
GET http://localhost:8080/actuator/routes
```

**Response:**
```json
[
    {
        "predicate": "Paths: [/api/cab/**], match trailing slash: true",
        "route_id": "cab-service",
        "filters": [
            "[StripPrefix(1)]"
        ],
        "uri": "lb://CAB-SERVICE"
    },
    {
        "predicate": "Paths: [/api/users/**], match trailing slash: true",
        "route_id": "user-service",
        "filters": [
            "[StripPrefix(1)]"
        ],
        "uri": "lb://USER-SERVICE"
    },
    {
        "predicate": "Paths: [/api/bookings/**], match trailing slash: true",
        "route_id": "booking-service",
        "filters": [
            "[StripPrefix(1)]"
        ],
        "uri": "lb://BOOKING-SERVICE"
    }
]
```

---

### Health Check

**Request:**
```http
GET http://localhost:8080/actuator/health
```

**Response:**
```json
{
    "status": "UP",
    "components": {
        "discoveryComposite": {
            "status": "UP",
            "components": {
                "discoveryclient": {
                    "status": "UP",
                    "details": {
                        "services": [
                            "CAB-SERVICE",
                            "USER-SERVICE",
                            "BOOKING-SERVICE",
                            "ADMIN-SERVICE",
                            "GATEWAY-SERVICE"
                        ]
                    }
                }
            }
        }
    }
}
```

---

## Request Flow

### Complete Request Lifecycle

```
1. Client Request
   Client → http://localhost:8080/api/cab/list

2. Gateway Receives Request
   ↓
   Match Route Predicate
   ├─ Path: /api/cab/**? ✓ MATCH

3. Apply Filters
   ├─ StripPrefix(1) → /cab/list

4. Service Discovery
   ├─ Eureka discovers CAB-SERVICE instances
   ├─ Selects healthy instance (load balancing)

5. Forward Request
   Gateway → http://CAB-SERVICE:8081/cab/list

6. Service Processing
   ├─ CAB-SERVICE processes request
   └─ Returns response

7. Response to Client
   ← Gateway returns response to client
```

---

## Load Balancing

### How Load Balancing Works

The `lb://` prefix in the URI enables client-side load balancing:

```yaml
uri: lb://CAB-SERVICE  # Load balanced URI
```

**Process:**
1. Gateway queries Eureka for CAB-SERVICE instances
2. Multiple instances available? Distributes requests
3. One instance DOWN? Automatically uses healthy ones
4. Automatic failover and circuit breaking

### Load Balancing Strategies

**Default (Round Robin):**
```
Request 1 → Instance 1
Request 2 → Instance 2
Request 3 → Instance 1
...
```

---

## Advanced Gateway Configuration

### Custom Filters

Add request/response headers:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: cab-service
          uri: lb://CAB-SERVICE
          predicates:
            - Path=/api/cab/**
          filters:
            - StripPrefix=1
            - AddRequestHeader=X-Request-Id, {java.util.UUID.randomUUID()}
            - AddResponseHeader=X-Custom-Response-Header, MyValue
```

### Rate Limiting

Limit requests per user:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: cab-service
          uri: lb://CAB-SERVICE
          predicates:
            - Path=/api/cab/**
          filters:
            - StripPrefix=1
            - name: RequestRateLimiter
              args:
                redis-rate-limiter:
                  replenishRate: 10
                  burstCapacity: 20
```

### CORS Configuration

Enable cross-origin requests:

```yaml
spring:
  cloud:
    gateway:
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins: "*"
            allowedMethods: 
              - GET
              - POST
              - PUT
              - DELETE
            allowedHeaders: "*"
```

---

## Security Configuration

### Enable HTTPS

```yaml
server:
  port: 8443
  ssl:
    key-store: classpath:keystore.p12
    key-store-password: password
    key-store-type: PKCS12
```

### Authentication

Add custom gateway filter for authentication:

```java
@Configuration
public class AuthenticationGatewayFilter {
    
    @Bean
    public GlobalFilter authenticationFilter() {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (token == null || !validateToken(token)) {
                return Mono.error(new UnauthorizedException("Invalid token"));
            }
            return chain.filter(exchange);
        };
    }
}
```

---

## Build & Run

### Prerequisites

- Java 21 JDK
- Maven 3.8+
- Discovery Service running on port 8761
- Microservices running and registered

### Building

```bash
cd gateway-service

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
java -jar target/gateway-service-0.0.1-SNAPSHOT.jar
```

### Verify Gateway is Running

```bash
# Check if port 8080 is listening
netstat -ano | findstr :8080      # Windows
lsof -i :8080                      # macOS/Linux

# Test gateway endpoint
curl http://localhost:8080/actuator/health

# View registered routes
curl http://localhost:8080/actuator/routes

# Test routing to cab service
curl http://localhost:8080/api/cab/

# Check Eureka registration
curl http://localhost:8761/eureka/apps/GATEWAY-SERVICE
```

---

## Request/Response Examples

### Request Through Gateway (Cab Service)

**Request:**
```bash
curl -X GET http://localhost:8080/api/cab/
```

**What happens:**
1. Gateway matches `/api/cab/` against route predicate
2. Applies StripPrefix=1 → `/`
3. Forwards to `http://CAB-SERVICE:8081/`
4. Returns response

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
    }
]
```

---

### Request Through Gateway (User Service)

**Request:**
```bash
curl -X GET http://localhost:8080/api/users/
```

**What happens:**
1. Gateway matches `/api/users/` against route predicate
2. Applies StripPrefix=1 → `/`
3. Forwards to `http://USER-SERVICE:8082/`
4. Returns response

---

### POST Request Through Gateway

**Request:**
```bash
curl -X POST http://localhost:8080/api/bookings/ \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "cabId": 1,
    "totalFare": 450.50,
    "status": "BOOKED"
  }'
```

**What happens:**
1. Gateway matches `/api/bookings/` against route predicate
2. Applies StripPrefix=1 → `/`
3. Forwards POST request to `http://BOOKING-SERVICE:8083/`
4. Returns response

---

## Testing

### Unit Tests

```java
@SpringBootTest
public class GatewayServiceApplicationTests {
    
    @Test
    public void contextLoads() {
        // Application context should load successfully
    }
}
```

### Integration Tests

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GatewayRoutingTests {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void testGatewayHealth() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "/actuator/health", String.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void testRouterConfiguration() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "/actuator/routes", String.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("cab-service"));
    }
}
```

---

## Monitoring

### Gateway Metrics

```bash
curl http://localhost:8080/actuator/metrics
```

### Route Statistics

Enable detailed logging:

```properties
logging.level.org.springframework.cloud.gateway=DEBUG
logging.level.org.springframework.cloud.gateway.route=DEBUG
logging.level.com.netflix.discovery=DEBUG
```

---

## Troubleshooting

### Service Discovery Issues

**Problem:** Gateway returns 503 Service Unavailable

**Solutions:**
1. Check if Eureka server is running
2. Verify microservices are registered: `curl http://localhost:8761/eureka/apps`
3. Check service health: `curl http://service:port/actuator/health`
4. Review gateway logs for errors

### Routing Issues

**Problem:** Requests not reaching correct service

**Solutions:**
1. Verify route configuration in application.yml
2. Check predicate matches: `curl http://localhost:8080/actuator/routes`
3. Ensure service is running on expected port
4. Check for port conflicts

### Load Balancing Issues

**Problem:** Requests always go to same instance

**Solutions:**
1. Verify multiple instances registered in Eureka
2. Check load balancer configuration
3. Review timeout and retry settings

---

## Performance Optimization

### Connection Pooling

```yaml
spring:
  cloud:
    gateway:
      httpClient:
        connectTimeout: 5000
        responseTimeout: 10000
```

### Caching

Enable response caching:

```java
@Configuration
public class CachingGatewayConfig {
    
    @Bean
    public GlobalFilter cachingFilter() {
        return (exchange, chain) -> {
            // Cache GET requests for certain routes
            return chain.filter(exchange);
        };
    }
}
```

---

## Logging & Debugging

### Enable Debug Logging

```properties
logging.level.org.springframework.cloud.gateway=DEBUG
logging.level.org.springframework.cloud.gateway.filter=TRACE
logging.level.org.springframework.web=DEBUG
```

### Access Logs

```yaml
server:
  tomcat:
    accesslog:
      enabled: true
      pattern: "%h %l %u %t \"%r\" %s %b %D"
```

---

## Deployment Checklist

- [ ] Java 21 JDK installed
- [ ] Maven configured
- [ ] Discovery Service running (8761)
- [ ] All microservices registered with Eureka
- [ ] application.yml configured with routes
- [ ] Service builds successfully
- [ ] Gateway accessible on port 8080
- [ ] All routes tested
- [ ] Health check responding
- [ ] Routes visible in /actuator/routes
- [ ] Logging configured
- [ ] No startup errors

---

## References

- [Spring Cloud Gateway Documentation](https://spring.io/projects/spring-cloud-gateway)
- [Spring Cloud Documentation](https://cloud.spring.io/)
- [Netflix Eureka](https://github.com/Netflix/eureka/wiki)
- [Load Balancing Guide](https://docs.spring.io/spring-cloud-commons/reference/html/)

