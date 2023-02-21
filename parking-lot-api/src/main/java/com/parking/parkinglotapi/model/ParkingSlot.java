package com.parking.parkinglotapi.model;

import com.parking.parkinglotapi.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ParkingSlot {

    private Integer number;

    private VehicleType spotType;

    private Vehicle vehicle;

    private Date parkingDate;
}
