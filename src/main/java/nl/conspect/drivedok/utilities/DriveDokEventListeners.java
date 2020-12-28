package nl.conspect.drivedok.utilities;


import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.services.ParkingSpotService;
import nl.conspect.drivedok.services.ParkingZoneService;
import nl.conspect.drivedok.services.VehicleService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DriveDokEventListeners implements ApplicationListener<ContextRefreshedEvent> {

    private final ParkingZoneService parkingZoneService;
    private final ParkingSpotService parkingSpotService;
    private final VehicleService vehicleService;

    public DriveDokEventListeners(ParkingZoneService parkingZoneService, ParkingSpotService parkingSpotService, VehicleService vehicleService) {
        this.parkingZoneService = parkingZoneService;
        this.parkingSpotService = parkingSpotService;
        this.vehicleService = vehicleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createDummyParkingZones();
        createDummyParkingSpots();
        createDummyVehicles();
    }

    private void createDummyParkingZones() {
        var greet = new ParkingZone("Greet", Collections.emptySet(), 10);
        var piet = new ParkingZone("Piet", Collections.emptySet(), 20);
        var joop = new ParkingZone("Joop", Collections.emptySet(), 30);
        parkingZoneService.create(greet);
        parkingZoneService.create(piet);
        parkingZoneService.create(joop);
    }

    private void createDummyParkingSpots() {
        var plek1 = new ParkingSpot();
        plek1.setParkingType(ParkingType.DISABLED);
        var plek2 = new ParkingSpot();
        plek2.setParkingType(ParkingType.ELECTRIC);
        parkingSpotService.create(plek1);
        parkingSpotService.create(plek2);
    }

    void createDummyVehicles() {
        var vehicles = List.of(
                new Vehicle("Ben's iene miene mutte auto", "H-000-B", ParkingType.NORMAL, Collections.emptySet()),
                new Vehicle("Tess's sla", "H-001-C", ParkingType.ELECTRIC, Collections.emptySet()),
                new Vehicle("Zo Fiets", "H-002-D", ParkingType.NORMAL, Collections.emptySet())
        );
        vehicles.forEach(vehicleService::create);
    }
}
