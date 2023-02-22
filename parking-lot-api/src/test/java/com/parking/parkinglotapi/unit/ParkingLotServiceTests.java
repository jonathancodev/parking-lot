package com.parking.parkinglotapi.unit;

import com.parking.parkinglotapi.dto.ParkingLotDto;
import com.parking.parkinglotapi.dto.VehicleDto;
import com.parking.parkinglotapi.enums.VehicleType;
import com.parking.parkinglotapi.exceptions.BadRequestException;
import com.parking.parkinglotapi.service.ParkingLotService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ParkingLotServiceTests {

    private final MessageSource messageSource;
    private final ParkingLotService parkingLotService;

    @Autowired
    public ParkingLotServiceTests(MessageSource messageSource, ParkingLotService parkingLotService) {
        this.messageSource = messageSource;
        this.parkingLotService = parkingLotService;
    }

    @AfterEach
    void clearAll() {
        for (long i=1; i<=5; i++) {
            try {
                parkingLotService.removeVehicle(i);
            } catch (Exception ignored) {

            }
        }
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }

    @Test
    public void whenParkNullVehicle() {

        Exception exception = assertThrows(BadRequestException.class, () -> {
            parkingLotService.parkVehicle(null);
        });

        Assertions.assertEquals(exception.getMessage(), getMessage("parking.lot.vehicle.required"));
    }

    @Test
    public void whenParkVehicleWithoutId() {

        Exception exception = assertThrows(BadRequestException.class, () -> {
            parkingLotService.parkVehicle(new VehicleDto());
        });

        Assertions.assertEquals(exception.getMessage(), getMessage("parking.lot.vehicle.id.required"));
    }

    @Test
    public void whenParkVehicleWithoutType() {

        Exception exception = assertThrows(BadRequestException.class, () -> {
            VehicleDto vehicleDto = new VehicleDto();
            vehicleDto.setId(1L);
            parkingLotService.parkVehicle(vehicleDto);
        });

        Assertions.assertEquals(exception.getMessage(), getMessage("parking.lot.vehicle.type.required"));
    }

    @Test
    public void whenParkVehicle() throws Exception {
        VehicleDto vehicleDto = new VehicleDto(1L, VehicleType.MOTORCYCLE);
        parkingLotService.parkVehicle(vehicleDto);
    }

    @Test
    public void whenParkVehicleDuplicate() throws Exception {
        Exception exception = assertThrows(BadRequestException.class, () -> {
            VehicleDto vehicleDto = new VehicleDto(1L, VehicleType.MOTORCYCLE);
            parkingLotService.parkVehicle(vehicleDto);
            parkingLotService.parkVehicle(vehicleDto);
        });

        Assertions.assertEquals(exception.getMessage(), getMessage("parking.lot.vehicle.unique"));
    }

    @Test
    public void whenRemoveVehicleWithoutId() {
        Exception exception = assertThrows(BadRequestException.class, () -> {
            parkingLotService.removeVehicle(null);
        });

        Assertions.assertEquals(exception.getMessage(), getMessage("parking.lot.vehicle.id.required"));
    }

    @Test
    public void whenRemoveVehicleWithNoPresence() {
        Exception exception = assertThrows(BadRequestException.class, () -> {
            parkingLotService.removeVehicle(0L);
        });

        Assertions.assertEquals(exception.getMessage(), getMessage("parking.lot.vehicle.presence"));
    }

    @Test
    public void whenRemoveVehicle() throws BadRequestException {
        VehicleDto vehicleDto = new VehicleDto(1L, VehicleType.MOTORCYCLE);
        parkingLotService.parkVehicle(vehicleDto);
        parkingLotService.removeVehicle(1L);
    }

    @Test
    public void whenParkingSpotIsNotFullSample1() {
        Assertions.assertFalse(parkingLotService.isParkedLotFull());
    }

    @Test
    public void whenParkingSpotIsNotFullSample2() throws Exception {
        VehicleDto vehicleDto = new VehicleDto(1L, VehicleType.MOTORCYCLE);
        parkingLotService.parkVehicle(vehicleDto);
        Assertions.assertFalse(parkingLotService.isParkedLotFull());
    }

    @Test
    public void parkingSpotRemaining() throws Exception {
        VehicleDto vehicleDto = new VehicleDto(1L, VehicleType.MOTORCYCLE);
        parkingLotService.parkVehicle(vehicleDto);
        Assertions.assertEquals(new ParkingLotDto(0L,3L,1L), parkingLotService.getRemainingSpots());
    }

    @Test
    public void parkingSpotParked() throws Exception {
        VehicleDto vehicleDto = new VehicleDto(1L, VehicleType.MOTORCYCLE);
        parkingLotService.parkVehicle(vehicleDto);
        Assertions.assertEquals(new ParkingLotDto(1L,0L,0L), parkingLotService.getParkedSpots());
    }

    @Test
    public void whenParkingSpotIsFull() throws Exception {

        for (long i=1; i<=5; i++) {
            VehicleDto vehicleDto = new VehicleDto(i, VehicleType.MOTORCYCLE);
            parkingLotService.parkVehicle(vehicleDto);
        }

        Assertions.assertTrue(parkingLotService.isParkedLotFull());
    }

    @Test
    public void whenParkingWhenIsFull() throws Exception {

        for (long i=1; i<=5; i++) {
            VehicleDto vehicleDto = new VehicleDto(i, VehicleType.MOTORCYCLE);
            parkingLotService.parkVehicle(vehicleDto);
        }

        Exception exception = assertThrows(BadRequestException.class, () -> {
            VehicleDto vehicleDto2 = new VehicleDto(6L, VehicleType.MOTORCYCLE);
            parkingLotService.parkVehicle(vehicleDto2);
        });

        Assertions.assertEquals(exception.getMessage(), getMessage("parking.lot.full"));
    }

}
