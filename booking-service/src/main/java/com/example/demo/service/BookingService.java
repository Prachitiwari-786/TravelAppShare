package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.repo.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private RestTemplate restTemplate;

	// Calculate fare by cabId
	public double calculateFare(Long cabId) {
		CabResponse cab = getCabDetails(cabId);
		if (cab == null) {
			return -1;
		}
		return cab.getKms() * cab.getFarePerKm();
	}

	// Fetch cab details by cabId
	public CabResponse getCabDetails(Long cabId) {
		return restTemplate.getForObject("http://CAB-SERVICE/cabs/" + cabId, // ✅ plural
				CabResponse.class);
	}

	// Save booking
	public Booking confirmBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	// Get bookings by userId
	public List<Booking> getBookingsByUserId(Long userId) {
		return bookingRepository.findByUserId(userId);
	}

	// Get all bookings
	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	// ✅ Calculate fare by source/destination/type via Cab Service
	/*
	 * public double calculateFare(String source, String destination, String type) {
	 * CabResponse cab = restTemplate.getForObject(
	 * "http://CAB-SERVICE/cabs/search?source=" + source + "&destination=" +
	 * destination + "&type=" + type, CabResponse.class); if (cab == null) { return
	 * -1; // no cab found } return cab.getKms() * cab.getFarePerKm(); }
	 */
	
	public double calculateFare(String source, String destination, String type) {
	    CabResponse cab = getCabDetailsByRoute(source, destination, type);
	    if (cab == null) return -1;
	    return cab.getKms() * cab.getFarePerKm();
	}


	// Fetch cab details by source/destination/type
	
	  public CabResponse getCabDetailsByRoute(String source, String destination,
	  String type) { return restTemplate.getForObject(
	  "http://CAB-SERVICE/cabs/search?source=" + source + "&destination=" +
	  destination + "&type=" + type, CabResponse.class); }
	 
	/*
	 * public CabResponse getCabDetailsByRoute(String source, String destination,
	 * String type) { return restTemplate.getForObject(
	 * "http://localhost:8081/cabs/search?source=" + source + "&destination=" +
	 * destination + "&type=" + type, CabResponse.class ); }
	 */

	// DTO for cab-service response
	public static class CabResponse {
		private Long id;
		private String source;
		private String destination;
		private String type;
		private int kms;
		private double farePerKm;

		// Getters and setters
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getDestination() {
			return destination;
		}

		public void setDestination(String destination) {
			this.destination = destination;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getKms() {
			return kms;
		}

		public void setKms(int kms) {
			this.kms = kms;
		}

		public double getFarePerKm() {
			return farePerKm;
		}

		public void setFarePerKm(double farePerKm) {
			this.farePerKm = farePerKm;
		}
	}
}
