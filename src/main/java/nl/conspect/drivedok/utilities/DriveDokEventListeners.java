package nl.conspect.drivedok.utilities;


import nl.conspect.drivedok.model.DriveDokSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.services.DriveDokSpotService;
import nl.conspect.drivedok.services.ParkingZoneService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DriveDokEventListeners implements ApplicationListener<ContextRefreshedEvent> {

    private final ParkingZoneService parkingZoneService;

    private final DriveDokSpotService driveDokSpotService;

    public DriveDokEventListeners(ParkingZoneService parkingZoneService, DriveDokSpotService driveDokSpotService) {
        this.parkingZoneService = parkingZoneService;
        this.driveDokSpotService = driveDokSpotService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        var greet = new ParkingZone("Greet", Collections.emptySet(), 10);
        var piet = new ParkingZone("Piet", Collections.emptySet(), 20);
        var joop = new ParkingZone("Joop", Collections.emptySet(), 30);
        parkingZoneService.create(greet);
        parkingZoneService.create(piet);
        parkingZoneService.create(joop);

        var plek1 = new DriveDokSpot();
        plek1.setParkingType(ParkingType.DISABLED);
        var plek2 = new DriveDokSpot();
        plek2.setParkingType(ParkingType.ELECTRIC);
        driveDokSpotService.create(plek1);
        driveDokSpotService.create(plek2);
    }
}
