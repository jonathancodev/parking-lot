package com.parking.parkinglotapi.api;

import com.parking.parkinglotapi.dto.ParkingLotDto;
import com.parking.parkinglotapi.dto.ParkingSlotDto;
import com.parking.parkinglotapi.dto.VehicleDto;
import com.parking.parkinglotapi.exceptions.BadRequestException;
import com.parking.parkinglotapi.service.ParkingLotService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@Api(value = "Parking Lot Vehicle", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"Parking Lot Vehicle", "Parking Lot Dashboard"})
public class ParkingLotApiController implements ParkingLotApi {

    private final ParkingLotService parkingLotService;

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> handleException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Autowired
    public ParkingLotApiController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @Override
    public ResponseEntity<Void> parkVehicle(VehicleDto vehicleDto) throws BadRequestException {
        parkingLotService.parkVehicle(vehicleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> removeVehicle(Long id) throws BadRequestException {
        parkingLotService.removeVehicle(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ParkingSlotDto>> getParkingSlotsFilled() {
        List<ParkingSlotDto> parkingSlotDtoList = parkingLotService.getParkingSlotsFilled();
        return new ResponseEntity<>(parkingSlotDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ParkingLotDto> getRemainingSpots() {
        ParkingLotDto parkingLotDto = parkingLotService.getRemainingSpots();
        return new ResponseEntity<>(parkingLotDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ParkingLotDto> getParkedSpots() {
        ParkingLotDto parkingLotDto = parkingLotService.getParkedSpots();
        return new ResponseEntity<>(parkingLotDto, HttpStatus.OK);
    }
}
