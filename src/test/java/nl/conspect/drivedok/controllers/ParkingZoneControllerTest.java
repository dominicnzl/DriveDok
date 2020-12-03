package nl.conspect.drivedok.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ParkingZoneControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldFindAllParkingZones() throws Exception {
        mockMvc.perform(get("/parkingzone/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateParkingZone() throws Exception {
        mockMvc.perform(post("/parkingzone/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" {" +
                        "    \"name\": \"Zone52\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}