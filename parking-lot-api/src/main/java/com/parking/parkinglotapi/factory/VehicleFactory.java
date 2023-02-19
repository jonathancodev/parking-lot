package com.parking.parkinglotapi.factory;

import com.parking.parkinglotapi.enums.VehicleType;
import com.parking.parkinglotapi.model.Car;
import com.parking.parkinglotapi.model.Motorcycle;
import com.parking.parkinglotapi.model.Van;
import com.parking.parkinglotapi.model.Vehicle;

public class VehicleFactory {

    public static Vehicle create(VehicleType vehicleType) {
        if (vehicleType == VehicleType.MOTORCYCLE)
            return new Motorcycle();
        else if (vehicleType == VehicleType.CAR)
            return new Car();
        else if (vehicleType == VehicleType.VAN)
            return new Van();

        return new Vehicle();
    }
}
