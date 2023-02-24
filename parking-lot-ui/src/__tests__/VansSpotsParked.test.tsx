import React from 'react';
import {render} from '@testing-library/react';
import {vansSpotsParkedMock} from "../mocks/dashboardMock";
import ParkingLotCard from "../components/ParkingLotCard";

describe('Dashboard Vans Spots Parked', () => {

    it('Vans Spots Parked Card', async () => {
        const title = 'Vans Parked';
        const { getByText } = render(
            <ParkingLotCard title={title} parkingLot={vansSpotsParkedMock}/>
        );

        const cardHeader = await getByText(title);
        expect(cardHeader).toBeInTheDocument();

        const carSpots = await getByText("Cars:");
        expect(carSpots).toBeInTheDocument();

        const vanSpots = await getByText("Vans:");
        expect(vanSpots).toBeInTheDocument();

        const motorcycleSpots = await getByText("Motorcycles:");
        expect(motorcycleSpots).toBeInTheDocument();
    });
});
