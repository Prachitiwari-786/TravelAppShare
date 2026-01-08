package com.example.demo.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Cab;

public interface CabRepository extends JpaRepository<Cab, Long> {
	// Custom finder method 
	Optional<Cab> findBySourceAndDestinationAndType(String source, String destination, String type);
}
