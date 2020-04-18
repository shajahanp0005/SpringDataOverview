package com.sha.springdataoverview;


import com.sha.springdataoverview.entity.Flight;
import com.sha.springdataoverview.repository.FlightRepository;


import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
@DataJpaTest
//@DataMongoTest
public class DerivedQureyTest {

    @Autowired
    private FlightRepository flightRepository;

    @Before
    public void setUp(){
        flightRepository.deleteAll();
    }

    @Test
    public void shouldFindFlightFromLondon(){
        final Flight f1 = createFlight("London");
        final Flight f2 = createFlight("London");
        final Flight f3 = createFlight("New York");
        final Flight f4 = createFlight("Paris");

        flightRepository.save(f1);
        flightRepository.save(f2);
        flightRepository.save(f3);
        flightRepository.save(f4);

        List<Flight> flighToLondon = flightRepository.findByOrigin("London");

        assertThat(flighToLondon).hasSize(2);
        assertThat(flighToLondon.get(0)).isEqualToComparingFieldByField(f1);
        assertThat(flighToLondon.get(1)).isEqualToComparingFieldByField(f2);




    }

    @Test
    public void testFlightFromLondonToParis(){
        Flight f1 = new Flight("London","Paris");
        Flight f2 = new Flight("London","Kochi");
        Flight f3 = new Flight("London","Paris");

        flightRepository.save(f1);
        flightRepository.save(f2);
        flightRepository.save(f3);

        List<Flight> flights = flightRepository.findByOriginAndDestination("London","Paris");
        assertThat(flights).hasSize(2);
        assertThat(flights.get(0)).isEqualToComparingFieldByField(f1);
        assertThat(flights.get(1)).isEqualToComparingFieldByField(f3);
    }

    @Test
    public void testFlightFromLondoOrParis(){

        Flight f1 = new Flight("London","Paris");
        Flight f2 = new Flight("London","Kochi");
        Flight f3 = new Flight("Paris","Kochi");

        flightRepository.save(f1);
        flightRepository.save(f2);
        flightRepository.save(f3);

        List<Flight> flights = flightRepository.findByOriginIn("London","Paris");
        assertThat(flights).hasSize(3);
        assertThat(flights.get(0)).isEqualToComparingFieldByField(f1);
        assertThat(flights.get(2)).isEqualToComparingFieldByField(f3);

    }
    @Test
    public void testFlightOriginIgnoreCase(){
        Flight f1 = new Flight("London","Paris");


        flightRepository.save(f1);


        List<Flight> flights = flightRepository.findByOriginIgnoreCase("LONDON");
        assertThat(flights).hasSize(1);
        assertThat(flights.get(0)).isEqualToComparingFieldByField(f1);


    }

    public Flight createFlight(String origin){
        Flight flight = new Flight(origin);
        return flight;
    }

    public Flight createFlight(String origin,String destination){
        Flight flight = new Flight(origin,destination);
        return flight;
    }


}
