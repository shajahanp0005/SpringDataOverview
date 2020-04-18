package com.sha.springdataoverview;

import com.sha.springdataoverview.entity.Flight;
import com.sha.springdataoverview.repository.FlightRepository;
import com.sha.springdataoverview.service.FlightService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TransationalTests {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightService flightService;

    @Before
    public void setUp(){
        flightRepository.deleteAll();
    }

    @Test
    public void testTransaction(){
        try {
            flightService.saveFlight(new Flight());
        }catch (Exception e){

        }finally {
            assertThat(flightRepository.findAll())
                    .isNotEmpty();
        }

    }
}
