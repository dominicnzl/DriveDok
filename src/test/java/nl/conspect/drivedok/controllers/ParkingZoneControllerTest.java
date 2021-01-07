package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.services.ParkingZoneService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParkingZoneController.class)
class ParkingZoneControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ParkingZoneService parkingZoneService;

    @Test
    public void homeShouldShowThereAreNoParkingZonesYet() throws Exception {
        mockMvc.perform(get("/parkingzone/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("You have no DriveDok Zones yet")))
                .andReturn();
    }

    @Test
    public void shouldShowListOfParkingZones() throws Exception {
        List<ParkingZone> listPZ = new ArrayList<>();
        ParkingZone pz1 = new ParkingZone(1L, "Zone 1", null, 100);
        ParkingZone pz2 = new ParkingZone(2L, "Zone 2", null, 200);
        listPZ.add(pz1);
        listPZ.add(pz2);

        when(parkingZoneService.findAll()).thenReturn(listPZ);

        mockMvc.perform(get("/parkingzone/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Zone 1")))
                .andExpect(content().string(containsString("Zone 2")));
    }

    @Test
    public void shouldShowParkingZoneForm() throws Exception {

        mockMvc.perform(get("/parkingzone/create"))
                .andDo(print())
                .andExpect(content().string(containsString("Create a new DriveDok Zone for your Parking Spots")));
    }

    @Test
    public void shouldCreateParkingZone() throws Exception {

        mockMvc.perform(post("/parkingzone/create", ParkingZone.class)
                .param("name", "Zone 1")
                .param("totalParkingSpots", "100"))
                .andDo(print())
                .andExpect(content().string(containsString("Zone 1")))
        ;
    }

    @Test
    public void shouldReturnParkingZoneById() throws Exception {
    //    ParkingSpot ps = new ParkingSpot();
        ParkingZone pz1 = new ParkingZone("Zone 1", Collections.emptySet(), 100);

        when(parkingZoneService.findById(1L))
                .thenReturn(Optional.of(pz1));

        mockMvc.perform(get("/parkingzone/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("DriveDok Zone: Zone 1")));
    }

    @Test
    public void shouldUpdateParkingZone() throws Exception {

        mockMvc.perform(post("/parkingzone/update", ParkingZone.class)
                .param("name", "Zone 1")
                .param("totalParkingSpots", "100"))
                .andDo(print())
                .andExpect(content().string(containsString("Zone 1")));
    }

    @Test
    public void shouldDeleteParkingZone() throws Exception {

        ParkingZone pz1 = new ParkingZone(1L, "Zone 1", null, 100);
        when(parkingZoneService.findById(1L))
                .thenReturn(Optional.of(pz1));

        mockMvc.perform(MockMvcRequestBuilders.get("/parkingzone/delete/{id}", 1))
                .andExpect(status().isOk());

    }
  
    @Test
    @DisplayName("When an id is used to findById but no ParkingZone is found throw an IllegalArgumentException")
    public void whenFailToFindByIdThrowIllegalArgument() {
        assertThatThrownBy(() -> mockMvc.perform(get("/parkingzone/-1"))
                .andExpect(status().isOk()))
                .hasCause(new IllegalArgumentException("ParkingZone with id -1 not found"));
    }
}
