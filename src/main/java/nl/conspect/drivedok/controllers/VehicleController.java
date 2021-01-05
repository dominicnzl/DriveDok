package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.services.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /* Thymeleaf conrollers */
    @GetMapping
    public String listpage(Model model) {
        model.addAttribute("vehicles", vehicleService.findAll());
        return "vehiclelistpage";
    }

    @GetMapping("/delete/{id}")
    public String deleteAndReturnToList(Model model, @PathVariable Long id) {
        var userId = vehicleService.findById(id)
                .map(Vehicle::getUser)
                .map(User::getId);
        vehicleService.findById(id).ifPresent(vehicleService::delete);

        if (userId.isEmpty()) {
            model.addAttribute("vehicles", vehicleService.findAll());
            return "vehiclelistpage";
        } else {
            return "redirect:/users/" + userId.get();
        }
    }

    /* Json controllers */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Vehicle>> findAll() {
        return ResponseEntity.ok(vehicleService.findAll());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehicle> findById(@PathVariable Long id) {
        return ResponseEntity.of(vehicleService.findById(id));
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehicle> create(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.create(vehicle));
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Vehicle> update(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        return vehicleService.findById(id)
                .map(v -> ResponseEntity.ok(vehicleService.update(vehicle)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            vehicleService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
