package com.parking.parkinglotapi.service;

import com.parking.parkinglotapi.dto.VehicleDto;
import com.parking.parkinglotapi.exceptions.BadRequestException;
import com.parking.parkinglotapi.factory.VehicleFactory;
import com.parking.parkinglotapi.interfaces.IVehicle;
import com.parking.parkinglotapi.mapper.VehicleMapper;
import com.parking.parkinglotapi.model.ParkingLot;
import com.parking.parkinglotapi.model.Vehicle;
import com.parking.parkinglotapi.repositories.ParkingLotRepository;
import com.parking.parkinglotapi.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class VehicleService {

    private final MessageSource messageSource;
    private final ParkingLotRepository parkingLotRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Autowired
    public VehicleService(MessageSource messageSource, ParkingLotRepository parkingLotRepository, VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.parkingLotRepository = parkingLotRepository;
        this.vehicleRepository = vehicleRepository;
        this.messageSource = messageSource;
        this.vehicleMapper = vehicleMapper;
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }

    @Transactional
    public VehicleDto parkVehicle(VehicleDto vehicleDto) throws BadRequestException {
        ParkingLot parkingLot = parkingLotRepository.findById(1L).orElseThrow();
        Vehicle vehicle = VehicleFactory.create(vehicleDto.getVehicleType());
        vehicle.setId(vehicleDto.getId());
        vehicle.park(parkingLot);
        parkingLotRepository.save(parkingLot);
        vehicleRepository.save(vehicle);

        return vehicleMapper.toDto(vehicle);
    }
}
