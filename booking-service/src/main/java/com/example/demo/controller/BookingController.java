package com.example.demo.controller;

import com.example.demo.entity.Booking;
import com.example.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/fare")
    public String showFareConfirmation(@RequestParam String source,
                                       @RequestParam String destination,
                                       @RequestParam String type,
                                       HttpSession session,
                                       Model model) {
        // Call Cab Service directly to get cab details
        BookingService.CabResponse cab = bookingService.getCabDetailsByRoute(source, destination, type);

        if (cab == null) {
            model.addAttribute("status", "No matching cab found");
            return "fare-confirmation";
        }

        double fare = cab.getKms() * cab.getFarePerKm();
        Long userId = (Long) session.getAttribute("loggedUserId");

        model.addAttribute("source", cab.getSource());
        model.addAttribute("destination", cab.getDestination());
        model.addAttribute("type", cab.getType());
        model.addAttribute("cabId", cab.getId());
        model.addAttribute("loggedUserId", userId);
        model.addAttribute("fare", fare);
        model.addAttribute("status", "AVAILABLE");

        return "fare-confirmation";
    }


    // Confirm Booking
    @PostMapping("/confirm")
    public String confirmBooking(@RequestParam Long userId,
                                 @RequestParam Long cabId,
                                 Model model) {

        BookingService.CabResponse cab = bookingService.getCabDetails(cabId);
        if (cab == null) {
            model.addAttribute("status", "Cab not found");
            return "booking-confirmation";
        }

        double fare = cab.getKms() * cab.getFarePerKm();

        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setCabId(cabId);
        booking.setTotalFare(fare);
        booking.setStatus("CONFIRMED");

        bookingService.confirmBooking(booking);

        model.addAttribute("bookingId", booking.getId());
        model.addAttribute("cabId", cabId);
        model.addAttribute("fare", fare);
        model.addAttribute("status", "CONFIRMED");

        return "booking-confirmation"; // JSP view name
    }

    @GetMapping("/history")
    public String viewBookingHistory(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("loggedUserId");
        if (userId == null) {
            return "redirect:/user-login";
        }

        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        model.addAttribute("bookings", bookings);
        model.addAttribute("loggedUserId", userId);

        return "booking-history"; // resolves to booking-history.jsp
    }
}
