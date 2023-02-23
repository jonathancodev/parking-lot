import React, {useEffect, useRef, useState} from 'react';
import {Card, CardContent, CardHeader, Grid, Typography} from "@mui/material";
import ParkingLotCard from "../components/ParkingLotCard";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import VehicleForm from "../components/VehicleForm";

const HEADERS = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
};

const API_URL = process.env.REACT_APP_API_URL

function Dashboard() {
    const [spotsRemaining, setSpotsRemaining] = useState({motorcycleSpots: 0, carSpots: 0, vanSpots: 0});
    const [spotsParked, setSpotsParked] = useState({motorcycleSpots: 0, carSpots: 0, vanSpots: 0});
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
        await getSpotsParked();
        setLoading(false);
    }

    const getSpotsRemaining = async () => {
        const options = {
            method: 'GET',
            headers: HEADERS
        };

        try {
            const response = await fetch(`${API_URL}/dashboard/remaining-spots`, options);
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

    const getSpotsParked= async () => {
        const options = {
            method: 'GET',
            headers: HEADERS
        };

        try {
            const response = await fetch(`${API_URL}/dashboard/parked-spots`, options);
            const body = await response.json();

            if (response.status === 200) {
                setSpotsParked(body);
            } else {
                toast.error('Something went wrong when request spots parked');
            }

        } catch (e) {
            toast.error('Something went wrong when request spots parked');
        }
    }

    return (
        <>
            <Grid container spacing={1} sx={{marginBottom: '30px'}}>
                <Grid xs={1}/>
                <Grid sx={{borderBottom: '1px solid', padding: '10px'}} xs={10}>
                    <Typography variant="h3" component="div">
                        Parking Lot
                    </Typography>
                </Grid>
                <Grid xs={1}/>
            </Grid>
            {!loading &&
                <Grid container spacing={2} sx={{marginBottom: '30px'}}>
                    <Grid item xs={3}/>
                    <Grid item xs={3}>
                        <ParkingLotCard title={"Spots Remaining"} parkingLot={spotsRemaining}/>
                    </Grid>
                    <Grid item xs={3}>
                        <ParkingLotCard title={"Vans parked"} parkingLot={spotsParked}/>
                    </Grid>
                    <Grid item xs={3}/>
                </Grid>
            }
            <Grid container spacing={1} sx={{marginBottom: '30px'}}>
                <Grid item xs={3}/>
                <Grid item xs={6}>
                    <VehicleForm/>
                </Grid>
                <Grid item xs={3}/>
            </Grid>
            <ToastContainer/>
        </>
    );
}

export default Dashboard;
