package com.parking.parkinglotapi.api;

import com.parking.parkinglotapi.dto.VehicleDto;
import com.parking.parkinglotapi.exceptions.BadRequestException;
import com.parking.parkinglotapi.service.VehicleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Vehicle", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"Vehicle"})
public class VehicleApiController implements VehicleApi {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleApiController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public ResponseEntity<VehicleDto> parkVehicle(VehicleDto vehicleDto) throws BadRequestException {
        VehicleDto res = vehicleService.parkVehicle(vehicleDto);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
