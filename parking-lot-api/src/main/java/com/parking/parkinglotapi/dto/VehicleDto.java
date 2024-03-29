package com.parking.parkinglotapi.dto;

import com.parking.parkinglotapi.enums.VehicleType;
import com.parking.parkinglotapi.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {

    public VehicleDto(Vehicle vehicle) {
        this.id = vehicle.getId();
        this.vehicleType = vehicle.getVehicleType();
    }

    private Long id;

    private VehicleType vehicleType;
}
