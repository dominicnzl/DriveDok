package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.exceptions.VehicleNotFoundException;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.services.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping
    public String createEditpage(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        return "vehicleeditpage";
    }

    @GetMapping("/{id}")
    public String editpage(Model model, @PathVariable Long id) {
        final var vehicle = vehicleService.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
        model.addAttribute("vehicle", vehicle);
        return "vehicleeditpage";
    }

    @DeleteMapping("/{id}")
    public String deleteAndReturnToListPage(@PathVariable Long id) {
        final var returnpage = vehicleService.pageAfterDelete(id);
        vehicleService.findById(id).ifPresent(vehicleService::delete);
        return returnpage;
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
