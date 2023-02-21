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
public class MotorcycleTests {

    private final MockMvc mockMvc;
    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    @Autowired
    public MotorcycleTests(MockMvc mockMvc, MessageSource messageSource, ObjectMapper objectMapper) throws Exception {
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
    public void whenParkMotorcyclesInAllSpots() throws Exception {

        for (long i=1; i<=5; i++) {
            VehicleDto vehicleDto = new VehicleDto(i, VehicleType.MOTORCYCLE);

            this.mockMvc.perform(post("/vehicle/park")
                    .content(objectMapper.writeValueAsString(vehicleDto))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        VehicleDto vehicleDto = new VehicleDto(6L, VehicleType.MOTORCYCLE);

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
