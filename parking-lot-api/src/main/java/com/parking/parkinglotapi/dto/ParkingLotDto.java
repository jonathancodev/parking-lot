package com.parking.parkinglotapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotDto {

    private Long id;

    private Long motorcycleSpots;

    private Long carSpots;

    private Long vanSpots;

    private Long currentSpot;
}
