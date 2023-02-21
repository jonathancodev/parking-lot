package com.parking.parkinglotapi.model;

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

        if (vehicleType == VehicleType.MOTORCYCLE) {
            return slots
                    .stream()
                    .filter(ps -> ps.getVehicle() == null)
                    .findFirst()
                    .orElse(null);
        } else if (vehicleType == VehicleType.CAR) {
            for (int i = motorcycleSpots; i < slots.size(); i++) {
                ParkingSlot parkingSlot = slots.get(i);
                if (parkingSlot.getVehicle() == null) {
                    return parkingSlot;
                }
            }
        } else if (vehicleType == VehicleType.VAN) {
            for (int i = (motorcycleSpots + carSpots); i < slots.size(); i++) {
                ParkingSlot parkingSlot = slots.get(i);
                if (parkingSlot.getVehicle() == null) {
                    return parkingSlot;
                }
            }

            for (int i = motorcycleSpots; i < (motorcycleSpots + carSpots); i++) {
                ParkingSlot parkingSlot = slots.get(i);
                if (parkingSlot.getVehicle() == null &&
                        (parkingSlot.getNumber() > 1 && slots.get(parkingSlot.getNumber() - 2).getSpotType() == VehicleType.CAR && slots.get(parkingSlot.getNumber() - 2).getVehicle() == null) &&
                        (parkingSlot.getNumber() < slots.size() && slots.get(parkingSlot.getNumber()).getSpotType() == VehicleType.CAR && slots.get(parkingSlot.getNumber()).getVehicle() == null)) {
                    return parkingSlot;
                }
            }
        }

        return null;
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
                parkingSlotCurrent.setParkingDate(null);
                ParkingSlot parkingSlotNext = getSlots().get(parkingSlot.getNumber() + 1);
                parkingSlotNext.setVehicle(null);
                parkingSlotNext.setParkingDate(null);
                slots.set(parkingSlotCurrent.getNumber() - 1, parkingSlotCurrent);
                slots.set(parkingSlotNext.getNumber() - 1, parkingSlotNext);
            }
            parkingSlot.setVehicle(null);
            parkingSlot.setParkingDate(null);
            slots.set(parkingSlot.getNumber() - 1, parkingSlot);
        }
    }

    public List<ParkingSlot> slotsFilled() {
        return slots
                .stream()
                .filter(ps -> ps.getVehicle() != null)
                .collect(Collectors.toList());
    }

    public Long countingSpots(VehicleType vehicleType, boolean parked) {

        long count = 0;

        if (vehicleType == VehicleType.MOTORCYCLE) {
            for (int i = 0; i < motorcycleSpots; i++) {
                count += countingSpots(i, parked);
            }
        } else if (vehicleType == VehicleType.CAR) {
            for (int i = motorcycleSpots; i < (motorcycleSpots + carSpots); i++) {
                count += countingSpots(i, parked);
            }
        } else if (vehicleType == VehicleType.VAN) {
            for (int i = (motorcycleSpots + carSpots); i < slots.size(); i++) {
                count += countingSpots(i, parked);
            }
        }

        return count;
    }

    public Long countingSpots(int index, boolean parked) {
        ParkingSlot parkingSlot = slots.get(index);
        if (parked) {
            if (parkingSlot.getVehicle() != null) {
                return 1L;
            }
        } else {
            if (parkingSlot.getVehicle() == null) {
                return 1L;
            }
        }

        return 0L;
    }

    public boolean isParkedLotFull() {
        long count = slots.stream()
                .filter(ps -> ps.getVehicle() != null)
                .count();

        return count == (motorcycleSpots + carSpots + vanSpots);
    }
}
