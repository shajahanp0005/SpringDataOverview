package com.sha.springdataoverview.repository;

import com.sha.springdataoverview.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FlightRepository extends PagingAndSortingRepository<Flight, Long>,DeleteByOriginRepository/*rudRepository<Flight, Long> */{
    List<Flight> findByOrigin(String london);
    List<Flight> findByOriginAndDestination(String origin, String destination);
    List<Flight> findByOriginIn(String ...origins);
    List<Flight> findByOriginIgnoreCase(String origin);

    //PagingAndSortingRepository
    Page<Flight> findByOrigin(String origin, Pageable pageRequest);

}
