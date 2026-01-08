package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.repo.CabRepository;
import com.example.demo.entity.Cab;
import com.example.demo.service.CabService;

@RestController
@RequestMapping("/cabs")   // ✅ plural naming
public class CabController {

    @Autowired
    private CabService cabService;

    @Autowired
    private CabRepository cabRepository;

    @GetMapping("/all")
    public List<Cab> getAllCabs() {
        return cabService.getAllCabs();
    }

    @GetMapping("/{id}")
    public Cab getCab(@PathVariable Long id) {
        return cabService.getCabById(id);
    }

    @PostMapping("/add")
    public Cab addCab(@RequestBody Cab cab) {
        return cabService.saveCab(cab);
    }

    @PutMapping("/update/{id}")
    public Cab updateCab(@PathVariable Long id, @RequestBody Cab cab) {
        cab.setId(id);
        return cabService.saveCab(cab);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCab(@PathVariable Long id) {
        cabService.deleteCab(id);
    }

    // ✅ Search cab by source, destination, and type
    @GetMapping("/search")
    public ResponseEntity<Cab> searchCab(@RequestParam String source,
                                         @RequestParam String destination,
                                         @RequestParam String type) {
        return cabRepository.findBySourceAndDestinationAndType(source, destination, type)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
