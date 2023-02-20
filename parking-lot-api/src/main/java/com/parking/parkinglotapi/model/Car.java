package com.parking.parkinglotapi.model;

import com.parking.parkinglotapi.enums.VehicleType;
import org.springframework.stereotype.Component;

@Component
public class Car extends Vehicle {

    @Override
    public ParkingSlot park(ParkingLot parkingLot) {
        ParkingSlot parkingSlot = parkingLot.getSlotAvailable(VehicleType.CAR);

        if (parkingSlot  == null) {
            parkingSlot = parkingLot.getSlotAvailable(VehicleType.VAN);
        }

        return parkingSlot;
    }
}
