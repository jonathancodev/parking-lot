import React, {useEffect, useRef, useState} from 'react';
import {Grid, Typography} from "@mui/material";
import ParkingLotCard from "../components/ParkingLotCard";
import {toast, ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import VehicleForm from "../components/VehicleForm";
import Constants from "../interfaces/Constants";
import VehicleTable from "../components/VehicleTable";

function Dashboard() {
    const [spotsRemaining, setSpotsRemaining] = useState({motorcycleSpots: 0, carSpots: 0, vanSpots: 0});
    const [vanParkedSpots, setVanParkedSpots] = useState({motorcycleSpots: 0, carSpots: 0, vanSpots: 0});
    const [parkingSlots, setParkingSlots] = useState([]);
    const [loading, setLoading] = useState(true);
    let fetched = useRef(false);

    useEffect( () => {
        if (!fetched.current) {
            fetched.current = true;
            fetchAll().catch(e => toast.error('Something went wrong with the request'));
        }
    }, []);

    const fetchAll = async () => {
        setLoading(true);
        await getSpotsRemaining();
        await getVanParkedSpots();
        await getParkingSlotsFilled();
        setLoading(false);
    }

    const getSpotsRemaining = async () => {
        const options = {
            method: 'GET',
            headers: Constants.HEADERS
        };

        try {
            const response = await fetch(`${Constants.API_URL}/dashboard/remaining-spots`, options);
            const body = await response.json();

            if (response.status === 200) {
                setSpotsRemaining(body);
            } else {
                toast.error('Something went wrong when request spots remaining');
            }

        } catch (e) {
            toast.error('Something went wrong when request spots remaining');
        }
    }

    const getVanParkedSpots = async () => {
        const options = {
            method: 'GET',
            headers: Constants.HEADERS
        };

        try {
            const response = await fetch(`${Constants.API_URL}/dashboard/van-parked-spots`, options);
            const body = await response.json();

            if (response.status === 200) {
                setVanParkedSpots(body);
            } else {
                toast.error('Something went wrong when request spots parked');
            }

        } catch (e) {
            toast.error('Something went wrong when request spots parked');
        }
    }

    const getParkingSlotsFilled = async () => {
        const options = {
            method: 'GET',
            headers: Constants.HEADERS
        };

        try {
            const response = await fetch(`${Constants.API_URL}/vehicle/parking-slots-filled`, options);
            const body = await response.json();

            if (response.status === 200) {
                setParkingSlots(body);
            } else {
                toast.error('Something went wrong when request parking slots filled');
            }

        } catch (e) {
            toast.error('Something went wrong when request parking slots filled');
        }
    }

    return (
        <>
            <Grid container spacing={1} sx={{marginBottom: '30px'}}>
                <Grid item xs={1}/>
                <Grid sx={{borderBottom: '1px solid', padding: '10px'}} xs={10} item>
                    <Typography variant="h3" component="div">
                        Parking Lot
                    </Typography>
                </Grid>
                <Grid item xs={1}/>
            </Grid>
            {!loading &&
                <Grid container spacing={2} sx={{marginBottom: '30px'}}>
                    <Grid item xs={3}/>
                    <Grid item xs={3}>
                        <ParkingLotCard title={"Spots Remaining"} parkingLot={spotsRemaining}/>
                    </Grid>
                    <Grid item xs={3}>
                        <ParkingLotCard title={"Vans parked"} parkingLot={vanParkedSpots}/>
                    </Grid>
                    <Grid item xs={3}/>
                </Grid>
            }
            <Grid container spacing={1} sx={{marginBottom: '30px'}}>
                <Grid item xs={3}/>
                <Grid item xs={6}>
                    <VehicleForm onAdd={fetchAll}/>
                </Grid>
                <Grid item xs={3}/>
            </Grid>
            {!loading &&
                <Grid container spacing={1} sx={{marginBottom: '30px'}}>
                    <Grid item xs={1}/>
                    <Grid xs={10} item>
                        <VehicleTable parkingSlots={parkingSlots} onRemove={fetchAll}/>
                    </Grid>
                    <Grid item xs={1}/>
                </Grid>
            }
            <ToastContainer/>
        </>
    );
}

export default Dashboard;
