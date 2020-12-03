package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.services.ParkingZoneService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("ParkingZoneController")
public class ParkingZoneController {


    private final ParkingZoneService parkingZoneService;

    public ParkingZoneController(ParkingZoneService parkingZoneService) {
        this.parkingZoneService = parkingZoneService;
    }

    @PostMapping("/createParkingZone")
    public ParkingZone createParkingZone(@RequestBody ParkingZone parkingZone){
        return parkingZoneService.createParkingZone(parkingZone);
    }
}
