package nl.conspect.drivedok.utilities;


import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.services.ParkingZoneService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DriveDokEventListeners implements ApplicationListener<ContextRefreshedEvent> {

    private final ParkingZoneService parkingZoneService;

    public DriveDokEventListeners(ParkingZoneService parkingZoneService) {
        this.parkingZoneService = parkingZoneService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        var greet = new ParkingZone("Greet", Collections.emptySet(), 10);
        var piet = new ParkingZone("Piet", Collections.emptySet(), 20);
        var joop = new ParkingZone("Joop", Collections.emptySet(), 30);
        parkingZoneService.create(greet);
        parkingZoneService.create(piet);
        parkingZoneService.create(joop);
    }
}
