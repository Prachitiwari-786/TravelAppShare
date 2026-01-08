# Discovery Service - Technical Implementation Guide

**Service Name:** Discovery Service  
**Application Name:** discovery-service  
**Port:** 8761  
**Type:** Eureka Server  
**Spring Boot Version:** 3.5.9  
**Spring Cloud Version:** 2025.0.1  

---

## Overview

The Discovery Service (Eureka Server) serves as the central service registry for the microservices platform. It enables automatic service registration, discovery, and health monitoring of all microservices.

---

## Architecture & Components

### Application Structure

```
discovery-service/
├── src/main/java/com/example/demo/
│   └── DiscoveryServiceApplication.java    # Main Eureka Server application
├── src/main/resources/
│   └── application.properties              # Configuration
├── src/test/java/
│   └── com/example/demo/                   # Unit tests
├── pom.xml
└── target/                                 # Build output
```

---

## Key Dependencies

| Dependency | Version | Purpose |
|------------|---------|---------|
| spring-boot-starter-web | 3.5.9 | Web server support |
| spring-cloud-starter-netflix-eureka-server | 4.1.2 | Eureka Server implementation |
| spring-boot-starter-test | 3.5.9 | Testing framework |
| spring-cloud-dependencies | 2025.0.1 | Cloud dependency management |

---

## Configuration

### Application Properties

**File:** `src/main/resources/application.properties`

```properties
# Spring Application Name
spring.application.name=discovery-service

# Server Port
server.port=8761

# Eureka Server Configuration
# Do not register with itself
eureka.client.register-with-eureka=false

# Do not fetch registry from other instances
eureka.client.fetch-registry=false

# Optional: Eureka server properties
eureka.server.enable-self-preservation=true
eureka.server.eviction-interval-timer-in-ms=60000
```

### Configuration Explanation

| Property | Value | Description |
|----------|-------|-------------|
| `server.port` | 8761 | Port where Eureka server listens |
| `register-with-eureka` | false | Server should not register itself |
| `fetch-registry` | false | Server should not fetch registry from peers |
| `enable-self-preservation` | true | Keep registrations even if heartbeats are missed |
| `eviction-interval-timer-in-ms` | 60000 | Check every 60 seconds for expired instances |

---

## Application Code

### DiscoveryServiceApplication

**File:** `src/main/java/com/example/demo/DiscoveryServiceApplication.java`

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceApplication.class, args);
    }
}
```

**Key Annotation:**
- `@EnableEurekaServer` - Marks this application as a Eureka server

---

## Eureka UI & Monitoring

### Access Eureka Dashboard

Open in web browser:
```
http://localhost:8761/
```

### Dashboard Features

1. **Registered Services Section**
   - Lists all registered microservices
   - Shows service instances
   - Displays health status (UP/DOWN)

2. **Service Details**
   - Service name
   - Host and port
   - Status page URL
   - Health check status
   - Instance count

3. **System Status**
   - Eureka environment
   - Current time
   - Uptime
   - Lease expiration interval

### Example Dashboard View

```
INSTANCES CURRENTLY REGISTERED WITH EUREKA
════════════════════════════════════════════

Application: CAB-SERVICE (1 instance)
  localhost:cab-service:8081   UP (1)   - http://localhost:8081/actuator

Application: USER-SERVICE (1 instance)
  localhost:user-service:8082  UP (1)   - http://localhost:8082/actuator

Application: BOOKING-SERVICE (1 instance)
  localhost:booking-service:8083  UP (1) - http://localhost:8083/actuator

Application: ADMIN-SERVICE (1 instance)
  localhost:admin-service:8084   UP (1)  - http://localhost:8084/actuator

Application: GATEWAY-SERVICE (1 instance)
  localhost:gateway-service:8080  UP (1) - http://localhost:8080/actuator
