package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.services.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleRestController {

    private final VehicleService vehicleService;

    public VehicleRestController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    private boolean isVehicleFound(Long id) {
        return vehicleService.findById(id).isPresent();
    }

    @GetMapping
    public ResponseEntity<VehicleList> findAll() {
        var vehicles = new VehicleList(vehicleService.findAll());
        return ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findById(@PathVariable Long id) {
        return of(vehicleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Vehicle> create(@RequestBody Vehicle vehicle, UriComponentsBuilder builder) {
        var createdVehicle = vehicleService.save(vehicle);
        var uri = builder.path("/api/vehicles/{id}").buildAndExpand(createdVehicle.getId()).toUri();
        return created(uri).body(createdVehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(@PathVariable Long id, @RequestBody Vehicle newVehicle) {
        return isVehicleFound(id) ? ok(vehicleService.update(id, newVehicle)) : notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Vehicle> updatePartially(@PathVariable Long id, @RequestBody Map<String, String> properties) {
        return isVehicleFound(id) ? ok(vehicleService.updatePartially(id, properties)) : notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (isVehicleFound(id)) {
            vehicleService.deleteById(id);
            return noContent().build();
        }
        return notFound().build();
    }

    static class VehicleList {
        private final List<Vehicle> vehicles;

        public VehicleList(List<Vehicle> vehicles) {
            this.vehicles = vehicles;
        }

        public final List<Vehicle> getVehicles() {
            return vehicles;
        }
    }
}
