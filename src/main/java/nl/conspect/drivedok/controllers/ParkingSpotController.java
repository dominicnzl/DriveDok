package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.services.ParkingSpotService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("parkingspots")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpot>> findAll() {
        return ResponseEntity.ok(parkingSpotService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpot> findById(@PathVariable Long id) {
        return ResponseEntity.of(parkingSpotService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ParkingSpot> create(@RequestBody ParkingSpot parkingSpot) {
        return ResponseEntity.ok(parkingSpotService.create(parkingSpot));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpot> update(@PathVariable Long id, @RequestBody ParkingSpot newParkingSpot) {
        return parkingSpotService.findById(id)
                .map(spot -> ResponseEntity.ok(parkingSpotService.update(newParkingSpot)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            parkingSpotService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
