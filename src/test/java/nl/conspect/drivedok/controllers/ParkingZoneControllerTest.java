package nl.conspect.drivedok.controllers;


import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.services.ParkingZoneService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.xml.transform.Result;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParkingZoneController.class)
class ParkingZoneControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ParkingZoneService parkingZoneService;

    @Test
    public void shouldFindAllParkingZones() throws Exception {
        mockMvc.perform(get("/parkingzone/home"))
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
        Mockito.when(parkingZoneService.findById(1L))
                .thenReturn(Optional.of(parkingZone));

        MvcResult mvcResult = mockMvc.perform(get("/parkingzone/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        boolean contains = contentAsString.contains("DriveDok Zone: NieuweZone<");
    }

    @Disabled
    public void shouldReturn404() throws Exception {
        mockMvc.perform(
                get("/parkingzone/20000"))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }
}