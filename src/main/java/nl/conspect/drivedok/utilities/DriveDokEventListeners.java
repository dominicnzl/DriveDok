package nl.conspect.drivedok.utilities;


import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.model.Zone;
import nl.conspect.drivedok.services.ParkingSpotService;
import nl.conspect.drivedok.services.VehicleService;
import nl.conspect.drivedok.services.ZoneServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DriveDokEventListeners implements ApplicationListener<ContextRefreshedEvent> {

    private final ZoneServiceImpl zoneService;
    private final ParkingSpotService parkingSpotService;
    private final VehicleService vehicleService;

    public DriveDokEventListeners(ZoneServiceImpl zoneService, ParkingSpotService parkingSpotService, VehicleService vehicleService) {
        this.zoneService = zoneService;
        this.parkingSpotService = parkingSpotService;
        this.vehicleService = vehicleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        createDummyZones();
//        createDummyParkingSpots();
//        createDummyVehicles();
    }

    private void createDummyZones() {
        var greet = new Zone("Greet", Collections.emptySet(), 10);
        var piet = new Zone("Piet", Collections.emptySet(), 20);
        var joop = new Zone("Joop", Collections.emptySet(), 30);
        zoneService.create(greet);
        zoneService.create(piet);
        zoneService.create(joop);
    }

    private void createDummyParkingSpots() {
        var plek1 = new ParkingSpot(ParkingType.DISABLED, 0);
        var plek2 = new ParkingSpot(ParkingType.ELECTRIC, 0);
        var plek3 = new ParkingSpot(ParkingType.NORMAL, 12);
        parkingSpotService.create(plek1);
        parkingSpotService.create(plek2);
        parkingSpotService.create(plek3);
    }

    private void createDummyVehicles() {
        var vehicles = List.of(
                new Vehicle("Ben's iene miene mutte auto", "H-000-B", ParkingType.NORMAL, Collections.emptySet()),
                new Vehicle("Tess's sla", "H-001-C", ParkingType.ELECTRIC, Collections.emptySet()),
                new Vehicle("Zo Fiets", "H-002-D", ParkingType.NORMAL, Collections.emptySet())
        );
        vehicles.forEach(vehicleService::create);
    }
}
