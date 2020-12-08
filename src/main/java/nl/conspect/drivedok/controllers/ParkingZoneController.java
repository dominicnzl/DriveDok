package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.services.ParkingZoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/parkingzone")
public class ParkingZoneController {


    private final ParkingZoneService parkingZoneService;

    public ParkingZoneController(ParkingZoneService parkingZoneService) {
        this.parkingZoneService = parkingZoneService;
    }

    @GetMapping("/all")
    public Collection<ParkingZone> findAllParkingZones() {
        return parkingZoneService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingZone> findById(@PathVariable Long id) {
        return parkingZoneService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ParkingZone createParkingZone(@RequestBody ParkingZone parkingZone) {
        return parkingZoneService.create(parkingZone);
    }

    @PutMapping("/update")
    public ParkingZone updateParkingZone(@RequestBody ParkingZone parkingZone) {
        return parkingZoneService.update(parkingZone);
    }
}
