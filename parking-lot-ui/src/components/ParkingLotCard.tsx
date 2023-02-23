import React, {FC} from 'react';
import {Card, CardContent, CardHeader, Typography} from "@mui/material";
import ParkingLotCardProps from "../interfaces/ParkingLotCardProps";

const ParkingLotCard: FC<ParkingLotCardProps> = (parkingLotCardProps) => {

    return (
        <Card sx={{ minWidth: 275, justifyContent:'start', alignItems: 'start' }}>
            <CardHeader title={parkingLotCardProps.title} sx={{borderBottom: '1px solid', marginLeft: '20px', marginRight: '20px'}}/>
            <CardContent>
                <Typography component="div">
                   Cars: {parkingLotCardProps.parkingLot.carSpots}
                </Typography>
                <Typography component="div">
                    Vans: {parkingLotCardProps.parkingLot.vanSpots}
                </Typography>
                <Typography component="div">
                    Motorcycles: {parkingLotCardProps.parkingLot.motorcycleSpots}
                </Typography>
            </CardContent>
        </Card>
    );
}

export default ParkingLotCard;
