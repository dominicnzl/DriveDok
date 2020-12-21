package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.DriveDokSpot;
import nl.conspect.drivedok.services.DriveDokSpotService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("drivedokspots")
public class DriveDokSpotController {

    private final DriveDokSpotService driveDokSpotService;

    public DriveDokSpotController(DriveDokSpotService driveDokSpotService) {
        this.driveDokSpotService = driveDokSpotService;
    }

    @GetMapping
    public ResponseEntity<List<DriveDokSpot>> findAll() {
        return ResponseEntity.ok(driveDokSpotService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriveDokSpot> findById(@PathVariable Long id) {
        return ResponseEntity.of(driveDokSpotService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DriveDokSpot> create(@RequestBody DriveDokSpot driveDokSpot) {
        return ResponseEntity.ok(driveDokSpotService.create(driveDokSpot));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriveDokSpot> update(@PathVariable Long id, @RequestBody DriveDokSpot newDriveDokSpot) {
        return driveDokSpotService.findById(id)
                .map(driveDokSpot -> ResponseEntity.ok(driveDokSpotService.update(newDriveDokSpot)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            driveDokSpotService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
