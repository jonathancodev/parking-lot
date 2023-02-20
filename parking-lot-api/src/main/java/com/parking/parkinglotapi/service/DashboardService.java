package com.parking.parkinglotapi.service;

import com.parking.parkinglotapi.dto.ParkingLotDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class DashboardService {

    private final MessageSource messageSource;

    @Autowired
    public DashboardService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }

    public ParkingLotDto getRemainingSpots() {
        return new ParkingLotDto();
    }
}
