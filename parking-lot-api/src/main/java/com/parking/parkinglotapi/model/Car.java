package com.parking.parkinglotapi.model;

import com.parking.parkinglotapi.enums.VehicleType;
import com.parking.parkinglotapi.exceptions.BadRequestException;

public class Car extends Vehicle {

    @Override
    public void park(ParkingLot parkingLot) throws BadRequestException {
        if (parkingLot.getCarSpots() > 0) {
            setSpot(VehicleType.CAR);
            setParked(parkingLot.getCurrentSpot());
            parkingLot.setCarSpots(parkingLot.getCarSpots() - 1L);
            parkingLot.setCurrentSpot(parkingLot.getCurrentSpot() + 1L);
        } else if (parkingLot.getVanSpots() > 0) {
            setSpot(VehicleType.VAN);
            setParked(parkingLot.getCurrentSpot());
            parkingLot.setVanSpots(parkingLot.getVanSpots() - 1L);
            parkingLot.setCurrentSpot(parkingLot.getCurrentSpot() + 1L);
        } else {
            throw new BadRequestException("Parking lot is full");
        }
    }

    @Override
    public void remove(ParkingLot parkingLot) {
        if (getSpot() == VehicleType.CAR) {
            parkingLot.setCarSpots(parkingLot.getCarSpots() + 1L);
        } else if (getSpot() == VehicleType.VAN) {
            parkingLot.setVanSpots(parkingLot.getVanSpots() + 1L);
        }

        parkingLot.setCurrentSpot(getParked());
    }
}
