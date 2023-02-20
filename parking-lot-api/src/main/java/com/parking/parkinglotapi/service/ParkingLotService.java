package com.parking.parkinglotapi.service;

import com.parking.parkinglotapi.dto.VehicleDto;
import com.parking.parkinglotapi.enums.VehicleType;
import com.parking.parkinglotapi.exceptions.BadRequestException;
import com.parking.parkinglotapi.factory.VehicleFactory;
import com.parking.parkinglotapi.model.Motorcycle;
import com.parking.parkinglotapi.model.ParkingLot;
import com.parking.parkinglotapi.model.ParkingSlot;
import com.parking.parkinglotapi.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class VehicleService {

    private final Environment env;
    private final MessageSource messageSource;
    private ParkingLot parkingLot;

    @Autowired
    public VehicleService(Environment env, MessageSource messageSource, ParkingLot parkingLot) {
        this.env = env;
        this.messageSource = messageSource;
        this.parkingLot = parkingLot;
    }

    private void checkInitialValues() {

        if (parkingLot.getMotorcycleSpots() == null) {
            parkingLot.setMotorcycleSpots(Integer.parseInt(Objects.requireNonNull(env.getProperty("custom.spots.motorcycle"))));
            parkingLot.setCarSpots(Integer.parseInt(Objects.requireNonNull(env.getProperty("custom.spots.car"))));
            parkingLot.setCarSpots(Integer.parseInt(Objects.requireNonNull(env.getProperty("custom.spots.van"))));
            int total = parkingLot.getMotorcycleSpots() + parkingLot.getCarSpots() + parkingLot.getVanSpots();

            List<ParkingSlot> slots = new ArrayList<>(total);
            int spotNumber = 1;

            for (int i = spotNumber; i <= parkingLot.getMotorcycleSpots(); i++) {
                ParkingSlot parkingSlot = new ParkingSlot(spotNumber, VehicleType.MOTORCYCLE, null);
                slots.add(parkingSlot);
                spotNumber++;
            }

            for (int i = spotNumber; i <= parkingLot.getCarSpots(); i++) {
                ParkingSlot parkingSlot = new ParkingSlot(spotNumber, VehicleType.CAR, null);
                slots.add(parkingSlot);
                spotNumber++;
            }

            for (int i = spotNumber; i <= parkingLot.getVanSpots(); i++) {
                ParkingSlot parkingSlot = new ParkingSlot(spotNumber, VehicleType.VAN, null);
                slots.add(parkingSlot);
                spotNumber++;
            }

            parkingLot.setSlots(slots);
        }
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }

    public VehicleDto parkVehicle(VehicleDto vehicleDto) throws BadRequestException {
        checkInitialValues();
        Vehicle vehicle = VehicleFactory.create(vehicleDto.getVehicleType());

        if (vehicle != null) {
            vehicle.setId(vehicleDto.getId());
            ParkingSlot parkingSlot = vehicle.park(parkingLot);

            if (parkingSlot == null) {
                throw new BadRequestException("Parking lot is full");
            }

            parkingSlot.setVehicle(vehicle);
            parkingLot.park(parkingSlot);

        }
    }
}
