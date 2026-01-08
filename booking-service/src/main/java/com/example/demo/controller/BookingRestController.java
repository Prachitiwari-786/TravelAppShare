package com.example.demo.controller;

import com.example.demo.entity.Booking;
import com.example.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/confirm")
    public ResponseEntity<Booking> confirmBooking(@RequestBody Booking booking) {
        double fare = bookingService.calculateFare(booking.getCabId());
        if (fare == -1) {
            return ResponseEntity.badRequest().build();
        }

        booking.setTotalFare(fare);
        booking.setStatus("CONFIRMED");

        Booking saved = bookingService.confirmBooking(booking);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable Long userId) {
        return bookingService.getBookingsByUserId(userId);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/fare/{cabId}")
    public ResponseEntity<?> calculateFare(@PathVariable Long cabId) {
        double fare = bookingService.calculateFare(cabId);
        if (fare == -1) {
            return ResponseEntity.badRequest().body("Cab not found");
        }
        return ResponseEntity.ok(fare);
    }
    
    @GetMapping("/fare")
    public ResponseEntity<?> calculateFareByRoute(@RequestParam String source,
                                                  @RequestParam String destination,
                                                  @RequestParam String type) {
        double fare = bookingService.calculateFare(source, destination, type);
        if (fare == -1) {
            return ResponseEntity.badRequest().body("No cab found");
        }
        return ResponseEntity.ok(fare);
    }
}
