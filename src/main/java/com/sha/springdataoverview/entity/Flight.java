package com.sha.springdataoverview.entity;




import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Flight {

    @Id
    @GeneratedValue
    private long id;

    //private String id; //mongodb
    private String origin;
    private String destination;
    private LocalDateTime date;


    public Flight() {
    }

    public Flight(String origin) {
        this.origin = origin;
    }

    public Flight(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Flight(String origin, String destination, LocalDateTime date) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", date=" + date +
                '}';
    }
}
