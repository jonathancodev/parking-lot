package com.parking.parkinglotapi.dto;

import com.parking.parkinglotapi.enums.VehicleType;
import com.parking.parkinglotapi.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSlotDto {

    private Integer number;

    private VehicleType spotType;

    private VehicleDto vehicle;

    private Date parkingDate;
}
