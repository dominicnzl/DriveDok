package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.model.ReservationDto;
import nl.conspect.drivedok.services.ReservationService;
import nl.conspect.drivedok.services.UserService;
import nl.conspect.drivedok.services.VehicleService;
import nl.conspect.drivedok.utilities.ReservationMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static nl.conspect.drivedok.controllers.ReservationController.RESERVATION_EDITPAGE;
import static nl.conspect.drivedok.controllers.ReservationController.RESERVATION_LISTPAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReservationService reservationService;

    @MockBean
    ReservationMapper mapper;

    @MockBean
    UserService userService;

    @MockBean
    VehicleService vehicleService;

    private static final String URL = "/reservations";

    @Test
    void listpage() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reservations"))
                .andExpect(view().name(RESERVATION_LISTPAGE));
        verify(reservationService, times(1)).findAll();
    }

    @Test
    void newpage() throws Exception {
        mockMvc.perform(get(URL.concat("/new")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reservationDto"))
                .andExpect(view().name(RESERVATION_EDITPAGE));
        verify(userService, times(1)).findAll();
        verify(vehicleService, times(1)).findAll();
    }

    @Test
    void editpage() throws Exception {
        var entity = new Reservation();
        when(reservationService.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.reservationToDto(any())).thenReturn(new ReservationDto());
        mockMvc.perform(get(URL.concat("/1")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reservationDto"))
                .andExpect(view().name(RESERVATION_EDITPAGE));
        verify(reservationService, times(1)).findById(1L);
    }

    @Test
    void handleDelete() throws Exception {
        doNothing().when(reservationService).deleteById(1L);
        mockMvc.perform(delete(URL.concat("/1")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reservations"))
                .andExpect(view().name(RESERVATION_LISTPAGE));
        verify(reservationService, times(1)).deleteById(1L);
    }

    @Test
    void saveHappy() throws Exception {
        mockMvc.perform(post(URL).content("{}"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reservations", "reservationDto"))
                .andExpect(view().name(RESERVATION_LISTPAGE));
        verify(reservationService, times(1)).save(any());
    }

    @Test
    void saveWithBindingErrors() throws Exception {
        mockMvc.perform(post(URL, ReservationDto.class)
                .param("startDate", "geen echte datum"))
                .andExpect(status().isOk())
                .andExpect(view().name(RESERVATION_EDITPAGE))
                .andDo(print());
        verify(reservationService, never()).save(any());
    }
}
