import React, {FC, useState} from 'react';
import {Form, FormikProvider, useFormik} from "formik";
import * as Yup from 'yup';
import {Button, Card, CardContent, CardHeader, Grid, MenuItem, TextField} from "@mui/material";
import VehicleTypeEnum from "../enums/VehicleTypeEnum";
import VehicleFormProps from "../interfaces/VehicleFormProps";
import {toast} from "react-toastify";
import Vehicle from "../interfaces/Vehicle";
import Constants from "../interfaces/Constants";

const StringIsNotNumber = (value: string) => isNaN(Number(value));

const VehicleForm: FC<VehicleFormProps> = (vehicleFormProps) => {

    const [loading, setLoading] = useState(false);

    const RegisterSchema = Yup.object().shape({
        id: Yup.number()
            .integer()
            .positive()
            .required('ID is a required field'),

        vehicleType: Yup.string()
            .required('Vehicle Type is a required field')
    });

    const formik = useFormik({
        initialValues: {
            id: 1,
            vehicleType: ''
        },
        validationSchema: RegisterSchema,
        onSubmit: async (values) => {
            setLoading(true);

            const body: Vehicle = {
                id: values.id,
                vehicleType: values.vehicleType
            };

            const options = {
                method: 'POST',
                headers: Constants.HEADERS,
                body: JSON.stringify(body)
            };

            try {
                const response = await fetch(`${Constants.API_URL}/vehicle/park`, options);

                if (response.status === 200) {
                    vehicleFormProps.onAdd();
                } else if (response.status === 400) {
                    const msg = await response.text();
                    toast.error(msg);
                } else {
                    toast.error('Something went wrong when request park vehicle');
                }

            } catch (e) {
                toast.error('Something went wrong when request park vehicle');
            }

            setLoading(false);
        }
    });

    const {errors, touched, getFieldProps} = formik;

    return (
        <>
            <FormikProvider value={formik}>
                <Form autoComplete='off' noValidate>
                    <Card sx={{minWidth: 275}}>
                        <CardHeader title={'Park vehicle'}
                                    sx={{marginLeft: '20px', marginRight: '20px'}}/>
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
                                        sx={{marginTop:'6px'}}
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
