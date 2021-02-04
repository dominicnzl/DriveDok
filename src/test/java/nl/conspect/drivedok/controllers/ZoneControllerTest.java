package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.Zone;
import nl.conspect.drivedok.services.ZoneService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
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

@WebMvcTest(ZoneController.class)
class ZoneControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ZoneService zoneService;

    @Test
    public void homeShouldShowThereAreNoZonesYet() throws Exception {
        mockMvc.perform(get("/zones"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("You have no DriveDok Zones yet")))
                .andReturn();
    }

    @Test
    public void shouldShowListOfZones() throws Exception {
        List<Zone> listPZ = new ArrayList<>();
        Zone pz1 = new Zone("Zone 1", 100);
        Zone pz2 = new Zone("Zone 2",200);
        listPZ.add(pz1);
        listPZ.add(pz2);

        when(zoneService.findAll()).thenReturn(listPZ);

        mockMvc.perform(get("/zones"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Zone 1")))
                .andExpect(content().string(containsString("Zone 2")));
    }

    @Test
    public void shouldShowZoneForm() throws Exception {

        mockMvc.perform(get("/zones/create"))
                .andDo(print())
                .andExpect(content().string(containsString("Create a new DriveDok Zone")));
    }

    @Test
    public void shouldCreateZone() throws Exception {

        mockMvc.perform(post("/zones/create", Zone.class)
                .param("name", "Zone 1")
                .param("totalParkingSpots", "100"))
                .andDo(print())
                .andExpect(content().string(containsString("Zone 1")))
        ;
    }

    @Test
    public void shouldReturnZoneById() throws Exception {
        Zone pz1 = new Zone("Zone 1",  100);

        when(zoneService.findById(1L))
                .thenReturn(Optional.of(pz1));

        mockMvc.perform(get("/zones/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("Your Zone Zone 1 has")));
    }

    @Test
    public void shouldUpdateZone() throws Exception {

        mockMvc.perform(post("/zones/update", Zone.class)
                .param("name", "Zone 1")
                .param("totalParkingSpots", "100"))
                .andDo(print())
                .andExpect(content().string(containsString("Zone 1")));
    }

    @Test
    public void shouldUpdateZonePSQuantity() throws Exception {
        Zone zone = new Zone("Zone 1",  100);
        zone.addParkingSpot(new ParkingSpot(ParkingType.DISABLED, 0));
        zone.addParkingSpot(new ParkingSpot(ParkingType.ELECTRIC, 20));
        zone.addParkingSpot(new ParkingSpot(ParkingType.NORMAL, zone.getTotalParkingSpots()-20));

        when(zoneService.update(zone)).thenReturn(zone);

        mockMvc.perform(post("/zones/update", Zone.class)
                .param("name", "Zone 1")
                .param("totalParkingSpots", "100"))
                .andDo(print())
                .andExpect(content().string(containsString("Zone 1")));
    }

    @Test
    public void shouldDeleteZone() throws Exception {

        Zone pz1 = new Zone("Zone 1", 100);
        when(zoneService.findById(1L))
                .thenReturn(Optional.of(pz1));

        mockMvc.perform(MockMvcRequestBuilders.get("/zones/delete/{id}", 1))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("When an id is used to findById but no Zone is found throw an IllegalArgumentException")
    public void whenFailToFindByIdThrowIllegalArgument() {
        assertThatThrownBy(() -> mockMvc.perform(get("/zones/-1"))
                .andExpect(status().isOk()))
                .hasCause(new IllegalArgumentException("Zone with id -1 not found"));
    }
}
