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

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleRestController {

    private final VehicleService service;

    private final VehicleMapper mapper;

    public VehicleRestController(VehicleService service, VehicleMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    private boolean isVehicleFound(Long id) {
        return service.findById(id).isPresent();
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> findAll() {
        return ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findById(@PathVariable Long id) {
        return of(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Vehicle> create(@RequestBody VehicleDto dto) {
        return status(CREATED).body(service.save(mapper.dtoToVehicle(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(@PathVariable Long id, @RequestBody VehicleDto dto) {
        if (!isVehicleFound(id)) {
            return notFound().build();
        }
        var vehicle = mapper.dtoToVehicle(dto);
        vehicle.setId(id);
        return ok(service.save(vehicle));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Vehicle> updatePartially(@PathVariable Long id, @RequestBody VehicleDto dto) {
        return service.findById(id)
                .map(e -> mapper.patchDtoToVehicle(dto, e))
                .map(service::save)
                .map(ResponseEntity::ok)
                .orElseGet(() -> noContent().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (isVehicleFound(id)) {
            service.deleteById(id);
            return noContent().build();
        }
        return notFound().build();
    }
}
