package com.example.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.CabController;
import com.example.demo.entity.Cab;
import com.example.demo.service.CabService;

@WebMvcTest(CabController.class)
//@ContextConfiguration(classes = CabServiceApplication.class)
public class CabControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CabService cabService;

    @Test
    void testViewCabDetails() throws Exception {
        Cab cab = new Cab();
        cab.setId(1L);
        cab.setSource("Pune");
        cab.setDestination("Mumbai");
        cab.setType("Sedan");

        when(cabService.getCabById(1L)).thenReturn(cab);

        mockMvc.perform(get("/cab/view/1"))
               .andExpect(status().isOk())
               .andExpect(model().attribute("cab", cab))
               .andExpect(view().name("cab-details"));
    }
}

