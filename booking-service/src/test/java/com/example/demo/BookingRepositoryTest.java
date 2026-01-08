package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Booking;
import com.example.demo.repo.BookingRepository;

@DataJpaTest
@org.springframework.test.context.ContextConfiguration(classes = BookingServiceApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookingRepositoryTest {
	@Autowired
	private BookingRepository bookingRepository;

	@Test
	void testFindByUserId() {
		Booking booking = new Booking();
		booking.setUserId(1L);
		booking.setCabId(2L);
		booking.setTotalFare(100.0);
		booking.setStatus("CONFIRMED");
		bookingRepository.save(booking);
		List<Booking> bookings = bookingRepository.findByUserId(1L);
		assertFalse(bookings.isEmpty());
		assertEquals(1L, bookings.get(0).getUserId());
	}
}