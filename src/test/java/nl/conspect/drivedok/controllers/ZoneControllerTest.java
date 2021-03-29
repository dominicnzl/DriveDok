package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.Zone;
import nl.conspect.drivedok.model.ZoneDto;
import nl.conspect.drivedok.services.ZoneService;
import nl.conspect.drivedok.utilities.ZoneMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ZoneController.class)
class ZoneControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ZoneMapper zoneMapper;

    @MockBean
    ZoneService zoneService;

    @Test
    void should_Show_ZoneListPage() throws Exception {
        Zone zone1 = new Zone("Noord", 100);
        Zone zone2 = new Zone("Oost", 200);
        Zone zone3 = new Zone("Zuid", 300);
        var expected = List.of(zone1, zone2, zone3);
        when(zoneService.findAll()).thenReturn(expected);

        mockMvc.perform(get("/zones"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("zones"))
                .andReturn();
    }

    @Test
    void homeShouldShowThereAreNoZonesYet() throws Exception {
        mockMvc.perform(get("/zones"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("You have no DriveDok Zones yet")))
                .andReturn();
    }

    @Test
    void shouldContainListOfZones() throws Exception {
        List<Zone> listPZ = new ArrayList<>();
        Zone pz1 = new Zone("Zone 1", 100);
        Zone pz2 = new Zone("Zone 2", 200);
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
    void shouldShowZoneForm() throws Exception {

        mockMvc.perform(get("/zones/create"))
                .andDo(print())
                .andExpect(content().string(containsString("Create a new DriveDok Zone")));
    }

    @Test
    void shouldCreateZone() throws Exception {
        when(zoneMapper.dtoToZone(any())).thenReturn(new Zone());
        mockMvc.perform(post("/zones/create", Zone.class)
                .param("name", "Zone 1")
                .param("totalParkingSpots", "100"))
                .andDo(print())
                .andExpect(view().name("redirect:/zones"));
    }

    @Test
    void shouldReturnZoneById() throws Exception {
        Zone pz1 = new Zone("Zone 1", 100);
        when(zoneService.findById(1L)).thenReturn(Optional.of(pz1));
        when(zoneMapper.zoneToDto(pz1)).thenReturn(new ZoneDto());
        mockMvc.perform(get("/zones/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/zone/zone-editpage"));
    }

    @Test
    void shouldGoToZoneListPageAfterDeletingZone() throws Exception {

        Zone pz1 = new Zone("Zone 1", 100);
        when(zoneService.findById(1L))
                .thenReturn(Optional.of(pz1));

        mockMvc.perform(MockMvcRequestBuilders.get("/zones/delete/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/zone/zone-listpage"))
        ;
    }

    @Test
    @DisplayName("When an id is used to findById but no Zone is found throw an IllegalArgumentException")
    void whenFailToFindByIdThrowIllegalArgument() {
        assertThatThrownBy(() -> mockMvc.perform(get("/zones/-1"))
                .andExpect(status().isOk()))
                .hasCause(new IllegalArgumentException("Zone with id -1 not found"));
    }

    @Test
    @DisplayName("Post and force binding errors. Expect zoneService.create never to be called")
    void handlePostWithBindingErrors() throws Exception {
        mockMvc.perform(post("/zones/create")
                .param("name", "")
                .param("totalParkingSpots", "0"))
                .andExpect(model().hasErrors())
                .andDo(print());
        verify(zoneService, never()).create(any());
    }

    @Test
    @DisplayName("Post a valid Zone and expect .create to be called once")
    void handlePostHappy() throws Exception {
        var zone = new Zone();
        zone.setId(3L);
        when(zoneMapper.dtoToZone(any())).thenReturn(zone);
        mockMvc.perform(post("/zones/create")
                .param("name", "Julia")
                .param("totalParkingSpots", "10"))
                .andExpect(view().name("redirect:/zones"))
                .andDo(print());
        verify(zoneService, times(1)).create(any());
    }

    @Test
    @DisplayName("Update and force binding errors. Expect service update method never to be called")
    void handleUpdateWithBindingErrors() throws Exception {
        mockMvc.perform(post("/zones/update")
                .param("name", "")
                .param("totalParkingSpots", ""))
                .andExpect(view().name("/zone/zone-editpage"))
                .andDo(print());
        verify(zoneService, never()).update(any());
    }

    @Test
    void handleUpdateOk() throws Exception {
        var zone = new Zone();
        zone.setId(4L);
        when(zoneMapper.dtoToZone(any())).thenReturn(zone);
        mockMvc.perform(post("/zones/update")
                .param("name", "Svetlana")
                .param("totalParkingSpots", "5"))
                .andExpect(view().name("/zone/zone-editpage"))
                .andDo(print());
        verify(zoneService, times(1)).update(any());
    }
}
