package com.parking.parkinglotapi.mapper;

import com.parking.parkinglotapi.dto.VehicleDto;
import com.parking.parkinglotapi.model.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper extends EntityMapper<VehicleDto, Vehicle>{

}