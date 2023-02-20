package com.parking.parkinglotapi.model;

import com.parking.parkinglotapi.dto.ParkingSlotDto;
import com.parking.parkinglotapi.dto.VehicleDto;
import com.parking.parkinglotapi.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ParkingLot {

    private Integer motorcycleSpots;

    private Integer carSpots;

    private Integer vanSpots;

    private List<ParkingSlot> slots;

    public ParkingSlot getSlotAvailable(VehicleType vehicleType) {
        return slots
                .stream()
                .filter(ps -> ps.getSpotType() == vehicleType && ps.getVehicle() == null)
                .findFirst()
                .orElse(null);
    }

    public void park(ParkingSlot parkingSlot) {
        slots.set(parkingSlot.getNumber() - 1, parkingSlot);

        if (parkingSlot.getSpotType() == VehicleType.CAR && parkingSlot.getVehicle().getVehicleType() == VehicleType.VAN) {
            ParkingSlot parkingSlotPrev = slots.get(parkingSlot.getNumber() - 2);
            parkingSlotPrev.setVehicle(parkingSlot.getVehicle());
            ParkingSlot parkingSlotNext = slots.get(parkingSlot.getNumber());
            parkingSlotNext.setVehicle(parkingSlot.getVehicle());
            slots.set(parkingSlotPrev.getNumber() - 1, parkingSlotPrev);
            slots.set(parkingSlotNext.getNumber() - 1, parkingSlotNext);
        }
    }

    public void remove(Long id) {
        ParkingSlot parkingSlot = slots.stream().filter(ps -> ps.getVehicle() != null && ps.getVehicle().getId().equals(id)).findFirst().orElse(null);

        if (parkingSlot != null) {
            if (parkingSlot.getSpotType() == VehicleType.CAR && parkingSlot.getVehicle().getVehicleType() == VehicleType.VAN) {
                ParkingSlot parkingSlotCurrent = getSlots().get(parkingSlot.getNumber());
                parkingSlotCurrent.setVehicle(null);
                ParkingSlot parkingSlotNext = getSlots().get(parkingSlot.getNumber() + 1);
                parkingSlotNext.setVehicle(null);
                slots.set(parkingSlotCurrent.getNumber() - 1, parkingSlotCurrent);
                slots.set(parkingSlotNext.getNumber() - 1, parkingSlotNext);
            }
            parkingSlot.setVehicle(null);
            slots.set(parkingSlot.getNumber() - 1, parkingSlot);
        }
    }

    public List<ParkingSlot> slotsFilled() {
        return slots
                .stream()
                .filter(ps -> ps.getVehicle() != null)
                .collect(Collectors.toList());
    }

    public Long remainingSpots(VehicleType vehicleType) {
        return slots
                .stream()
                .filter(ps -> ps.getSpotType() == vehicleType && ps.getVehicle() == null)
                .count();
    }

    public Long parkedSpots(VehicleType vehicleType) {
        return slots
                .stream()
                .filter(ps -> ps.getSpotType() == vehicleType && ps.getVehicle() != null)
                .count();
    }
}
