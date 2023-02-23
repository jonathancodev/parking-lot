import React, {useState} from 'react';
import {Form, FormikProvider, useFormik} from "formik";
import * as Yup from 'yup';
import {Button, Card, CardContent, CardHeader, Grid, MenuItem, TextField, Typography} from "@mui/material";
import VehicleTypeEnum from "../enums/VehicleTypeEnum";

const StringIsNotNumber = (value: string) => isNaN(Number(value));

function VehicleForm() {

    const [loading, setLoading] = useState(false);

    const RegisterSchema = Yup.object().shape({
        id: Yup.number()
            .integer()
            .positive()
            .required(),

        vehicleType: Yup.string()
            .required('Vehicle Type is a required field')
    });

    const formik = useFormik({
        initialValues: {
            id: 1,
            vehicleType: ''
        },
        validationSchema: RegisterSchema,
        onSubmit: (values) => {
            setLoading(true);
            setLoading(false);
        }
    });

    const {errors, touched, getFieldProps} = formik;

    return (
        <>
            <FormikProvider value={formik}>
                <Form autoComplete='off' noValidate>
                    <Card sx={{minWidth: 275, justifyContent: 'start', alignItems: 'start'}}>
                        <CardHeader title={'Park vehicle'}
                                    sx={{borderBottom: '1px solid', marginLeft: '20px', marginRight: '20px'}}/>
                        <CardContent>
                            <Grid container spacing={2} sx={{marginBottom: '30px'}}>
                                <Grid item xs={5}>
                                    <TextField
                                        fullWidth
                                        label='ID'
                                        {...getFieldProps('id')}
                                        error={Boolean(touched.id && errors.id)}
                                        helperText={touched.id && errors.id}
                                    />
                                </Grid>
                                <Grid item xs={5}>
                                    <TextField
                                        select
                                        fullWidth
                                        label='Vehicle Type'
                                        {...getFieldProps('vehicleType')}
                                        error={Boolean(touched.vehicleType && errors.vehicleType)}
                                        helperText={touched.vehicleType && errors.vehicleType}
                                    >
                                        {Object.keys(VehicleTypeEnum).filter(StringIsNotNumber).map(typeKey => {
                                            return (
                                                <MenuItem key={typeKey} value={typeKey}>{typeKey}</MenuItem>
                                            )
                                        })
                                        }
                                    </TextField>
                                </Grid>
                                <Grid item xs={1}>
                                    <Button
                                        sx={{marginTop:'6px', borderRadius: '40%'}}
                                        fullWidth
                                        disabled={loading}
                                        size='large'
                                        type='submit'
                                        variant='outlined'
                                    >
                                        Add
                                    </Button>
                                </Grid>
                                <Grid item xs={1}/>
                            </Grid>
                        </CardContent>
                    </Card>
                </Form>
            </FormikProvider>
        </>
    );
}

export default VehicleForm;
