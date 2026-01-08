package com.example.demo;

import com.example.demo.entity.Booking;
import com.example.demo.repo.BookingRepository;
import com.example.demo.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BookingServiceApplication.class)
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;   // ✅ real service bean

    @MockBean
    private BookingRepository bookingRepository;  // ✅ mock repo

    @MockBean
    private RestTemplate restTemplate;  // ✅ mock RestTemplate

    @Test
    void testCalculateFareByCabId() {
        BookingService.CabResponse cab = new BookingService.CabResponse();
        cab.setId(1L);
        cab.setKms(10);
        cab.setFarePerKm(12.0);

        // Mock RestTemplate call
        when(restTemplate.getForObject("http://CAB-SERVICE/cabs/1", BookingService.CabResponse.class))
            .thenReturn(cab);

        double fare = bookingService.calculateFare(1L);
        assertEquals(120.0, fare);
    }

    @Test
    void testConfirmBooking() {
        Booking booking = new Booking();
        booking.setUserId(1L);
        booking.setCabId(2L);
        booking.setTotalFare(100.0);
        booking.setStatus("CONFIRMED");

        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking saved = bookingService.confirmBooking(booking);
        assertEquals("CONFIRMED", saved.getStatus());
    }
}
