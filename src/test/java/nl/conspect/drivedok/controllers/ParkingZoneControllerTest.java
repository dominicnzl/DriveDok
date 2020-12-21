package nl.conspect.drivedok.controllers;


import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.repositories.ParkingZoneRepository;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
    public void shouldShowThereAreNoPZYet() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/parkingzone/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        boolean hasString = contentAsString.contains("You have no DriveDok Zones yet");
    }

    @Test
    public void shouldShowListOfParkingZones() throws Exception {
        List<ParkingZone> listPZ = new ArrayList<>();
        ParkingZone pz1 = new ParkingZone(1L, "Zone 1", null, 100);
        ParkingZone pz2 = new ParkingZone(2L, "Zone 2", null, 200);
        listPZ.add(pz1);
        listPZ.add(pz2);

        when(parkingZoneService.findAll()).thenReturn(listPZ);

        MvcResult mvcResult = mockMvc.perform(get("/parkingzone/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        boolean hasString = contentAsString.contains("Zone 1");
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
        when(parkingZoneService.findById(1L))
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