package nl.conspect.drivedok.controllers;


import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.services.ParkingZoneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ParkingZoneControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ParkingZoneService parkingZoneService;


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

    @Test
    public void shouldReturnParkingZoneById() throws Exception {

        ParkingZone parkingZone = new ParkingZone();
        parkingZone.setName("NieuweZone");
        ParkingZone parkingZone1 = parkingZoneService.create(parkingZone);
        Long id = parkingZone1.getId();

        mockMvc.perform(get("/parkingzone/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowException() {
        Exception exception = assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/parkingzone/123546"));
        });
    }
}