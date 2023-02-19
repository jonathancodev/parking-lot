package com.parking.parkinglotapi.service;

import com.parking.parkinglotapi.dto.ParkingLotDto;
import com.parking.parkinglotapi.repositories.ParkingLotRepository;
import com.parking.parkinglotapi.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class DashboardService {

    private final MessageSource messageSource;
    private final ParkingLotRepository parkingLotRepository;

    @Autowired
    public DashboardService(ParkingLotRepository parkingLotRepository, MessageSource messageSource) {
        this.parkingLotRepository = parkingLotRepository;
        this.messageSource = messageSource;
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }

    @Transactional(readOnly = true)
    public ParkingLotDto getRemainingSpots() {
        return new ParkingLotDto();
    }
}
