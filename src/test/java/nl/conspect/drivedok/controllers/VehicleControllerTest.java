package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.services.VehicleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.mock;

class VehicleControllerTest {

    private VehicleController vehicleController;

    private VehicleService vehicleService;

    @BeforeEach
    void init() {
        vehicleService = mock(VehicleService.class);
        this.vehicleController = new VehicleController(vehicleService);
    }

    @Test
    @DisplayName("When delete is called with id null, redirect to /vehicles")
    void idNullThenReturnVehiclesListPage() {
        Mockito.when(vehicleService.findById(null)).thenReturn(Optional.of(new Vehicle()));
        Assertions.assertEquals("redirect:/vehicles", vehicleController.deleteAndReturnToListPage(null));
    }

    @Test
    @DisplayName("When delete is called on a vehicle without an associated user, redirect to /vehicles")
    void vehicleWithoutUser() {
        Mockito.when(vehicleService.findById(1L)).thenReturn(Optional.of(new Vehicle()));
        Assertions.assertEquals("redirect:/vehicles", vehicleController.deleteAndReturnToListPage(1L));
    }

    @Test
    @DisplayName("When delete is called on a vehicle with an associated User, redirect to /users/{userId}")
    void vehicleWithUser() {
        var user = new User("Henk", "henk@email.nl", "wachtwoord123");
        user.setId(123L);
        var vehicle = new Vehicle();
        vehicle.setUser(user);
        Mockito.when(vehicleService.findById(1L)).thenReturn(Optional.of(vehicle));
        Assertions.assertEquals("redirect:/users/123", vehicleController.deleteAndReturnToListPage(1L));
    }
}