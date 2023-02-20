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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CarTests {

    private final MockMvc mockMvc;
    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    @Autowired
    public CarTests(MockMvc mockMvc, MessageSource messageSource, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.messageSource = messageSource;
        this.objectMapper = objectMapper;
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }

    @Test
    public void whenParkCarsInAllSpots() throws Exception {

        for (long i=2; i<=5; i++) {
            VehicleDto vehicleDto = new VehicleDto();
            vehicleDto.setId(i);
            vehicleDto.setVehicleType(VehicleType.CAR);

            this.mockMvc.perform(post("/vehicle/park")
                    .content(objectMapper.writeValueAsString(vehicleDto))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(6L);
        vehicleDto.setVehicleType(VehicleType.CAR);

        String msg = this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(msg, getMessage("parking.lot.full"));
    }

}
