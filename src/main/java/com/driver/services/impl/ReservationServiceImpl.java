package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {


        // Check the User is Present or Not
        Optional<User> optionalUser = userRepository3.findById(userId);
        if(!optionalUser.isPresent()){
            throw new Exception("Cannot make reservation");
        }

        // Check the Parking lot is present or not
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository3.findById(parkingLotId);
        if(!optionalParkingLot.isPresent()){
            throw new Exception("Cannot make reservation");
        }

        User user = optionalUser.get();
        ParkingLot parkingLot = optionalParkingLot.get();

        // Check if any Spots are available;
        List<Spot> availableSpots = spotRepository3.findByParkingLotAndOccupiedFalse(parkingLot);

        for (Spot spot : availableSpots){
            if(spot.getSpotType() == SpotType.TWO_WHEELER && numberOfWheels < 2){
                availableSpots.remove(spot);
            }else if(spot.getSpotType() == SpotType.FOUR_WHEELER && numberOfWheels < 4){
                availableSpots.remove(spot);
            } else if (spot.getSpotType() == SpotType.OTHERS && numberOfWheels <= 4) {
                availableSpots.remove(spot);
            }
        }

        if(availableSpots.isEmpty()){
            throw new Exception("Cannot make reservation");
        }

        int minCost = Integer.MAX_VALUE;
        Spot minCostSpot = null;
        for (Spot spot : availableSpots){
            if(spot.getPricePerHour() < minCost ){
                minCostSpot = spot;
                minCost = spot.getPricePerHour();
            }
        }

        minCostSpot.setOccupied(true);


        Reservation reservation = new Reservation();
        reservation.setNumberOfHours(timeInHours);
        reservation.setSpot(minCostSpot);
        reservation.setUser(user);

        minCostSpot.getReservations().add(reservation);
        user.getReservations().add(reservation);

        return reservationRepository3.save(reservation);
    }
}
