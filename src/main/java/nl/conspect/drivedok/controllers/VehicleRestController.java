package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.model.VehicleDto;
import nl.conspect.drivedok.services.VehicleService;
import nl.conspect.drivedok.utilities.VehicleMapper;
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

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleRestController {

    private final VehicleService vehicleService;

    private final VehicleMapper mapper;

    public VehicleRestController(VehicleService vehicleService, VehicleMapper mapper) {
        this.vehicleService = vehicleService;
        this.mapper = mapper;
    }

    private boolean isVehicleFound(Long id) {
        return vehicleService.findById(id).isPresent();
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> findAll() {
        return ok(vehicleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findById(@PathVariable Long id) {
        return of(vehicleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Vehicle> create(@RequestBody VehicleDto dto) {
        var entity = vehicleService.save(mapper.dtoToVehicle(dto));
        var location = URI.create("/api/vehicles/" + entity.getId());
        return created(location).body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(@PathVariable Long id, @RequestBody VehicleDto dto) {
        if (!isVehicleFound(id)) {
            return notFound().build();
        }
        var vehicle = mapper.dtoToVehicle(dto);
        vehicle.setId(id);
        return ok(vehicleService.save(vehicle));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Vehicle> updatePartially(@PathVariable Long id, @RequestBody VehicleDto dto) {
        return vehicleService.findById(id)
                .map(e -> mapper.patchDtoToVehicle(dto, e))
                .map(vehicleService::save)
                .map(ResponseEntity::ok)
                .orElseGet(() -> noContent().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return vehicleService.findById(id)
                .map(entity -> {
                    vehicleService.delete(entity);
                    return noContent().<Void>build();
                })
                .orElseGet(() -> notFound().build());
    }
}
