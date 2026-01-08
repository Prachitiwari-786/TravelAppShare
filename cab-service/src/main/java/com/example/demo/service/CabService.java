package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repo.CabRepository;
import com.example.demo.entity.*;

@Service
public class CabService {
	@Autowired
	private CabRepository cabRepository;

	public List<Cab> getAllCabs() {
		return cabRepository.findAll();
	}

	public Cab getCabById(Long id) {
		return cabRepository.findById(id).orElse(null);
	}

	public Cab saveCab(Cab cab) {
		return cabRepository.save(cab);
	}

	public void deleteCab(Long id) {
		cabRepository.deleteById(id);
	}

	public void updateCab(Cab cab) {
		cabRepository.save(cab);
	}
	
	public double calculateFare(Long cabId) {
	    Cab cab = cabRepository.findById(cabId).orElseThrow();
	    return cab.getKms() * cab.getFarePerKm();
	}


}
