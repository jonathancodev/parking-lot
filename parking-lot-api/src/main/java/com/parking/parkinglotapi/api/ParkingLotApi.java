package com.parking.parkinglotapi.api;

import com.parking.parkinglotapi.dto.ParkingLotDto;
import com.parking.parkinglotapi.dto.ParkingSlotDto;
import com.parking.parkinglotapi.dto.VehicleDto;
import com.parking.parkinglotapi.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface ParkingLotApi {

    @Operation(summary = "Park vehicle", description = "park vehicle", tags = {"Parking Lot Vehicle"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request, please review the params"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you need pass a token to use this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden, you do not have permission to use this service"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error, contact the admin"),
            @ApiResponse(responseCode = "503", description = "Service unavailable, try again later")})
    @RequestMapping(value = "/vehicle/park", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<Void> parkVehicle(@RequestBody VehicleDto vehicleDto) throws BadRequestException;

    @Operation(summary = "Remove vehicle", description = "remove vehicle", tags = {"Parking Lot Vehicle"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request, please review the params"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you need pass a token to use this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden, you do not have permission to use this service"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error, contact the admin"),
            @ApiResponse(responseCode = "503", description = "Service unavailable, try again later")})
    @RequestMapping(value = "/vehicle/remove/{id}", produces = {"application/json"}, method = RequestMethod.DELETE)
    ResponseEntity<Void> removeVehicle(@PathVariable Long id) throws BadRequestException;

    @Operation(summary = "Get parking slots filled", description = "get parking slots filled", tags = {"Parking Lot Vehicle"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ParkingSlotDto.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request, please review the params"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you need pass a token to use this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden, you do not have permission to use this service"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error, contact the admin"),
            @ApiResponse(responseCode = "503", description = "Service unavailable, try again later")})
    @RequestMapping(value = "/vehicle/parking-slots-filled", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<List<ParkingSlotDto>> getParkingSlotsFilled();

    @Operation(summary = "Get remaining spots", description = "get remaining spots", tags = {"Parking Lot Dashboard"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingLotDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, please review the params"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you need pass a token to use this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden, you do not have permission to use this service"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error, contact the admin"),
            @ApiResponse(responseCode = "503", description = "Service unavailable, try again later")})
    @RequestMapping(value = "/dashboard/remaining-spots", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<ParkingLotDto> getRemainingSpots();

    @Operation(summary = "Get parked spots", description = "get parked spots", tags = {"Parking Lot Dashboard"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParkingLotDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, please review the params"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you need pass a token to use this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden, you do not have permission to use this service"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error, contact the admin"),
            @ApiResponse(responseCode = "503", description = "Service unavailable, try again later")})
    @RequestMapping(value = "/dashboard/parked-spots", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<ParkingLotDto> getParkedSpots();
}
