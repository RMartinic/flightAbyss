package com.rmartinic.flightabyss;
import com.amadeus.Amadeus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AmadeusAuth {

    private final Amadeus amadeus;

    public AmadeusAuth(
            @Value("${amadeus.api.key}") String apiKey,
            @Value("${amadeus.api.secret}") String apiSecret
    ) {
        this.amadeus = Amadeus.builder(apiKey, apiSecret).build();
    }

    public Amadeus getAmadeusClient() {
        return amadeus;
    }
}


