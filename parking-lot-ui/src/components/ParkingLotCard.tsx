import React, {FC} from 'react';
import {Card, CardContent, CardHeader, Grid, Typography} from "@mui/material";
import ParkingLotCardProps from "../interfaces/ParkingLotCardProps";

const ParkingLotCard: FC<ParkingLotCardProps> = (parkingLotCardProps) => {

    return (
        <Card sx={{ minWidth: 275 }}>
            <CardHeader title={parkingLotCardProps.title} sx={{borderBottom: '1px solid', marginLeft: '20px', marginRight: '20px'}}/>
            <CardContent>
                <Grid container sx={{justifyContent: 'space-between'}}>
                    <Typography component="div">
                       Cars:
                    </Typography>
                    <Typography component="div">
                        {parkingLotCardProps.parkingLot.carSpots}
                    </Typography>
                </Grid>
                <Grid container sx={{justifyContent: 'space-between'}}>
                    <Typography component="div">
                        Vans:
                    </Typography>
                    <Typography component="div">
                        {parkingLotCardProps.parkingLot.vanSpots}
                    </Typography>
                </Grid>
                <Grid container sx={{justifyContent: 'space-between'}}>
                    <Typography component="div">
                        Motorcycles:
                    </Typography>
                    <Typography component="div">
                        {parkingLotCardProps.parkingLot.motorcycleSpots}
                    </Typography>
                </Grid>
            </CardContent>
        </Card>
    );
}

export default ParkingLotCard;
