import Vehicle from "./Vehicle";

interface ParkingSlot {
    number: number,
    spotType: string,
    vehicle: Vehicle,
    parkingDate: Date
}

export default ParkingSlot;