package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.example.demo.entity.BookingDTO;

import com.example.demo.entity.UserDTO;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserPageController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/user-register")
	public String registerPage() {
		return "user-register"; // JSP in Admin Service
	}

	/*
	 * @PostMapping("/register-user") public String registerUser(UserDTO user) { //
	 * Call User Service REST API
	 * restTemplate.postForObject("http://USER-SERVICE/users/register", user,
	 * UserDTO.class); return "redirect:/user-login"; }
	 */
	
	@PostMapping("/login-user")
	public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session,
			Model model) {

		// Call User Service REST API
		UserDTO user = restTemplate.postForObject(
				"http://USER-SERVICE/users/login?username=" + username + "&password=" + password, null, UserDTO.class);

		if (user == null) {
			model.addAttribute("error", "Invalid username or password");
			return "user-login";
		}

		session.setAttribute("loggedUser", user);
		return "redirect:/user-dashboard";
	}

	@GetMapping("/user-dashboard")
	public String showDashboard(HttpSession session, Model model) {
		UserDTO loggedUser = (UserDTO) session.getAttribute("loggedUser");
		model.addAttribute("loggedUser", loggedUser);
		return "user-dashboard"; // JSP in Admin Service
	}

	@GetMapping("/user-login")
	public String loginPage(Model model, @RequestParam(required = false) String logoutMessage) {
		model.addAttribute("logoutMessage", logoutMessage);
		return "user-login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/user-login?logoutMessage=You have been logged out successfully.";
	}

	@GetMapping("/book-cab")
	public String showBookCabPage() {
		return "book-cab"; // resolves to /WEB-INF/views/book-cab.jsp
	}

	@GetMapping("/booking/history")
	public String showBookingHistory(HttpSession session, Model model) {
		UserDTO loggedUser = (UserDTO) session.getAttribute("loggedUser");

		// Call Booking Service REST API to fetch this user's bookings
		List<BookingDTO> bookings = Collections.emptyList();
		try {
			bookings = restTemplate.exchange("http://BOOKING-SERVICE/bookings/user/" + loggedUser.getId(),
					HttpMethod.GET, null, new ParameterizedTypeReference<List<BookingDTO>>() {
					}).getBody();
		} catch (Exception e) {
			// fallback to empty list if service call fails
		}

		model.addAttribute("bookings", bookings);
		return "booking-history"; // resolves to /WEB-INF/views/booking-history.jsp
	}

	@PostMapping("/register-user")
	public String registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password,
			@RequestParam String confirmPassword, Model model) {
		// Validate confirm password
		if (!password.equals(confirmPassword)) {
			model.addAttribute("message", "Passwords do not match!");
			return "user-register"; // reload registration page with error
		}
		// Build DTO
		UserDTO user = new UserDTO();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password); // now captured correctly
		user.setRole("USER"); // default role
		// Call User Service REST API
		try {
			restTemplate.postForObject("http://USER-SERVICE/users/register", user, UserDTO.class);
			model.addAttribute("message", "Registration successful! Please login.");
			return "redirect:/user-login";
		} catch (Exception e) {
			model.addAttribute("message", "Registration failed. Try again.");
			return "user-register";
		}
	}
	
	@GetMapping("/booking/fare")
	public String showFare(@RequestParam String source,
	                       @RequestParam String destination,
	                       @RequestParam String type,
	                       Model model) {
	    // Call Booking Service REST API
	    Double fare = restTemplate.getForObject(
	        "http://BOOKING-SERVICE/bookings/fare?source=" + source + "&destination=" + destination + "&type=" + type,
	        Double.class
	    );

	    model.addAttribute("source", source);
	    model.addAttribute("destination", destination);
	    model.addAttribute("type", type);
	    model.addAttribute("fare", fare);

	    return "fare-confirmation"; // resolves to /WEB-INF/views/fare-confirmation.jsp
	}


}
