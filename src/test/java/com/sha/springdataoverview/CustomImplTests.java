package com.sha.springdataoverview;

import com.sha.springdataoverview.entity.Flight;
import com.sha.springdataoverview.repository.FlightRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomImplTests {

    @Autowired
    private FlightRepository flightRepository;

    @Test
    public void shouldBeSavedCustomTemp(){
       Flight toDelete=  createFlight("London");
       Flight toKeep=  createFlight("Paris");
       flightRepository.save(toDelete);
       flightRepository.save(toKeep);
       flightRepository.deleteByOrigin("London");

       assertThat(flightRepository.findAll())
               .hasSize(1).first()
               .isEqualToComparingFieldByField(toKeep);
    }

    private Flight createFlight(String origin) {
        Flight flight  = new Flight(origin);
        return flight;
    }
}
