package com.parking.parkinglotapi.api;

import com.parking.parkinglotapi.dto.ParkingLotDto;
import com.parking.parkinglotapi.service.DashboardService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Dashboard", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"Dashboard"})
public class DashboardApiController implements DashboardApi {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardApiController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Override
    public ResponseEntity<ParkingLotDto> getRemainingSpots() {
        ParkingLotDto parkingLotDto = dashboardService.getRemainingSpots();
        return new ResponseEntity<>(parkingLotDto, HttpStatus.OK);
    }
}
