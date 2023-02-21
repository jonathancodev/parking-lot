package com.parking.parkinglotapi.service;

import com.parking.parkinglotapi.dto.ParkingLotDto;
import com.parking.parkinglotapi.dto.ParkingSlotDto;
import com.parking.parkinglotapi.dto.VehicleDto;
import com.parking.parkinglotapi.enums.VehicleType;
import com.parking.parkinglotapi.exceptions.BadRequestException;
import com.parking.parkinglotapi.factory.VehicleFactory;
import com.parking.parkinglotapi.model.ParkingLot;
import com.parking.parkinglotapi.model.ParkingSlot;
import com.parking.parkinglotapi.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ParkingLotService {

    private final Environment env;
    private final MessageSource messageSource;
    private final ParkingLot parkingLot;

    @Autowired
    public ParkingLotService(Environment env, MessageSource messageSource, ParkingLot parkingLot) {
        this.env = env;
        this.messageSource = messageSource;
        this.parkingLot = parkingLot;
        checkInitialValues();
    }

    private void checkInitialValues() {

        if (parkingLot.getMotorcycleSpots() == null) {
            parkingLot.setMotorcycleSpots(Integer.parseInt(Objects.requireNonNull(env.getProperty("custom.spots.motorcycle"))));
            parkingLot.setCarSpots(Integer.parseInt(Objects.requireNonNull(env.getProperty("custom.spots.car"))));
            parkingLot.setVanSpots(Integer.parseInt(Objects.requireNonNull(env.getProperty("custom.spots.van"))));
            int total = parkingLot.getMotorcycleSpots() + parkingLot.getCarSpots() + parkingLot.getVanSpots();

            List<ParkingSlot> slots = new ArrayList<>(total);
            int spotNumber = 1;

            for (int i = 0; i < parkingLot.getMotorcycleSpots(); i++) {
                ParkingSlot parkingSlot = new ParkingSlot(spotNumber, VehicleType.MOTORCYCLE, null, null);
                slots.add(parkingSlot);
                spotNumber++;
            }

            for (int i = 0; i < parkingLot.getCarSpots(); i++) {
                ParkingSlot parkingSlot = new ParkingSlot(spotNumber, VehicleType.CAR, null, null);
                slots.add(parkingSlot);
                spotNumber++;
            }

            for (int i = 0; i < parkingLot.getVanSpots(); i++) {
                ParkingSlot parkingSlot = new ParkingSlot(spotNumber, VehicleType.VAN, null, null);
                slots.add(parkingSlot);
                spotNumber++;
            }

            parkingLot.setSlots(slots);
        }
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }

    private void parkValidation(VehicleDto vehicleDto) throws BadRequestException {
        if (vehicleDto == null) {
            throw new BadRequestException(getMessage("parking.lot.vehicle.required"));
        } else if (vehicleDto.getId() == null) {
            throw new BadRequestException(getMessage("parking.lot.vehicle.id.required"));
        } else if (vehicleDto.getVehicleType() == null) {
            throw new BadRequestException(getMessage("parking.lot.vehicle.type.required"));
        } else if (parkingLot.getSlots().stream().filter(ps -> ps.getVehicle() != null && ps.getVehicle().getId().equals(vehicleDto.getId())).findFirst().orElse(null) != null) {
            throw new BadRequestException(getMessage("parking.lot.vehicle.unique"));
        }
    }

    private void removeValidation(Long id) throws BadRequestException {
        if (id == null) {
            throw new BadRequestException(getMessage("parking.lot.vehicle.id.required"));
        } else if (parkingLot.getSlots().stream().filter(ps -> ps.getVehicle() != null && ps.getVehicle().getId().equals(id)).findFirst().orElse(null) == null) {
            throw new BadRequestException(getMessage("parking.lot.vehicle.presence"));
        }
    }

    public void parkVehicle(VehicleDto vehicleDto) throws BadRequestException {
        parkValidation(vehicleDto);
        Vehicle vehicle = VehicleFactory.create(vehicleDto.getVehicleType());

        if (vehicle != null) {
            vehicle.setId(vehicleDto.getId());
            vehicle.setVehicleType(vehicleDto.getVehicleType());
            ParkingSlot parkingSlot = parkingLot.getSlotAvailable(vehicle.getVehicleType());

            if (parkingSlot == null) {
                throw new BadRequestException(getMessage("parking.lot.full"));
            }

            parkingSlot.setVehicle(vehicle);
            parkingSlot.setParkingDate(new Date());
            parkingLot.park(parkingSlot);
        }
    }

    public void removeVehicle(Long id) throws BadRequestException {
        removeValidation(id);
        parkingLot.remove(id);
    }

    public ParkingLotDto getRemainingSpots() {
        ParkingLotDto parkingLotDto = new ParkingLotDto();
        parkingLotDto.setMotorcycleSpots(parkingLot.countingSpots(VehicleType.MOTORCYCLE, false));
        parkingLotDto.setCarSpots(parkingLot.countingSpots(VehicleType.CAR, false));
        parkingLotDto.setVanSpots(parkingLot.countingSpots(VehicleType.VAN, false));

        return parkingLotDto;
    }

    public ParkingLotDto getParkedSpots() {
        ParkingLotDto parkingLotDto = new ParkingLotDto();
        parkingLotDto.setMotorcycleSpots(parkingLot.countingSpots(VehicleType.MOTORCYCLE, true));
        parkingLotDto.setCarSpots(parkingLot.countingSpots(VehicleType.CAR, true));
        parkingLotDto.setVanSpots(parkingLot.countingSpots(VehicleType.VAN, true));

        return parkingLotDto;
    }

    public boolean isParkedLotFull() {
        return parkingLot.isParkedLotFull();
    }

    public List<ParkingSlotDto> getParkingSlotsFilled() {
        return parkingLot.slotsFilled().stream()
                .map(ps -> new ParkingSlotDto(ps.getNumber(), ps.getSpotType(), new VehicleDto(ps.getVehicle()), ps.getParkingDate()))
                .sorted(Comparator.comparing(ParkingSlotDto::getParkingDate).reversed())
                .collect(Collectors.toList());
    }
}
