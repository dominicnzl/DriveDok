package nl.conspect.drivedok.utilities;


import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.model.Zone;
import nl.conspect.drivedok.services.BasicReservationService;
import nl.conspect.drivedok.services.UserService;
import nl.conspect.drivedok.services.ZoneServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Set;

@Component
public class DriveDokEventListeners implements ApplicationListener<ContextRefreshedEvent> {

    private final ZoneServiceImpl zoneService;
    private final UserService userService;
    private final BasicReservationService reservationService;

    private User sjaak;
    private Vehicle autoVanDeSjaak;
    private Vehicle electrischeAuto;

    public DriveDokEventListeners(ZoneServiceImpl zoneService, UserService userService, BasicReservationService reservationService) {
        this.zoneService = zoneService;
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createDummyZones();
        createDummyUsersAndVehicles();
        createDummyReservations();
    }

    private void createDummyZones() {
        var noord = new Zone("Noord", 10);
        var zuid = new Zone("Zuid", 20);
        var west = new Zone("West", 30);
        zoneService.create(noord);
        zoneService.create(zuid);
        zoneService.create(west);
    }

    private void createDummyUsersAndVehicles() {
        var sjaaksVehicles = Set.of(
                autoVanDeSjaak(),
                electrischeAuto(),
                new Vehicle("Zonnepaneel fiets", "H-002-D", ParkingType.DISABLED)
        );
        var piensVehicles = Set.of(
                new Vehicle("Volkswagen Pino", "H-000-E", ParkingType.ELECTRIC),
                new Vehicle("Motor", "H-001-F", ParkingType.NORMAL)
        );
        sjaaksVehicles.forEach(sjaak()::addVehicle);
        userService.save(sjaak());
        var pien = new User("Pien", "pien@email.nl", "zomer2020");
        piensVehicles.forEach(pien::addVehicle);
        userService.save(pien);
    }

    private User sjaak() {
        if (null == sjaak) {
            sjaak = new User("Sjaak", "sjaak@email.nl", "password123");
        }
        return sjaak;
    }

    private Vehicle autoVanDeSjaak() {
        if (null == autoVanDeSjaak) {
            autoVanDeSjaak = new Vehicle("Auto van de Sjaak", "H-000-B", ParkingType.NORMAL);
        }
        return autoVanDeSjaak;
    }

    private Vehicle electrischeAuto() {
        if (null == electrischeAuto) {
            electrischeAuto = new Vehicle("Electrische auto", "H-001-C", ParkingType.ELECTRIC);
        }
        return electrischeAuto;
    }

    private void createDummyReservations() {
        var reservation = new Reservation(
                LocalDateTime.of(2021, Month.JANUARY, 1, 8, 0),
                LocalDateTime.of(2021, Month.DECEMBER, 31, 17, 0),
                sjaak(),
                autoVanDeSjaak(),
                null);
        var otherReservation = new Reservation(
                LocalDateTime.of(2021, Month.APRIL, 1, 8, 0),
                LocalDateTime.of(2021, Month.APRIL, 1, 18, 0),
                sjaak(),
                electrischeAuto(),
                null);
        reservationService.save(reservation);
        reservationService.save(otherReservation);
    }
}
