package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VehicleServiceTest {

    private VehicleService vehicleService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void init() {
        vehicleService = new VehicleService(vehicleRepository);
    }

    @Test
    @DisplayName("Create 3 vehicles and persist. Expect findAll() to return a list with size 3")
    void findAll() {
        var vehicles = List.of(new Vehicle(), new Vehicle(), new Vehicle());
        vehicles.forEach(testEntityManager::persist);
        assertEquals(3, vehicleService.findAll().size());
    }

    @Test
    @DisplayName("Persist two vehicles. Expect findById to retrieve the correct vehicle. Verify by checking the ParkingType")
    void findById() {
        var vehicle1 = new Vehicle("a", "123-456", ParkingType.NORMAL, Collections.emptySet());
        var vehicle2 = new Vehicle("b", "abc-def", ParkingType.ELECTRIC, Collections.emptySet());
        testEntityManager.persist(vehicle1);
        testEntityManager.persist(vehicle2);
        assertEquals(
                ParkingType.ELECTRIC,
                vehicleService.findById(vehicle2.getId()).map(Vehicle::getParkingType).orElse(null));
    }

    @Test
    @DisplayName("Assert initial findAll() to return empty list. Expect subsequent findAll().size() to be exactly 1 after create()")
    void create() {
        assertTrue(vehicleService.findAll().isEmpty());
        vehicleService.create(new Vehicle());
        assertEquals(1, vehicleService.findAll().size());
    }

    @Test
    @DisplayName("Persist a vehicle with ParkingType Disabled. Retrieve this and set the type to Electric and call update(). Expect the ParkingType to be Electric after subsequent retrieval")
    void update() {
        var vehicle = new Vehicle("a", "123-456", ParkingType.DISABLED, Collections.emptySet());
        testEntityManager.persist(vehicle);

        var beforeUpdate = vehicleService.findById(vehicle.getId()).orElse(null);
        assertNotNull(beforeUpdate);
        assertEquals(ParkingType.DISABLED, beforeUpdate.getParkingType());

        beforeUpdate.setParkingType(ParkingType.ELECTRIC);
        vehicleService.update(beforeUpdate);
        var afterUpdate = vehicleService.findById(vehicle.getId()).orElse(null);
        assertNotNull(afterUpdate);
        assertEquals(ParkingType.ELECTRIC, vehicleService.findById(vehicle.getId()).map(Vehicle::getParkingType).orElse(null));
    }

    @Test
    @DisplayName("Persist 3 vehicles. Delete the second. Expect findAll() to have size 2")
    void deleteById() {
        var vehicle1 = new Vehicle("a", "123-456", ParkingType.ELECTRIC, Collections.emptySet());
        var vehicle2 = new Vehicle("b", "234-567", ParkingType.ELECTRIC, Collections.emptySet());
        var vehicle3 = new Vehicle("c", "345-678", ParkingType.ELECTRIC, Collections.emptySet());
        var vehicles = List.of(vehicle1, vehicle2, vehicle3);
        vehicles.forEach(testEntityManager::persist);
        assertEquals(3, vehicleService.findAll().size());

        vehicleService.deleteById(vehicle2.getId());
        assertEquals(2, vehicleService.findAll().size());
    }
}
