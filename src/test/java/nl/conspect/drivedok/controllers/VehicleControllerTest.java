package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.services.VehicleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleControllerTest {

    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleService;

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