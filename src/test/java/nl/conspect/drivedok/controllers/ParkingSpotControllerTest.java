package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.services.ParkingSpotService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static nl.conspect.drivedok.model.ParkingType.possibleTypes;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParkingSpotController.class)
class ParkingSpotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingSpotService parkingSpotService;

    @Test
    @DisplayName("""
            Call "/parkingspots" when no ParkingSpots exists. Expect the view to be "parkingspotlistpage" and the body 
            to contain the message "No parkingspots found". 
            """)
    void emptyListFindAll() throws Exception {
        mockMvc.perform(get("/parkingspots"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("parkingspotlistpage"))
                .andExpect(content().string(containsString("No parkingspots found")));
    }

    @Test
    @DisplayName("""
            Call "/parkingspots" with two existing ParkingSpots. Expect the view to be "parkingspotlistpage" and
            the body to contain
            """)
    void nonEmptyListFindAll() throws Exception {
        List<ParkingSpot> spots = List.of(
                new ParkingSpot(1L, ParkingType.ELECTRIC, 1),
                new ParkingSpot(2L, ParkingType.DISABLED, 2));
        when(parkingSpotService.findAll()).thenReturn(spots);
        mockMvc.perform(get("/parkingspots"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("parkingspotlistpage"))
                .andExpect(model().attributeExists("parkingSpots"))
                .andExpect(model().attribute("parkingSpots", spots));
    }

    @Test
    @DisplayName("""
            Call "/parkingspots/1" and expect to get the parkingspoteditpage.html template. The attribute "parkingSpot" 
            should equal with the ParkingSpot in findById. The attribute "parkingTypes" should equal 
            ParkingType.possibleTypes()
            """)
    void editPage() throws Exception {
        var spot = new ParkingSpot(1L, ParkingType.NORMAL, 20);
        Mockito.when(parkingSpotService.findById(1L))
                .thenReturn(Optional.of(spot));
        mockMvc.perform(get("/parkingspots/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("parkingspoteditpage"))
                .andExpect(model().attributeExists("parkingSpot", "parkingTypes"))
                .andExpect(model().attribute("parkingSpot", spot))
                .andExpect(model().attribute("parkingTypes", possibleTypes()));
    }

    @Test
    @DisplayName("""
            Call "/parkingspots/create" and expect to get the parkingspotcreatepage.html template. The attribute 
            "parkingSpot" should be a new ParkingSpot and the attribute "parkingTypes" should be 
            ParkingType.possibleTypes()
            """)
    void createPage() throws Exception {
        mockMvc.perform(get("/parkingspots/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("parkingspotcreatepage"))
                .andExpect(model().attributeExists("parkingSpot", "parkingTypes"))
                .andExpect(model().attribute("parkingSpot", new ParkingSpot()))
                .andExpect(model().attribute("parkingTypes", possibleTypes()));
    }

    @Test
    @DisplayName("""
            Post to "/parkingspots/save", expect to get parkingspotlistpage.html template. Expect attribute 
            "parkingSpot" to exist and to be a new ParkingSpot.
            """)
    void saveOrUpdateParkingSpot() throws Exception {
        mockMvc.perform(post("/parkingspots/save"))
                .andExpect(status().isOk())
                .andExpect(view().name("parkingspotlistpage"))
                .andExpect(model().attributeExists("parkingSpot"))
                .andExpect(model().attribute("parkingSpot", new ParkingSpot()))
                .andDo(print());
    }

    @Test
    @DisplayName("""
            Call "/parkingspot/delete/1" and expect to get the parkingspotlistpage.html template. Since the model is 
            empty, expect the template to show "No parkingspots found".
            """)
    void deleteAndReturnToListPage() throws Exception {
        var spot = new ParkingSpot(1L, ParkingType.ELECTRIC, 10);
        Mockito.when(parkingSpotService.findById(1L)).thenReturn(Optional.of(spot));
        mockMvc.perform(get("/parkingspots/delete/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("parkingspotlistpage"))
                .andExpect(content().string(containsString("No parkingspots found")))
                .andDo(print());
    }
}