```

---

## REST API Endpoints

### Get All Registered Applications

**Request:**
```http
GET /eureka/apps
```

**Response:**
```xml
<applications version-delta="0">
  <apps__hashcode>UP_1_0_0_DOWN_0_0_0_</apps__hashcode>
  <application>
    <name>CAB-SERVICE</name>
    <instance>
      <instanceId>localhost:cab-service:8081</instanceId>
      <hostName>localhost</hostName>
      <app>CAB-SERVICE</app>
      <ipAddr>127.0.0.1</ipAddr>
      <status>UP</status>
      <overriddenStatus>UNKNOWN</overriddenStatus>
      <port enabled="true">8081</port>
      <securePort enabled="false">443</securePort>
      <countryId>1</countryId>
      <dataCenterInfo>
        <name>MyOwn</name>
      </dataCenterInfo>
      <leaseInfo>
        <renewalIntervalInSecs>30</renewalIntervalInSecs>
        <durationInSecs>90</durationInSecs>
        <registrationTimestamp>1704691845123</registrationTimestamp>
        <lastRenewalTimestamp>1704691905456</lastRenewalTimestamp>
      </leaseInfo>
      <metadata/>
      <homePageUrl>http://localhost:8081/</homePageUrl>
      <statusPageUrl>http://localhost:8081/actuator/health</statusPageUrl>
      <healthCheckUrl>http://localhost:8081/actuator/health</healthCheckUrl>
      <vipAddress>cab-service</vipAddress>
      <secureVipAddress>cab-service</secureVipAddress>
      <isCoordinatingDiscoveryServer>false</isCoordinatingDiscoveryServer>
      <lastUpdatedTimestamp>1704691845123</lastUpdatedTimestamp>
      <lastDirtyTimestamp>1704691845123</lastDirtyTimestamp>
      <actionType>ADDED</actionType>
    </instance>
  </application>
</applications>
```

**HTTP Status:** 200 OK

---

### Get Specific Application

**Request:**
```http
GET /eureka/apps/{serviceName}
```

**Example:**
```http
GET /eureka/apps/CAB-SERVICE
```

**HTTP Status:** 200 OK | 404 Not Found

---

### Get Service Instance

**Request:**
```http
GET /eureka/apps/{serviceName}/{instanceId}
```

**Example:**
```http
GET /eureka/apps/CAB-SERVICE/localhost:cab-service:8081
```

**HTTP Status:** 200 OK | 404 Not Found

---

## Service Registration Process

### Lifecycle of a Service Instance

```
1. Service Startup
   ├─ Service connects to Eureka server
   ├─ Sends registration request with service metadata
   └─ Eureka server stores registration

2. Heartbeat Interval (every 30 seconds)
   ├─ Service sends renewal request
   ├─ Eureka confirms received
   └─ Updates lastRenewalTimestamp

3. Service Health Check (every 10 seconds)
   ├─ Eureka queries /actuator/health endpoint
   ├─ Service responds with health status
   └─ Eureka updates service status (UP/DOWN)

4. Service Shutdown
   ├─ Service sends deregistration request
   ├─ Eureka removes service instance
   └─ Service becomes unavailable for discovery
```

### Configuration Parameters

| Parameter | Default | Purpose |
|-----------|---------|---------|
| `lease-renewal-interval-in-seconds` | 30 | How often service renews lease |
| `lease-expiration-duration-in-seconds` | 90 | How long before instance is expired |
| `health-check-url-path` | /actuator/health | Where Eureka checks health |

---

## Build & Run

### Prerequisites

- Java 21 JDK
- Maven 3.8+

### Building

```bash
cd discovery-service

# Clean build
mvn clean install

# Build without tests
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
java -jar target/discovery-service-0.0.1-SNAPSHOT.jar
```

### Verify Service is Running

```bash
# Check if port 8761 is listening
netstat -ano | findstr :8761      # Windows
lsof -i :8761                      # macOS/Linux

# Test Eureka API
curl http://localhost:8761/eureka/apps

# Access dashboard
# Open browser: http://localhost:8761/
```

---

## Client Configuration (For Other Services)

### Enable Service Discovery in Microservices

Each microservice must be configured to register with Eureka:

**application.properties:**
```properties
# Enable Eureka client
spring.cloud.discovery.enabled=true

# Service name (used for discovery)
spring.application.name=cab-service

# Eureka server URL
eureka.client.service-url.defaultZone=http://localhost:8761/eureka

