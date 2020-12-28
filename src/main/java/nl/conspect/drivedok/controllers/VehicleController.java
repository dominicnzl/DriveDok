package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.services.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> findAll() {
        return ResponseEntity.ok(vehicleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findById(@PathVariable Long id) {
        return ResponseEntity.of(vehicleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Vehicle> create(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.create(vehicle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        return vehicleService.findById(id)
                .map(v -> ResponseEntity.ok(vehicleService.update(vehicle)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            vehicleService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
