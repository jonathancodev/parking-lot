package com.parking.parkinglotapi.api;

import com.parking.parkinglotapi.dto.VehicleDto;
import com.parking.parkinglotapi.exceptions.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface VehicleApi {

    @Operation(summary = "Park vehicle", description = "park vehicle", tags = {"Vehicle"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request, please review the params"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, you need pass a token to use this service"),
            @ApiResponse(responseCode = "403", description = "Forbidden, you do not have permission to use this service"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error, contact the admin"),
            @ApiResponse(responseCode = "503", description = "Service unavailable, try again later")})
    @RequestMapping(value = "/vehicle/park", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<VehicleDto> parkVehicle(@RequestBody VehicleDto vehicleDto) throws BadRequestException;
}
