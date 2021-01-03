package nl.conspect.drivedok.utilities;


import nl.conspect.drivedok.model.*;
import nl.conspect.drivedok.services.ParkingSpotService;
import nl.conspect.drivedok.services.ParkingZoneService;
import nl.conspect.drivedok.services.UserService;
import nl.conspect.drivedok.services.VehicleService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
public class DriveDokEventListeners implements ApplicationListener<ContextRefreshedEvent> {

    private final ParkingZoneService parkingZoneService;
    private final ParkingSpotService parkingSpotService;
    private final VehicleService vehicleService;
    private final UserService userService;

    Set<Vehicle> sjaaksVehicles;
    Set<Vehicle> piensVehicles;

    public DriveDokEventListeners(ParkingZoneService parkingZoneService,
                                  ParkingSpotService parkingSpotService,
                                  VehicleService vehicleService,
                                  UserService userService) {
        this.parkingZoneService = parkingZoneService;
        this.parkingSpotService = parkingSpotService;
        this.vehicleService = vehicleService;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createDummyParkingZones();
        createDummyParkingSpots();
        createDummyVehicles();
        createDummyUser();
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

    private void createDummyVehicles() {
        sjaaksVehicles = Set.of(
                new Vehicle("Auto van de Sjaak", "H-000-B", ParkingType.NORMAL, Collections.emptySet()),
                new Vehicle("Electrische auto", "H-001-C", ParkingType.ELECTRIC, Collections.emptySet()),
                new Vehicle("Zonnepaneel fiets", "H-002-D", ParkingType.DISABLED, Collections.emptySet())
        );
        piensVehicles = Set.of(
                new Vehicle("Volkswagen Pino", "H-000-E", ParkingType.ELECTRIC, Collections.emptySet()),
                new Vehicle("Motor", "H-001-F", ParkingType.NORMAL, Collections.emptySet())
        );
        sjaaksVehicles.forEach(vehicleService::create);
        piensVehicles.forEach(vehicleService::create);
    }

    private void createDummyUser() {
        var user1 = new User("Sjaak", "sjaak@email.nl", "password123", sjaaksVehicles);
        userService.create(user1);
        var user2 = new User("Pien", "pien@email.nl", "zomer2020", piensVehicles);
        userService.create(user2);
    }
}
