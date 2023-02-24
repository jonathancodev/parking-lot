export const spotsRemainingMock = {
    motorcycleSpots: 1,
    carSpots: 1,
    vanSpots: 2
};

export const vansSpotsParkedMock = {
    motorcycleSpots: 0,
    carSpots: 0,
    vanSpots: 0
};

export const parkingSlotsList = [
    {
        number: 1,
        spotType: "MOTORCYCLE",
        vehicle: {
            id: 3,
            vehicleType: "MOTORCYCLE"
        },
        parkingDate: new Date(1677209570058)
    },
    {
        number: 2,
        spotType: "CAR",
        vehicle: {
            id: 2,
            vehicleType: "VAN"
        },
        parkingDate: new Date(1677209559869)
    },
    {
        number: 3,
        spotType: "CAR",
        vehicle: {
            id: 2,
            vehicleType: "VAN"
        },
        parkingDate: new Date(1677209559869)
    },
    {
        number: 4,
        spotType: "CAR",
        vehicle: {
            id: 2,
            vehicleType: "VAN"
        },
        parkingDate: new Date(1677209559869)
    },
    {
        number: 5,
        spotType: "VAN",
        vehicle: {
            id: 1,
            vehicleType: "VAN"
        },
        parkingDate: new Date(1677209555070)
    }
];