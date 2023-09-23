package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {

        // Create a new Parking Lot Entity
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);

        // Save the Entity to DB
        return parkingLotRepository1.save(parkingLot);
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {

        // Check if the Parking Lot is Present
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository1.findById(parkingLotId);

        if(!optionalParkingLot.isPresent()){
            throw new RuntimeException("ParkingLot Id is incorrect");
        }

        // Respective Parking Lot
        ParkingLot parkingLot = optionalParkingLot.get();

        // Create a new Spot
        Spot spot = new Spot();
        spot.setPricePerHour(pricePerHour);

        // Set the SpotType
        if(numberOfWheels <= 2){
            spot.setSpotType(SpotType.TWO_WHEELER);
        } else if (numberOfWheels <= 4) {
            spot.setSpotType(SpotType.FOUR_WHEELER);
        }else {
            spot.setSpotType(SpotType.OTHERS);
        }

        parkingLot.getSpots().add(spot);
        spot.setParkingLot(parkingLot);

        // Save the Spot in DB
        return spotRepository1.save(spot);

    }

    @Override
    public void deleteSpot(int spotId) {

        Optional<Spot> spot = spotRepository1.findById(spotId);
        if(spot.isPresent()){
            spotRepository1.delete(spot.get());
        }


    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {

        Optional<Spot> optionalSpot = spotRepository1.findById(spotId);
        if(!optionalSpot.isPresent()){
            throw new RuntimeException("SpotID is not present");
        }

        Spot spot = optionalSpot.get();

        spot.setPricePerHour(pricePerHour);

        return spotRepository1.save(spot);
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {

        Optional<ParkingLot> parkingLot = parkingLotRepository1.findById(parkingLotId);
        if(parkingLot.isPresent()){
            parkingLotRepository1.delete(parkingLot.get());
        }
    }
}
