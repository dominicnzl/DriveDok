package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.services.ParkingZoneService;
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
    public Collection<ParkingZone> findAllParkingZones(){
        return parkingZoneService.findAllParkingZones();
    }

    @PostMapping("/create")
    public ParkingZone createParkingZone(@RequestBody ParkingZone parkingZone){
        return parkingZoneService.createParkingZone(parkingZone);
    }

}
