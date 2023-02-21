package com.parking.parkinglotapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.parkinglotapi.dto.VehicleDto;
import com.parking.parkinglotapi.enums.VehicleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ParkVehicleTests {

    private final MockMvc mockMvc;
    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    @Autowired
    public ParkVehicleTests(MockMvc mockMvc, MessageSource messageSource, ObjectMapper objectMapper) throws Exception {
        this.mockMvc = mockMvc;
        this.messageSource = messageSource;
        this.objectMapper = objectMapper;
        clearAll(1,5);
    }

    private void clearAll(int from, int to) throws Exception {
        for (int i=from; i<=to; i++) {
            this.mockMvc.perform(delete("/vehicle/remove/"+i)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON));
        }
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }

    @Test
    public void whenParkNullVehicle() throws Exception {
        this.mockMvc.perform(post("/vehicle/park")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenParkVehicleWithoutId() throws Exception {
        VehicleDto vehicleDto = new VehicleDto();

        String msg = this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(msg, getMessage("parking.lot.vehicle.id.required"));
    }

    @Test
    public void whenParkVehicleWithoutType() throws Exception {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(1L);

        String msg = this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(msg, getMessage("parking.lot.vehicle.type.required"));
    }

    @Test
    public void whenParkVehicle() throws Exception {
        VehicleDto vehicleDto = new VehicleDto(1L, VehicleType.MOTORCYCLE);

        this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenParkVehicleDuplicate() throws Exception {
        VehicleDto vehicleDto = new VehicleDto(2L, VehicleType.MOTORCYCLE);

        this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String msg = this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(msg, getMessage("parking.lot.vehicle.unique"));
    }

}
