package com.parking.parkinglotapi.model;

import com.parking.parkinglotapi.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Component
public abstract class Vehicle {

    private Long id;

    private VehicleType vehicleType;
}
