package com.parking.parkinglotapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.parkinglotapi.dto.ParkingLotDto;
import com.parking.parkinglotapi.dto.VehicleDto;
import com.parking.parkinglotapi.enums.VehicleType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DashboardTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public DashboardTests(MockMvc mockMvc, ObjectMapper objectMapper) throws Exception {
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
    public void whenParkingSpotIsNotFullSample1() throws Exception {

        String msg = this.mockMvc.perform(get("/dashboard/is-parked-lot-full")
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(msg, "No");
    }

    @Test
    public void whenParkingSpotIsNotFullSample2() throws Exception {

        VehicleDto vehicleDto = new VehicleDto(1L, VehicleType.MOTORCYCLE);

        this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String msg = this.mockMvc.perform(get("/dashboard/is-parked-lot-full")
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(msg, "No");
    }

    @Test
    public void parkingSpotRemaining() throws Exception {

        VehicleDto vehicleDto = new VehicleDto(1L, VehicleType.MOTORCYCLE);

        this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ParkingLotDto parkingLotDto = new ParkingLotDto(0L,3L,1L);

        String msg = this.mockMvc.perform(get("/dashboard/remaining-spots")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(msg, objectMapper.writeValueAsString(parkingLotDto));
    }

    @Test
    public void parkingVanParkedSpots() throws Exception {

        VehicleDto vehicleDto = new VehicleDto(1L, VehicleType.VAN);

        this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ParkingLotDto parkingLotDto = new ParkingLotDto(0L,0L,1L);

        String msg = this.mockMvc.perform(get("/dashboard/van-parked-spots")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(msg, objectMapper.writeValueAsString(parkingLotDto));
    }

    @Test
    public void whenParkingSpotIsFull() throws Exception {

        for (long i=1; i<=5; i++) {
            VehicleDto vehicleDto = new VehicleDto(i, VehicleType.MOTORCYCLE);

            this.mockMvc.perform(post("/vehicle/park")
                    .content(objectMapper.writeValueAsString(vehicleDto))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        String msg = this.mockMvc.perform(get("/dashboard/is-parked-lot-full")
                .accept(MediaType.TEXT_PLAIN)
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(msg, "Yes");
    }

}
