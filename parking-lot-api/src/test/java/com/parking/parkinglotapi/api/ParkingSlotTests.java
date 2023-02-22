package com.parking.parkinglotapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.parking.parkinglotapi.dto.ParkingSlotDto;
import com.parking.parkinglotapi.dto.VehicleDto;
import com.parking.parkinglotapi.enums.VehicleType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ParkingSlotTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public ParkingSlotTests(MockMvc mockMvc, ObjectMapper objectMapper) throws Exception {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @AfterEach
    void clearAll() throws Exception {
        for (int i=1; i<=5; i++) {
            this.mockMvc.perform(delete("/vehicle/remove/"+i)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON));
        }
    }

    @Test
    public void parkingSlotsFilled() throws Exception {

        VehicleDto vehicleDto = new VehicleDto(1L, VehicleType.MOTORCYCLE);

        this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        for (long i=2; i<=4; i++) {
            VehicleDto vehicleDto2 = new VehicleDto(i, VehicleType.CAR);

            this.mockMvc.perform(post("/vehicle/park")
                    .content(objectMapper.writeValueAsString(vehicleDto2))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        VehicleDto vehicleDto3 = new VehicleDto(5L, VehicleType.VAN);

        this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto3))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<ParkingSlotDto> parkingSlotDtoList = new ArrayList<>();

        ParkingSlotDto parkingLotDto3 = new ParkingSlotDto(5, VehicleType.VAN, vehicleDto3, null );
        parkingSlotDtoList.add(parkingLotDto3);

        for (int i = 4; i>=2; i--) {
            ParkingSlotDto parkingLotDto2 = new ParkingSlotDto(i, VehicleType.CAR, new VehicleDto((long) i, VehicleType.CAR), null );
            parkingSlotDtoList.add(parkingLotDto2);
        }

        ParkingSlotDto parkingLotDto = new ParkingSlotDto(1, VehicleType.MOTORCYCLE, vehicleDto, null );
        parkingSlotDtoList.add(parkingLotDto);

        String contentText = this.mockMvc.perform(get("/vehicle/parking-slots-filled")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<ParkingSlotDto> parkingSlotDtoListResponse = objectMapper.readValue(contentText, typeFactory.constructCollectionType(List.class, ParkingSlotDto.class));
        parkingSlotDtoListResponse = parkingSlotDtoListResponse.stream().
                peek(parkingSlotDto -> parkingSlotDto.setParkingDate(null))
                .collect(Collectors.toList());

        Assertions.assertEquals(parkingSlotDtoList, parkingSlotDtoListResponse);
    }

}
