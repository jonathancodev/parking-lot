package com.parking.parkinglotapi.dto;

import com.parking.parkinglotapi.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {

    private Long id;

    private ParkingLotDto parkingLotDto;

    private VehicleType vehicleType;

    private VehicleType spot;

    private Long parked;
}