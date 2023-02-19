package com.parking.parkinglotapi.model;

import com.parking.parkinglotapi.enums.VehicleType;
import com.parking.parkinglotapi.exceptions.BadRequestException;
import com.parking.parkinglotapi.interfaces.IVehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle")
public class Vehicle implements IVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="parking_lot_id", nullable=false)
    private ParkingLot parkingLot;

    @Column(name = "type")
    @Enumerated
    private VehicleType vehicleType;

    @Column(name = "spot")
    @Enumerated
    private VehicleType spot;

    @Column(name = "parked")
    private Long parked;

    @Override
    public void park(ParkingLot parkingLot) throws BadRequestException {

    }

    @Override
    public void remove(ParkingLot parkingLot) {

    }
}
