package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.example.demo.entity.Cab;
import com.example.demo.repo.CabRepository;

@DataJpaTest
//@ContextConfiguration(classes = CabServiceApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CabRepositoryTest {

    @Autowired
    private CabRepository cabRepository;

    @Test
    void testFindBySourceDestinationType() {
        Cab cab = new Cab();
        cab.setSource("Pune");
        cab.setDestination("Mumbai");
        cab.setType("Sedan");
        cab.setKms(10);
        cab.setFarePerKm(12.0);

        cabRepository.save(cab);

        Optional<Cab> found = cabRepository.findBySourceAndDestinationAndType("Pune", "Mumbai", "Sedan");
        assertTrue(found.isPresent());
        assertEquals("Sedan", found.get().getType());
    }
}

