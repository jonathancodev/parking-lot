package com.parking.parkinglotapi.mapper;

import com.parking.parkinglotapi.dto.ParkingLotDto;
import com.parking.parkinglotapi.model.ParkingLot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParkingLotMapper extends EntityMapper<ParkingLotDto, ParkingLot>{

}