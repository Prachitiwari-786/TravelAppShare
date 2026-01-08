package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Admin;
import com.example.demo.entity.CabDTO;
import com.example.demo.entity.UserDTO;
import com.example.demo.entity.BookingDTO;
import com.example.demo.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RestTemplate restTemplate; // ✅ LoadBalanced bean defined in AdminServiceApplication

    // Register Admin
    @PostMapping("/register")
    public ResponseEntity<Admin> register(@RequestBody Admin admin) {
        Admin saved = adminService.registerAdmin(admin);
        return ResponseEntity.ok(saved);
    }

    // Login Admin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin admin) {
        Admin loggedIn = adminService.validateLogin(admin.getUsername(), admin.getPassword());
        if (loggedIn != null) {
            return ResponseEntity.ok(loggedIn);
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    // View All Cabs (calls cab-service)
    @GetMapping("/cabs")
    public ResponseEntity<List<CabDTO>> getAllCabs() {
        List<CabDTO> cabs = restTemplate.exchange(
            "http://CAB-SERVICE/cab/all",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<CabDTO>>() {}
        ).getBody();
        return ResponseEntity.ok(cabs);
    }

    // Add New Cab (calls cab-service)
    @PostMapping("/cabs")
    public ResponseEntity<CabDTO> addCab(@RequestBody CabDTO cab) {
        CabDTO saved = restTemplate.postForObject("http://CAB-SERVICE/cab/add", cab, CabDTO.class);
        return ResponseEntity.status(201).body(saved);
    }

    // View All Users (calls user-service)
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = restTemplate.exchange(
            "http://USER-SERVICE/users",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<UserDTO>>() {}
        ).getBody();
        return ResponseEntity.ok(users);
    }

    // Delete User (calls user-service)
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        restTemplate.delete("http://USER-SERVICE/users/" + id);
        return ResponseEntity.noContent().build();
    }

    // View All Bookings (calls booking-service)
    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = restTemplate.exchange(
            "http://BOOKING-SERVICE/bookings",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<BookingDTO>>() {}
        ).getBody();
        return ResponseEntity.ok(bookings);
    }
}
