package com.example.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.BookingController;
import com.example.demo.service.BookingService;

//@WebMvcTest(BookingController.class)
@SpringBootTest(classes = BookingServiceApplication.class) 
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService; // mock service

    @Test
    void testShowFareConfirmationAvailable() throws Exception {
        BookingService.CabResponse cab = new BookingService.CabResponse();
        cab.setId(1L);
        cab.setSource("Pune");
        cab.setDestination("Mumbai");
        cab.setType("Sedan");
        cab.setKms(10);
        cab.setFarePerKm(12.0);

        when(bookingService.getCabDetailsByRoute("Pune", "Mumbai", "Sedan")).thenReturn(cab);

        mockMvc.perform(get("/booking/fare")
                .param("source", "Pune")
                .param("destination", "Mumbai")
                .param("type", "Sedan"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("status", "AVAILABLE"))
                .andExpect(view().name("fare-confirmation"));
    }
}
