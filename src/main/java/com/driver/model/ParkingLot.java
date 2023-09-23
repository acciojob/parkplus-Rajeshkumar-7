package com.driver.model;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    String address;

    @OneToMany(mappedBy = "parkingLot" , cascade = CascadeType.ALL)
    List<Spot> spots = new ArrayList<>();

    public ParkingLot() {
    }

    public ParkingLot(int id, String name, String address, List<Spot> spots) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.spots = spots;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }
}
