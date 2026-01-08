package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.example.demo.entity.Cab;
import com.example.demo.repo.CabRepository;
import com.example.demo.service.CabService;
import java.util.Optional;

@SpringBootTest(classes = CabServiceApplication.class)
public class CabServiceTest {

    @Autowired
    private CabService cabService;

    @MockBean
    private CabRepository cabRepository;

    @Test
    void testCalculateFare() {
        Cab cab = new Cab();
        cab.setId(1L);
        cab.setKms(10);
        cab.setFarePerKm(12.0);

        // ✅ Use java.util.Optional
        when(cabRepository.findById(1L)).thenReturn(Optional.of(cab));

        double fare = cabService.calculateFare(1L);
        assertEquals(120.0, fare);
    }
}
