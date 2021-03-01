package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.UserDto;
import nl.conspect.drivedok.services.UserService;
import nl.conspect.drivedok.utilities.UserMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserMapperImpl mapper;

    @MockBean
    UserService service;

    private static final String URL = "/api/users";

    @Test
    void getMapping() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk());
        verify(service, times(1)).findAll();
    }

    @Test
    void getMappingWithId() throws Exception {
        var user = new User();
        var dto = new UserDto();
        when(service.findById(78L)).thenReturn(Optional.of(user));
        when(mapper.userToDto(any())).thenReturn(dto);
        mockMvc.perform(get(URL.concat("/78")))
                .andExpect(status().isOk());
        verify(service, times(1)).findById(78L);
    }

    @Test
    void postMapping() throws Exception {
        var user = new User();
        when(mapper.dtoToUser(any())).thenReturn(user);
        when(service.save(any())).thenReturn(user);
        mockMvc.perform(post(URL)
                .contentType(APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated());
        verify(service, times(1)).save(user);
    }

    @Test
    void putMapping() throws Exception {
        var user = new User();
        when(service.findById(20L)).thenReturn(Optional.of(user));
        when(mapper.dtoToUser(any())).thenReturn(user);
        when(service.save(any())).thenReturn(user);
        mockMvc.perform(put(URL.concat("/20"))
                .contentType(APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
        verify(service, times(1)).save(user);
    }

    @Test
    void patchMapping() throws Exception {
        var user = new User();
        when(service.findById(5L)).thenReturn(Optional.of(user));
        when(mapper.patchDtoToUser(any(), any())).thenReturn(user);
        when(service.save(any())).thenReturn(user);
        mockMvc.perform(patch(URL.concat("/5"))
                .contentType(APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
        verify(mapper, times(1)).patchDtoToUser(any(), any());
        verify(service, times(1)).save(user);
    }

    @Test
    void deleteMapping() throws Exception {
        var user = new User();
        when(service.findById(64L)).thenReturn(Optional.of(user));
        mockMvc.perform(delete(URL.concat("/64")))
                .andExpect(status().isNoContent());
        verify(service, times(1)).deleteById(64L);
    }
}