# Client configuration
eureka.instance.prefer-ip-address=true
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
```

### POM Dependency

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

---

## Monitoring & Troubleshooting

### Check Service Registration

```bash
# View all registered services
curl http://localhost:8761/eureka/apps

# Check specific service
curl http://localhost:8761/eureka/apps/CAB-SERVICE

# Get instance details
curl http://localhost:8761/eureka/apps/CAB-SERVICE/localhost:cab-service:8081
```

### Common Issues & Solutions

| Issue | Cause | Solution |
|-------|-------|----------|
| Service not appearing in Eureka | Service not started | Ensure service is running and properly configured |
| Service marked as DOWN | Health check failing | Check service logs and health endpoint |
| Eureka shows many DOWN services | Network issues | Check network connectivity |
| Services disappear from registry | Lease expired | Ensure heartbeat configuration is correct |

### Log Analysis

```bash
# Start with debug logging
mvn spring-boot:run -X

# Watch Eureka logs in real-time
tail -f logs/discovery-service.log | grep "Registered"
```

### Debug Endpoints

Enable detailed logging:

```properties
logging.level.com.netflix.eureka=DEBUG
logging.level.com.netflix.discovery=DEBUG
logging.level.org.springframework.cloud=DEBUG
```

---

## Performance Tuning

### Server Properties

```properties
# Self-preservation mode (recommended for production)
eureka.server.enable-self-preservation=true

# How often to check for expired leases (milliseconds)
eureka.server.eviction-interval-timer-in-ms=60000

# Response cache update interval (seconds)
eureka.server.response-cache-update-interval-ms=30000

# Response cache auto expiration (seconds)
eureka.server.response-cache-auto-expiration-in-seconds=180
```

### Client Optimization

```properties
# How often to fetch full registry (seconds)
eureka.client.registry-fetch-interval-seconds=30

# Initial delay before fetching registry (seconds)
eureka.client.initial-instance-info-replication-interval-seconds=40

# Interval to replicate instance changes (seconds)
eureka.client.instance-info-replication-interval-seconds=30
```

---

## High Availability Setup (Optional)

For production, you can run multiple Eureka servers:

### Eureka Server 1 Configuration

```properties
server.port=8761
spring.application.name=discovery-service
eureka.instance.hostname=eureka1
eureka.client.service-url.defaultZone=http://eureka2:8762/eureka/,http://eureka3:8763/eureka/
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
```

### Eureka Server 2 Configuration

```properties
server.port=8762
spring.application.name=discovery-service
eureka.instance.hostname=eureka2
eureka.client.service-url.defaultZone=http://eureka1:8761/eureka/,http://eureka3:8763/eureka/
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
```

### Load Balancer Configuration

Services connect to all Eureka servers:

```properties
eureka.client.service-url.defaultZone=http://eureka1:8761/eureka/,http://eureka2:8762/eureka/,http://eureka3:8763/eureka/
```

---

## Testing

### Unit Tests

```java
@SpringBootTest
public class DiscoveryServiceApplicationTests {
    
    @Test
    public void contextLoads() {
        // Application context should load successfully
    }
}
```

### Integration Tests

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EurekaIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void testEurekaServerIsUp() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "/eureka/apps", String.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
```

---

## Metrics & Health

### Health Check

```bash
curl http://localhost:8761/actuator/health
```

**Response:**
```json
{
    "status": "UP",
    "components": {
        "discoveryComposite": {
            "status": "UP"
        }
    }
}
```

---

## References

- [Netflix Eureka Wiki](https://github.com/Netflix/eureka/wiki)
- [Spring Cloud Netflix Eureka](https://cloud.spring.io/spring-cloud-netflix/reference/html/)
- [Eureka Understanding Eureka Documentation](https://github.com/Netflix/eureka/wiki/Eureka-Client-Configuration)
- [Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

---

## Deployment Checklist

- [ ] Java 21 JDK installed
- [ ] Maven configured
- [ ] application.properties updated
- [ ] Service builds successfully
- [ ] Eureka Dashboard accessible on port 8761
- [ ] REST API endpoints responding
- [ ] Logging configured
- [ ] No startup errors in logs

