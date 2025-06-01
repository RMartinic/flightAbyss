package com.rmartinic.flightabyss;

import com.amadeus.resources.FlightOfferSearch;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightService flightService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public FlightService flightService() {

            return Mockito.mock(FlightService.class);
        }
    }

    @Test
    public void searchFlights_noResults_showsErrorMessage() throws Exception {
        Mockito.when(flightService.searchWithCache(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyString()
        )).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/search")
                        .param("originAirport", "ZAG")
                        .param("destinationAirport", "FRA")
                        .param("departureDate", "2025-06-10")
                        .param("returnDate", "2025-06-15")
                        .param("numberOfPassengers", "1")
                        .param("currency", "EUR"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errorNoFlights"));
    }
}
