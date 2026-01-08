package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;

	// Register
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {
		User saved = userService.register(user);

		if (saved == null) {
			return ResponseEntity.badRequest().body("Username already exists");
		}

		return ResponseEntity.ok(saved);
	}

	/*
	 * // Login
	 * 
	 * @PostMapping("/login") public ResponseEntity<?> login(@RequestBody User user)
	 * { User loggedIn = userService.login(user.getUsername(), user.getPassword());
	 * 
	 * if (loggedIn == null) { return
	 * ResponseEntity.status(401).body("Invalid username or password"); }
	 * 
	 * return ResponseEntity.ok(loggedIn); }
	 */
	
	// Login
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String username,
	                               @RequestParam String password) {
	    User loggedIn = userService.login(username, password);

	    if (loggedIn == null) {
	        return ResponseEntity.status(401).body("Invalid username or password");
	    }

	    return ResponseEntity.ok(loggedIn);
	}


	// Get Profile
	@GetMapping("/{id}")
	public ResponseEntity<?> getProfile(@PathVariable Long id) {
		User user = userService.getUserById(id);

		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(user);
	}

	// Get all users
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		User updated = userService.updateUser(id, user);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
