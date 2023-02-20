package com.parking.parkinglotapi.model;

import com.parking.parkinglotapi.enums.VehicleType;
import org.springframework.stereotype.Component;

@Component
public class Van extends Vehicle {

    @Override
    public ParkingSlot park(ParkingLot parkingLot) {
        ParkingSlot parkingSlot = parkingLot.getSlotAvailable(VehicleType.VAN);

        if (parkingSlot == null) {
            parkingSlot = parkingLot.getSlots()
                    .stream()
                    .filter(ps -> ps.getSpotType() == VehicleType.CAR && ps.getVehicle() == null &&
                            (ps.getNumber() > 1 && parkingLot.getSlots().get(ps.getNumber() - 2).getVehicle() == null) &&
                            (ps.getNumber() < parkingLot.getSlots().size() && parkingLot.getSlots().get(ps.getNumber()).getVehicle() == null))
                    .findFirst()
                    .orElse(null);
        }

        return parkingSlot;
    }
}
