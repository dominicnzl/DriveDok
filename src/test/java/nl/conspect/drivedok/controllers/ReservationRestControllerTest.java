package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.model.ReservationDto;
import nl.conspect.drivedok.services.ReservationService;
import nl.conspect.drivedok.utilities.ReservationMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationRestController.class)
class ReservationRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReservationMapper mapper;

    @MockBean
    ReservationService service;

    private static final String URL = "/api/reservations";

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get(URL)).andExpect(status().isOk());
        verify(service, times(1)).findAll();
    }

    @Test
    void findByIdNotFound() throws Exception {
        mockMvc.perform(get(URL.concat("/404"))).andExpect(status().isNotFound());
        verify(service, times(1)).findById(404L);
    }

    @Test
    void findById() throws Exception {
        var reservation = new Reservation();
        var dto = new ReservationDto();
        when(service.findById(200L)).thenReturn(Optional.of(reservation));
        mockMvc.perform(get(URL.concat("/200"))).andExpect(status().isOk());
        verify(service, times(1)).findById(200L);
    }

    @Test
    void create() throws Exception {
        var reservation = new Reservation();
        reservation.setId(1L);
        when(mapper.dtoToReservation(any())).thenReturn(reservation);
        when(service.save(any())).thenReturn(reservation);
        mockMvc.perform(post(URL)
                .contentType(APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrl(URL.concat("/1")));
        verify(service, times(1)).save(reservation);
    }

    @Test
    void update() throws Exception {
        var reservation = new Reservation();
        when(mapper.dtoToReservation(any())).thenReturn(reservation);
        when(service.save(any())).thenReturn(reservation);
        mockMvc.perform(put(URL.concat("/20"))
                .contentType(APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
        verify(service, times(1)).save(reservation);
    }

    @Test
    void updatePartially() throws Exception {
        var reservation = new Reservation();
        when(service.findById(5L)).thenReturn(Optional.of(reservation));
        when(mapper.patchDtoToReservation(any(), any())).thenReturn(reservation);
        when(service.save(any())).thenReturn(reservation);
        mockMvc.perform(patch(URL.concat("/5"))
                .contentType(APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
        verify(mapper, times(1)).patchDtoToReservation(any(), any());
        verify(service, times(1)).save(reservation);

    }

    @Test
    void deleteMappingHappy() throws Exception {
        var reservation = new Reservation();
        when(service.findById(45L)).thenReturn(Optional.of(reservation));
        mockMvc.perform(delete(URL.concat("/45"))).andExpect(status().isNoContent());
        verify(service, times(1)).delete(reservation);
    }

    @Test
    void deleteMappingEntityNotFound() throws Exception {
        when(service.findById(22L)).thenReturn(Optional.empty());
        mockMvc.perform(delete(URL.concat("/22"))).andExpect(status().isNotFound());
        verify(service, never()).delete(any());
    }
}