package com.sha.springdataoverview;

import com.sha.springdataoverview.entity.Flight;
import com.sha.springdataoverview.repository.FlightRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Direction.DESC;

//@RunWith(SpringRunner.class)
@DataJpaTest
//@DataMongoTest
public class PagingAndSortingRepoTest {

    @Autowired
    private FlightRepository flightRepository;

    @Before
    public void setUp(){
        flightRepository.deleteAll();
    }

    @Test
    public void shouldSortByFlightByDestionation(){
        LocalDateTime now = LocalDateTime.now();
        final Flight madrid = createFlight("London", "Madrid",now);
        final Flight london = createFlight("Madrid", "London",now);
        final Flight kochi = createFlight("Kochi", "Paris",now);

        flightRepository.save(madrid);
        flightRepository.save(london);
        flightRepository.save(kochi);

        final Iterable<Flight> flights = flightRepository.findAll(Sort.by("destination"));

        assertThat(flights).hasSize(3);
        final Iterator<Flight> iterator = flights.iterator();
        assertThat(iterator.next().getDestination()).isEqualTo("London");
        assertThat(iterator.next().getDestination()).isEqualTo("Madrid");
        assertThat(iterator.next().getDestination()).isEqualTo("Paris");


    }

    @Test
    public void shouldSortByFlightByScheduledAndThenNames(){
        LocalDateTime now = LocalDateTime.now();
        final Flight paris1 = createFlight("London", "Paris",now);
        final Flight paris2 = createFlight("Madrid", "Paris",now.plusHours(2));
        final Flight paris3 = createFlight("Kochi", "Paris",now.minusHours(1));

        final Flight london1 = createFlight("Glasgow", "London",now.plusHours(1));
        final Flight london2 = createFlight("Madrid", "London",now);



        flightRepository.save(paris1);
        flightRepository.save(paris2);
        flightRepository.save(paris3);
        flightRepository.save(london1);
        flightRepository.save(london2);

        final Iterable<Flight> flights = flightRepository.findAll(Sort.by("destination","date"));

        assertThat(flights).hasSize(5);
        final Iterator<Flight> iterator = flights.iterator();
       // assertThat(iterator.next().getDestination()).isEqualTo(london1);
        //assertThat(iterator.next().getDestination()).isEqualTo(london1);
        //assertThat(iterator.next().getDestination()).isEqualTo(paris3);
        //assertThat(iterator.next().getDestination()).isEqualTo(paris1);
        //assertThat(iterator.next().getDestination()).isEqualTo(paris2);


    }

    @Test
    public void  shouldPageResult(){
        for (int i=0 ; i< 50 ;i++){
            flightRepository.save(createFlight(String.valueOf(i)));
        }

       final Page<Flight> page = flightRepository.findAll(PageRequest.of(2,5));
        assertThat(page.getTotalElements()).isEqualTo(50);
        assertThat(page.getNumberOfElements()).isEqualTo(5);
        assertThat(page.getTotalPages()).isEqualTo(10);
        assertThat(page.getContent())
                .extracting(Flight::getDestination)
                .containsExactly("10","11","12","13","14");

    }
    @Test
    public void  shouldPageAndSortResult() {
        for (int i = 0; i < 50; i++) {
            flightRepository.save(createFlight(String.valueOf(i)));
        }

        final Page<Flight> page = flightRepository.findAll(PageRequest.of(2, 5,Sort.by(DESC,"destination")));
        assertThat(page.getTotalElements()).isEqualTo(50);
        assertThat(page.getNumberOfElements()).isEqualTo(5);
        assertThat(page.getTotalPages()).isEqualTo(10);
        assertThat(page.getContent())
                .extracting(Flight::getDestination)
                .containsExactly("44", "43", "42", "41", "40");
    }

    @Test
    public void  shouldPageAndSortDerivedQueryResult() {
        for (int i = 0; i < 10; i++) {
            final Flight flight = createFlight(String.valueOf(i));
            flight.setOrigin("Paris");
            flightRepository.save(flight);
        }

        for (int i = 0; i < 10; i++) {
            final Flight flight = createFlight(String.valueOf(i));
            flight.setOrigin("London");
            flightRepository.save(flight);
        }

        final Page<Flight> page = flightRepository
                .findByOrigin("London",PageRequest.of(0, 5,Sort.by(DESC,"destination")));
        assertThat(page.getTotalElements()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(5);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.getContent())
                .extracting(Flight::getDestination)
                .containsExactly("9", "8", "7", "6", "5");
    }


    private Flight createFlight(String destination) {
        LocalDateTime now = LocalDateTime.now();

        Flight flight  = new Flight("Test Flight",destination,now);
        return flight;
    }

    private Flight createFlight(String origin, String destination, LocalDateTime sheduledAt) {
        Flight flight  = new Flight(origin,destination,sheduledAt);
        return flight;
    }
}
