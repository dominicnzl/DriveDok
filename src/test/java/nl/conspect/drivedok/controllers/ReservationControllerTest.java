package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.services.ReservationService;
import nl.conspect.drivedok.services.UserService;
import nl.conspect.drivedok.services.VehicleService;
import nl.conspect.drivedok.utilities.ReservationMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReservationService service;

    @MockBean
    ReservationMapper mapper;

    @MockBean
    UserService userService;

    @MockBean
    VehicleService vehicleService;

    @Value("${reservation.list.page}")
    private String listpage;

    @Value("${reservation.edit.page}")
    private String editpage;

    private static final String URL = "/reservations";

    @Test
    void listpage() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reservations"))
                .andExpect(view().name(listpage));
    }

    @Test
    void newpage() throws Exception {
        mockMvc.perform(get(URL.concat("/new")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reservation"))
                .andExpect(view().name(editpage));
    }

    @Test
    void editpage() throws Exception {
        var entity = new Reservation();
        Mockito.when(service.findById(1L)).thenReturn(Optional.of(entity));
        mockMvc.perform(get(URL.concat("/1")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reservation"))
                .andExpect(view().name(editpage));
    }

    @Test
    void save() throws Exception {
        mockMvc.perform(post(URL).content("{}"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reservations", "reservationDto"))
                .andExpect(view().name(listpage));
        verify(service, times(1)).save(any());
    }
}
