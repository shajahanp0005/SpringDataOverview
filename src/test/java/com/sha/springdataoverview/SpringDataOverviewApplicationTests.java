package com.sha.springdataoverview;

import com.sha.springdataoverview.entity.Flight;
import com.sha.springdataoverview.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
//@DataMongoTest
class SpringDataOverviewApplicationTests {

	@Autowired
	private FlightRepository flightRepository;
	//private EntityManager em;
	//@Test
	/*void verifyFlightCanBeSaved() {
		Flight flight = new Flight("Kochi", "London", LocalDate.of(2020,02,22 ));
		em.persist(flight);
		final TypedQuery<Flight> result = em.createQuery("select f from Flight f",Flight.class);
		List<Flight> resultList = result.getResultList();
		assertThat(resultList).hasSize(1).first().isEqualTo(flight);
	}*/

	@Test
	void testCurdOperation(){
		LocalDateTime now = LocalDateTime.now();
		Flight flight = new Flight("Kochi", "London",now);
		flightRepository.save(flight);
		assertThat(flightRepository.findAll()).hasSize(1).first().isEqualToComparingFieldByField(flight);
		flightRepository.deleteById(flight.getId());
		assertThat(flightRepository.count()).isZero();
	}




}
