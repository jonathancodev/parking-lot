import React from 'react';
import {render, fireEvent, within, act} from '@testing-library/react';
import VehicleForm from "../components/VehicleForm";
import userEvent from "@testing-library/user-event";

describe('VehicleForm', () => {
    it('renders the form with fields', async () => {
        const { getByLabelText } = render(
            <VehicleForm onAdd={() => {}}/>
        );

        const vehicleIdField = getByLabelText('ID');
        expect(vehicleIdField).toBeInTheDocument();

        const vehicleTypeField = getByLabelText('Vehicle Type');
        expect(vehicleTypeField).toBeInTheDocument();
    });

    it('Add vehicle', async () => {

        const { getByLabelText, findByLabelText, findByRole } = render(
            <VehicleForm onAdd={() => {}}/>
        );

        const idEl = await getByLabelText('ID');
        fireEvent.change(idEl, { target: { value: '1' } });

        const selectLabel = /Vehicle Type/i;
        const selectEl = await findByLabelText(selectLabel);
        expect(selectEl).toBeInTheDocument();
        userEvent.click(selectEl);

        const optionsPopupEl = await findByRole("listbox", {
            name: selectLabel
        });

        userEvent.click(within(optionsPopupEl).getByText(/car/i));
    });
});
