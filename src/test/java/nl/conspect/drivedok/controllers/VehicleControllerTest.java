package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.services.VehicleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static nl.conspect.drivedok.controllers.VehicleController.VEHICLE_EDITPAGE;
import static nl.conspect.drivedok.controllers.VehicleController.VEHICLE_LISTPAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private VehicleController vehicleController;

    @MockBean
    private VehicleService vehicleService;

    @Test
    @DisplayName("Calling '/vehicles' should result in the listpage being returned")
    void handleGet() throws Exception {
        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vehicles"))
                .andExpect(view().name(VEHICLE_LISTPAGE));
    }

    @Test
    @DisplayName("Calling '/vehicles/1' should result in the editpage being returned")
    void handleGetId() throws Exception {
        when(vehicleService.findById(1L)).thenReturn(Optional.of(new Vehicle()));
        mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vehicle"))
                .andExpect(view().name(VEHICLE_EDITPAGE));
    }

    @Test
    @DisplayName("When delete is called with id null, redirect to /vehicles")
    void idNullThenReturnVehiclesListPage() {
        when(vehicleService.findById(null)).thenReturn(Optional.of(new Vehicle()));
        assertThat(vehicleController.deleteAndReturnToPage(null)).isEqualTo("redirect:/vehicles");
    }

    @Test
    @DisplayName("When delete is called on a vehicle without an associated user, redirect to /vehicles")
    void vehicleWithoutUser() {
        when(vehicleService.findById(1L)).thenReturn(Optional.of(new Vehicle()));
        assertThat(vehicleController.deleteAndReturnToPage(1L)).isEqualTo("redirect:/vehicles");
    }

    @Test
    @DisplayName("When delete is called on a vehicle with an associated User, redirect to /users/{userId}")
    void vehicleWithUser() {
        var user = new User("Henk", "henk@email.nl", "wachtwoord123");
        user.setId(123L);
        var vehicle = new Vehicle();
        vehicle.setUser(user);
        when(vehicleService.findById(1L)).thenReturn(Optional.of(vehicle));
        assertThat(vehicleController.deleteAndReturnToPage(1L)).isEqualTo("redirect:/users/123");
    }
}
