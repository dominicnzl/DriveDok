package nl.conspect.drivedok.utilities;


import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.services.ParkingSpotService;
import nl.conspect.drivedok.services.ParkingZoneService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DriveDokEventListeners implements ApplicationListener<ContextRefreshedEvent> {

    private final ParkingZoneService parkingZoneService;

    private final ParkingSpotService parkingSpotService;

    public DriveDokEventListeners(ParkingZoneService parkingZoneService, ParkingSpotService parkingSpotService) {
        this.parkingZoneService = parkingZoneService;
        this.parkingSpotService = parkingSpotService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        var greet = new ParkingZone("Greet", Collections.emptySet(), 10);
        var piet = new ParkingZone("Piet", Collections.emptySet(), 20);
        var joop = new ParkingZone("Joop", Collections.emptySet(), 30);
        parkingZoneService.create(greet);
        parkingZoneService.create(piet);
        parkingZoneService.create(joop);

        var plek1 = new ParkingSpot();
        plek1.setParkingType(ParkingType.DISABLED);
        var plek2 = new ParkingSpot();
        plek2.setParkingType(ParkingType.ELECTRIC);
        parkingSpotService.create(plek1);
        parkingSpotService.create(plek2);
    }
}
