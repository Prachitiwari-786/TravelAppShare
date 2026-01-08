package com.example.demo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.entity.Admin;
import com.example.demo.repo.AdminRepository;
import com.example.demo.service.AdminService;
import com.google.common.base.Optional;

@SpringBootTest(classes = AdminServiceApplication.class)
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @MockBean
    private AdminRepository adminRepository;

    @Test
    void testValidateLogin() {
        Admin admin = new Admin();
        admin.setUsername("rohit");
        admin.setPassword("rohit@123");

        when(adminRepository.findByUsername("rohit")).thenReturn(admin);

        // ✅ Expect an Admin object, not boolean
        Admin found = adminService.validateLogin("rohit", "rohit@123");

        assertNotNull(found);
        assertEquals("rohit", found.getUsername());
        assertEquals("rohit@123", found.getPassword());
    }
}
