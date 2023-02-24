import React from 'react';
import {render} from '@testing-library/react';
import {spotsRemainingMock} from "../mocks/dashboardMock";
import ParkingLotCard from "../components/ParkingLotCard";

describe('Dashboard Spots Remaining', () => {

    it('Spots Remaining Card', async () => {
        const title = 'Spots Remaining';
        const { getByText } = render(
            <ParkingLotCard title={title} parkingLot={spotsRemainingMock}/>
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
