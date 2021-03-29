package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.exceptions.VehicleNotFoundException;
import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.services.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;
import java.util.Optional;

import static nl.conspect.drivedok.model.ParkingType.possibleTypes;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public static final String VEHICLE_LISTPAGE = "vehicle/vehicle-listpage";

    public static final String VEHICLE_EDITPAGE = "vehicle/vehicle-editpage";

    @GetMapping
    public String listpage(Model model) {
        model.addAttribute("vehicles", vehicleService.findAll());
        return VEHICLE_LISTPAGE;
    }

    @GetMapping("/{id}")
    public String editpage(Model model, @PathVariable Long id) {
        final var vehicle = vehicleService.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
        if (null != vehicle.getUser()) {
            model.addAttribute("user", vehicle.getUser());
        }
        model.addAttribute("parkingTypes", possibleTypes());
        model.addAttribute("vehicle", vehicle);
        return VEHICLE_EDITPAGE;
    }

    /**
     * Determine the correct landing page to redirect to based on the vehicle id: if a vehicle with that Id is found,
     * and that vehicle has an associated User, redirect to that User's editpage. Otherwise redirect to the vehicle
     * listpage. Delete the vehicle if present.
     * @param id The VehicleId
     * @return the appropriate Thymeleaf template uri after Vehicle deletion attempt
     */
    @DeleteMapping("/{id}")
    public String deleteAndReturnToPage(@PathVariable Long id) {
        final var returnpage = null == id ? "redirect:/vehicles" : Optional.of(id)
                .flatMap(vehicleService::findById)
                .map(Vehicle::getUser)
                .map(User::getId)
                .map(Objects::toString)
                .map("redirect:/users/"::concat)
                .orElse("redirect:/vehicles");
        vehicleService.findById(id).ifPresent(vehicleService::delete);
        return returnpage;
    }
}
