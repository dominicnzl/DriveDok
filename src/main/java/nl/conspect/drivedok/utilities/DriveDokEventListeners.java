package nl.conspect.drivedok.utilities;


import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.services.ParkingSpotService;
import nl.conspect.drivedok.services.ParkingZoneServiceImpl;
import nl.conspect.drivedok.services.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
public class DriveDokEventListeners implements ApplicationListener<ContextRefreshedEvent> {

    private final ParkingZoneServiceImpl parkingZoneService;
    private final ParkingSpotService parkingSpotService;
    private final UserService userService;


    public DriveDokEventListeners(ParkingZoneServiceImpl parkingZoneService,
                                  ParkingSpotService parkingSpotService,
                                  UserService userService) {
        this.parkingZoneService = parkingZoneService;
        this.parkingSpotService = parkingSpotService;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//      // createDummyParkingZones();
//      // createDummyParkingSpots();
        createDummyUsersAndVehicles();
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
        var plek1 = new ParkingSpot(ParkingType.DISABLED, 0);
        var plek2 = new ParkingSpot(ParkingType.ELECTRIC, 0);
        var plek3 = new ParkingSpot(ParkingType.NORMAL, 12);
        parkingSpotService.create(plek1);
        parkingSpotService.create(plek2);
        parkingSpotService.create(plek3);
    }

    private void createDummyUsersAndVehicles() {
        var sjaaksVehicles = Set.of(
                new Vehicle("Auto van de Sjaak", "H-000-B", ParkingType.NORMAL),
                new Vehicle("Electrische auto", "H-001-C", ParkingType.ELECTRIC),
                new Vehicle("Zonnepaneel fiets", "H-002-D", ParkingType.DISABLED)
        );
        var piensVehicles = Set.of(
                new Vehicle("Volkswagen Pino", "H-000-E", ParkingType.ELECTRIC),
                new Vehicle("Motor", "H-001-F", ParkingType.NORMAL)
        );
        var sjaak = new User("Sjaak", "sjaak@email.nl", "password123");
        sjaaksVehicles.forEach(sjaak::addVehicle);
        userService.createOrUpdate(sjaak);
        var pien = new User("Pien", "pien@email.nl", "zomer2020");
        piensVehicles.forEach(pien::addVehicle);
        userService.createOrUpdate(pien);
    }
}
