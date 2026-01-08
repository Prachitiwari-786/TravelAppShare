package com.example.demo.controller;

import com.example.demo.entity.Cab;
import com.example.demo.service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin") // admin URLs
public class CabPageController {

    @Autowired
    private CabService cabService;

    @GetMapping("/add-cab")
    public String showAddCabPage() {
        return "add-cab"; // resolves to /WEB-INF/views/add-cab.jsp
    }
    
    @PostMapping("/add-cab")
    public String addCab(Cab cab) {
        cabService.saveCab(cab);
        return "redirect:/admin/view-cabs"; // after saving, go back to view page
    }
    
    @GetMapping("/view-cabs")
    public String showAllCabsPage(Model model) {
        List<Cab> cabList = cabService.getAllCabs();
        model.addAttribute("cabList", cabList);
        return "view-cabs"; // resolves to /WEB-INF/views/view-cabs.jsp
    }

	/*
	 * // Show Edit Cab Page
	 * 
	 * @GetMapping("/edit-page/{id}") public String showEditCabPage(@PathVariable
	 * Long id, Model model) { Cab cab = cabService.getCabById(id);
	 * model.addAttribute("cab", cab); return "edit-cab"; // maps to edit-cab.jsp }
	 */
    
    @GetMapping("/delete/{id}")
    public String deleteCab(@PathVariable Long id) {
        cabService.deleteCab(id);
        return "redirect:/admin/view-cabs";
    }
    
    @GetMapping("/edit-page/{id}")
    public String showEditCabPage(@PathVariable Long id, Model model) {
        Cab cab = cabService.getCabById(id);
        model.addAttribute("cab", cab);
        return "edit-cab"; // maps to edit-cab.jsp
    }

    @PostMapping("/edit-page/{id}")
    public String updateCab(@PathVariable Long id, Cab cab) {
        cab.setId(id);              // ensure the ID is set
        cabService.saveCab(cab);    // update in DB
        return "redirect:/admin/view-cabs"; // go back to list
    }

}