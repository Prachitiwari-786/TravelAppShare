package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.repo.AdminRepository;
import com.example.demo.service.AdminService;

@Service
public class AdminService {
	@Autowired
    private AdminRepository adminRepository;

    public Admin validateLogin(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }

    public Admin registerAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

	
}
