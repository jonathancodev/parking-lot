import React, {FC, useState} from 'react';
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    IconButton,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow
} from "@mui/material";
import VehicleTableProps from "../interfaces/VehicleTableProps";
import Constants from "../interfaces/Constants";
import {toast} from "react-toastify";

const VehicleTable: FC<VehicleTableProps> = (vehicleTableProps) => {

    const [loading, setLoading] = useState(false);
    const [openRemoveRequest, setOpenRemoveRequest] = useState(false);
    const [vehicleId, setVehicleId] = useState(0);

    const remove = async (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        setLoading(true);
        const options = {
            method: 'DELETE',
            headers: Constants.HEADERS
        };

        try {
            const response = await fetch(`${Constants.API_URL}/vehicle/remove/${vehicleId}`, options);

            if (response.status === 200) {
                vehicleTableProps.onRemove();
            } else if (response.status === 400) {
                const msg = await response.text();
                toast.error(msg);
            } else {
                toast.error('Something went wrong when request remove vehicle');
            }

        } catch (e) {
            toast.error('Something went wrong when request remove vehicle');
        }
        setLoading(false);
        setOpenRemoveRequest(false);
    }

    return (
        <>
            <Dialog open={openRemoveRequest} onClose={() => setOpenRemoveRequest(false)} disableEnforceFocus>
                <DialogTitle>
                    Remove Vehicle
                    {openRemoveRequest ? (
                        <IconButton
                            aria-label="close"
                            onClick={() => setOpenRemoveRequest(false)}
                            sx={{
                                position: 'absolute',
                                right: 8,
                                top: 8,
                                color: (theme) => theme.palette.grey[500],
                            }}
                        >
                            X
                        </IconButton>
                    ) : null}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Do you really want to remove the vehicle?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button
                        style={{color: 'gray'}}
                        onClick={() => setOpenRemoveRequest(false)}>
                        No
                    </Button>
                    {vehicleId && <Button
                        size='medium'
                        variant='contained'
                        disabled={loading}
                        onClick={remove}
                    >
                        Yes
                    </Button>}
                </DialogActions>
            </Dialog>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell align="center">Vehicle ID</TableCell>
                            <TableCell align="center">Vehicle Type</TableCell>
                            <TableCell align="center">Spot</TableCell>
                            <TableCell align="center">Parked</TableCell>
                            <TableCell align="center"/>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {vehicleTableProps.parkingSlots.map((parkingSlot) => (
                            <TableRow
                                key={parkingSlot.number}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell align="center" component="th" scope="row">
                                    {parkingSlot.vehicle.id}
                                </TableCell>
                                <TableCell align="center">{parkingSlot.vehicle.vehicleType}</TableCell>
                                <TableCell align="center">{parkingSlot.spotType}</TableCell>
                                <TableCell align="center">{parkingSlot.number}</TableCell>
                                <TableCell align="center">
                                    <Button
                                        color={'error'}
                                        disabled={loading}
                                        size='large'
                                        type='submit'
                                        variant='outlined'
                                        onClick={() => {
                                            setVehicleId(parkingSlot.vehicle.id);
                                            setOpenRemoveRequest(true);
                                        }}
                                    >
                                        Remove
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </>
    );
}

export default VehicleTable;
