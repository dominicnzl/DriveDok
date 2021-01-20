package nl.conspect.drivedok.utilities;


import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.model.Zone;
import nl.conspect.drivedok.services.ParkingSpotService;
import nl.conspect.drivedok.services.UserService;
import nl.conspect.drivedok.services.ZoneServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DriveDokEventListeners implements ApplicationListener<ContextRefreshedEvent> {

    private final ZoneServiceImpl zoneService;
    private final ParkingSpotService parkingSpotService;
    private final UserService userService;

    public DriveDokEventListeners(ZoneServiceImpl zoneService, ParkingSpotService parkingSpotService, UserService userService) {
        this.zoneService = zoneService;
        this.parkingSpotService = parkingSpotService;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createDummyZones();
        createDummyUsersAndVehicles();
    }

    private void createDummyZones() {
        var greet = new Zone("Greet",  10);
        var piet = new Zone("Piet", 20);
        var joop = new Zone("Joop", 30);
        zoneService.create(greet);
        zoneService.create(piet);
        zoneService.create(joop);
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
        userService.save(sjaak);
        var pien = new User("Pien", "pien@email.nl", "zomer2020");
        piensVehicles.forEach(pien::addVehicle);
        userService.save(pien);
    }
}
