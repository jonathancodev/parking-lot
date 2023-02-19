package com.parking.parkinglotapi.interfaces;

import com.parking.parkinglotapi.exceptions.BadRequestException;
import com.parking.parkinglotapi.model.ParkingLot;

public interface IVehicle {
    void park(ParkingLot parkingLot) throws BadRequestException;
    void remove(ParkingLot parkingLot);
}
