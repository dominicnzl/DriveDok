package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.model.VehicleDto;
import nl.conspect.drivedok.services.VehicleService;
import nl.conspect.drivedok.utilities.VehicleMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleRestController.class)
class VehicleRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    VehicleMapperImpl mapper;

    @MockBean
    VehicleService service;

    private static final String BASE_URL = "/api/vehicles";

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andDo(print());
        verify(service, times(1)).findAll();
    }

    @Test
    void findById() throws Exception {
        var vehicle = new Vehicle();
        var dto = new VehicleDto();
        when(service.findById(999L)).thenReturn(Optional.of(vehicle));
        when(mapper.vehicleToDto(vehicle)).thenReturn(dto);
        mockMvc.perform(get(BASE_URL.concat("/999")))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service, times(1)).findById(999L);
    }

    @Test
    void create() throws Exception {
        var vehicle = new Vehicle();
        when(mapper.dtoToVehicle(any())).thenReturn(vehicle);
        when(service.save(any())).thenReturn(vehicle);
        mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isCreated());
        verify(service, times(1)).save(vehicle);
    }

    @Test
    void update() throws Exception {
        var vehicle = new Vehicle();
        when(service.findById(1L)).thenReturn(Optional.of(vehicle));
        when(mapper.dtoToVehicle(any())).thenReturn(vehicle);
        when(service.save(any())).thenReturn(vehicle);
        mockMvc.perform(put(BASE_URL.concat("/1"))
                .contentType(APPLICATION_JSON)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service, times(1)).save(vehicle);
    }

    @Test
    void updatePartially() throws Exception {
        var vehicle = new Vehicle();
        when(service.findById(5L)).thenReturn(Optional.of(vehicle));
        when(mapper.patchDtoToVehicle(any(), any())).thenReturn(vehicle);
        when(service.save(any())).thenReturn(vehicle);
        mockMvc.perform(patch(BASE_URL.concat("/5"))
                .contentType(APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
        verify(mapper, times(1)).patchDtoToVehicle(any(), any());
        verify(service, times(1)).save(vehicle);
    }

    @Test
    void deleteMapping() throws Exception {
        var vehicle = new Vehicle();
        when(service.findById(10L)).thenReturn(Optional.of(vehicle));
        mockMvc.perform(delete(BASE_URL.concat("/10")))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(service, times(1)).deleteById(10L);
    }
}