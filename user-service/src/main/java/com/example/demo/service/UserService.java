package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	// Register User
	public User register(User user) {
		// Optional: check if username already exists
		User existing = userRepository.findByUsername(user.getUsername());
		if (existing != null) {
			return null; // username taken
		}

		user.setRole("USER"); // default role
		return userRepository.save(user);
	}

	// Login User
	public User login(String username, String password) {
		User user = userRepository.findByUsername(username);

		if (user != null && user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}

	// Get User Profile
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	// Get all users
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	// 🔹 Optional: Delete user
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
	
	// Update user
	public User updateUser(Long id, User user) {
	    User existing = userRepository.findById(id).orElse(null);
	    if (existing != null) {
	        existing.setUsername(user.getUsername());
	        existing.setEmail(user.getEmail());
	        existing.setRole(user.getRole());
	        // optionally update password if allowed
	        return userRepository.save(existing);
	    }
	    return null;
	}


}
