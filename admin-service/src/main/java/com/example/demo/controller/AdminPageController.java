package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import com.example.demo.entity.CabDTO;
import com.example.demo.entity.UserDTO;
import com.example.demo.entity.BookingDTO;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @Autowired
    private RestTemplate restTemplate; // ✅ LoadBalanced bean in AdminServiceApplication

    // -------------------- Portal & Dashboard --------------------
    @GetMapping("/portal")
    public String showPortal() {
        return "travel-portal"; // travel-portal.jsp
    }

    @GetMapping("/login-page")
    public String showLoginPage() {
        return "admin-login"; // admin-login.jsp
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "admin-dashboard"; // admin-dashboard.jsp
    }

    // -------------------- Cab Management --------------------
    @GetMapping("/cab-list")
    public String showCabListPage(Model model) {
        List<CabDTO> cabList;
        try {
            cabList = restTemplate.exchange(
                "http://CAB-SERVICE/cabs/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CabDTO>>() {}
            ).getBody();
        } catch (Exception e) {
            cabList = Collections.emptyList();
        }
        model.addAttribute("cabList", cabList);
        return "view-cabs"; // view-cabs.jsp
    }

    @GetMapping("/cabs/add")
    public String showAddCabForm(Model model) {
        model.addAttribute("cab", new CabDTO());
        return "admin-add-cab";
    }

    @PostMapping("/cabs/add")
    public String addCab(@ModelAttribute CabDTO cab) {
        restTemplate.postForObject("http://CAB-SERVICE/cabs/add", cab, CabDTO.class);
        return "redirect:/admin/cab-list";
    }

    @GetMapping("/cabs/edit/{id}")
    public String showEditCabForm(@PathVariable Long id, Model model) {
        CabDTO cab = restTemplate.getForObject("http://CAB-SERVICE/cabs/" + id, CabDTO.class);
        model.addAttribute("cab", cab);
        return "admin-edit-cab";
    }

	/*
	 * @PostMapping("/cabs/edit") public String editCab(@ModelAttribute CabDTO cab)
	 * { restTemplate.put("http://CAB-SERVICE/cab/update", cab); return
	 * "redirect:/admin/cab-list"; }
	
    @PostMapping("/cabs/edit")
    public String editCab(@ModelAttribute CabDTO cab) {
        restTemplate.postForObject("http://CAB-SERVICE/cab/update", cab, CabDTO.class);
        return "redirect:/admin/cab-list";
    }
 */
    @PostMapping("/cabs/edit")
    public String editCab(@ModelAttribute CabDTO cab) {
        restTemplate.put("http://CAB-SERVICE/cabs/update/" + cab.getId(), cab);
        return "redirect:/admin/cab-list";
    }

    
    @GetMapping("/cabs/delete/{id}")
    public String deleteCab(@PathVariable Long id) {
        restTemplate.delete("http://CAB-SERVICE/cabs/delete/" + id);
        return "redirect:/admin/cab-list";
    }

    // -------------------- Booking Management --------------------
    @GetMapping("/bookings")
    public String viewAllBookings(Model model) {
        List<BookingDTO> bookings;
        try {
            bookings = restTemplate.exchange(
                "http://BOOKING-SERVICE/bookings",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BookingDTO>>() {}
            ).getBody();
        } catch (Exception e) {
            bookings = Collections.emptyList();
        }
        model.addAttribute("bookings", bookings);
        return "admin-bookings"; // admin-bookings.jsp
    }

    // -------------------- User Management --------------------
    @GetMapping("/users")
    public String viewAllUsers(Model model) {
        List<UserDTO> users;
        try {
            users = restTemplate.exchange(
                "http://USER-SERVICE/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDTO>>() {}
            ).getBody();
        } catch (Exception e) {
            users = Collections.emptyList();
        }
        model.addAttribute("users", users);
        return "admin-users"; // admin-users.jsp
    }
    
    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        UserDTO user = restTemplate.getForObject("http://USER-SERVICE/users/" + id, UserDTO.class);
        model.addAttribute("user", user);
        return "admin-edit-user"; // new JSP
    }
    
    @PostMapping("/users/edit")
    public String editUser(@ModelAttribute UserDTO user) {
        restTemplate.put("http://USER-SERVICE/users/update/" + user.getId(), user);
        return "redirect:/admin/users";
    }


}
