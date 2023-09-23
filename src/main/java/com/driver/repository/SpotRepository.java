package com.driver.repository;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Integer>{

    List<Spot> findByParkingLotAndOccupiedFalse(ParkingLot parkingLot);
}
