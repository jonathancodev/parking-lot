import React from 'react';
import {render} from '@testing-library/react';
import {parkingSlotsList} from "../mocks/dashboardMock";
import VehicleTable from "../components/VehicleTable";

describe('Dashboard Vehicles Table', () => {

    it('Vehicles Table', async () => {
        const { getByText, getAllByText } = render(
            <VehicleTable onRemove={() => {}} parkingSlots={parkingSlotsList}/>
        );

        let text = await getByText('Vehicle ID');
        expect(text).toBeInTheDocument();

        text = await getByText("Vehicle Type");
        expect(text).toBeInTheDocument();

        text = await getByText("Spot");
        expect(text).toBeInTheDocument();

        text = await getByText("Parked");
        expect(text).toBeInTheDocument();

        let allText = await getAllByText("MOTORCYCLE");
        expect(allText[0]).toBeInTheDocument();

        allText = await getAllByText("CAR");
        expect(allText[0]).toBeInTheDocument();

        allText = await getAllByText("VAN");
        expect(allText[0]).toBeInTheDocument();

        allText = await getAllByText("Remove");
        expect(allText[0]).toBeInTheDocument();
    });
});
