package com.parking.parkinglotapi.model;

import com.parking.parkinglotapi.enums.VehicleType;
import com.parking.parkinglotapi.exceptions.BadRequestException;

public class Van extends Vehicle {

    @Override
    public void park(ParkingLot parkingLot) throws BadRequestException {
        if (parkingLot.getVanSpots() > 0) {
            setSpot(VehicleType.VAN);
            setParked(parkingLot.getCurrentSpot());
            parkingLot.setVanSpots(parkingLot.getVanSpots() - 1L);
            parkingLot.setCurrentSpot(parkingLot.getCurrentSpot() + 1L);
        } else if (parkingLot.getCarSpots() >= 3) {
            setSpot(VehicleType.CAR);
            setParked(parkingLot.getCurrentSpot());
            parkingLot.setCarSpots(parkingLot.getCarSpots() - 3L);
            parkingLot.setCurrentSpot(parkingLot.getCurrentSpot() + 3L);
        } else {
            throw new BadRequestException("Parking lot is full");
        }
    }

    @Override
    public void remove(ParkingLot parkingLot) {
        if (getSpot() == VehicleType.VAN) {
            parkingLot.setVanSpots(parkingLot.getVanSpots() + 1L);
        } else if (getSpot() == VehicleType.CAR) {
            parkingLot.setCarSpots(parkingLot.getCarSpots() + 3L);
        }

        parkingLot.setCurrentSpot(getParked());
    }
}
