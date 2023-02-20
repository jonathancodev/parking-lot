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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VanTests {

    private final MockMvc mockMvc;
    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    @Autowired
    public VanTests(MockMvc mockMvc, MessageSource messageSource, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.messageSource = messageSource;
        this.objectMapper = objectMapper;
    }

    private String getMessage(String code) {
        return messageSource.getMessage(code, null, Locale.ENGLISH);
    }

    @Test
    public void whenParkVansInAllSpots() throws Exception {

        for (long i=4; i<=5; i++) {
            VehicleDto vehicleDto = new VehicleDto();
            vehicleDto.setId(i);
            vehicleDto.setVehicleType(VehicleType.VAN);

            this.mockMvc.perform(post("/vehicle/park")
                    .content(objectMapper.writeValueAsString(vehicleDto))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(6L);
        vehicleDto.setVehicleType(VehicleType.VAN);

        String msg = this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(msg, getMessage("parking.lot.full"));

        for (long i=4; i<=5; i++) {
            this.mockMvc.perform(delete("/vehicle/remove/"+i)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void whenVanCanParkInCarSpot() throws Exception {

        for (long i=1; i<=2; i++) {
            VehicleDto vehicleDto = new VehicleDto();
            vehicleDto.setId(i);
            vehicleDto.setVehicleType(VehicleType.VAN);

            this.mockMvc.perform(post("/vehicle/park")
                    .content(objectMapper.writeValueAsString(vehicleDto))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        for (long i=1; i<=2; i++) {
            this.mockMvc.perform(delete("/vehicle/remove/"+i)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void whenVanCannotParkInCarSpotSample1() throws Exception {

        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(1L);
        vehicleDto.setVehicleType(VehicleType.VAN);

        this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        VehicleDto vehicleDto2 = new VehicleDto();
        vehicleDto2.setId(2L);
        vehicleDto2.setVehicleType(VehicleType.CAR);

        this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto2))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        VehicleDto vehicleDto3 = new VehicleDto();
        vehicleDto3.setId(3L);
        vehicleDto3.setVehicleType(VehicleType.VAN);

        String msg = this.mockMvc.perform(post("/vehicle/park")
                .content(objectMapper.writeValueAsString(vehicleDto3))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(msg, getMessage("parking.lot.full"));

        for (long i=1; i<=2; i++) {
            this.mockMvc.perform(delete("/vehicle/remove/"+i)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

}
