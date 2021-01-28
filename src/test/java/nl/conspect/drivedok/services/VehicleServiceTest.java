package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        final var vehicles = List.of(
                new Vehicle("a", "123-456", ParkingType.NORMAL),
                new Vehicle("b", "123-456", ParkingType.ELECTRIC),
                new Vehicle("c", "123-456", ParkingType.DISABLED));
        vehicles.forEach(testEntityManager::persist);
        assertEquals(3, vehicleService.findAll().size());
    }

    @Test
    @DisplayName("Persist two vehicles. Expect findById to retrieve the correct vehicle. Verify by checking the ParkingType")
    void findById() {
        final var vehicle1 = new Vehicle("a", "123-456", ParkingType.NORMAL);
        final var vehicle2 = new Vehicle("b", "abc-def", ParkingType.ELECTRIC);
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
        vehicleService.create(new Vehicle("a", "123-456", ParkingType.NORMAL));
        assertEquals(1, vehicleService.findAll().size());
    }

    @Test
    @DisplayName("Persist a vehicle with ParkingType Disabled. Retrieve this and set the type to Electric and call update(). Expect the ParkingType to be Electric after subsequent retrieval")
    void update() {
        final var vehicle = new Vehicle("a", "123-456", ParkingType.DISABLED);
        testEntityManager.persist(vehicle);

        final var beforeUpdate = vehicleService.findById(vehicle.getId()).orElse(null);
        assertNotNull(beforeUpdate);
        assertEquals(ParkingType.DISABLED, beforeUpdate.getParkingType());

        beforeUpdate.setParkingType(ParkingType.ELECTRIC);
        vehicleService.update(beforeUpdate);
        final var afterUpdate = vehicleService.findById(vehicle.getId()).orElse(null);
        assertNotNull(afterUpdate);
        assertEquals(ParkingType.ELECTRIC, vehicleService.findById(vehicle.getId()).map(Vehicle::getParkingType).orElse(null));
    }

    @Test
    @DisplayName("Persist 3 vehicles. Delete the second. Expect findAll() to have size 2")
    void deleteById() {
        final var vehicle1 = new Vehicle("a", "123-456", ParkingType.ELECTRIC);
        final var vehicle2 = new Vehicle("b", "234-567", ParkingType.ELECTRIC);
        final var vehicle3 = new Vehicle("c", "345-678", ParkingType.ELECTRIC);
        final var vehicles = List.of(vehicle1, vehicle2, vehicle3);
        vehicles.forEach(testEntityManager::persist);
        assertEquals(3, vehicleService.findAll().size());

        vehicleService.deleteById(vehicle2.getId());
        assertEquals(2, vehicleService.findAll().size());
    }

    @Test
    @DisplayName("Persist a vehicle. Delete it, then expect findAll() to be empty")
    void delete() {
        final var vehicle = new Vehicle("d", "456-789", ParkingType.DISABLED);
        testEntityManager.persist(vehicle);
        assertEquals(1, vehicleService.findAll().size());

        vehicleService.delete(vehicle);
        assertTrue(vehicleService.findAll().isEmpty());
    }

    @Test
    @DisplayName("Controller should redirect to /vehicles if no vehicleId is passed")
    void pageAfterDeleteWhenNoVehicleId() {
        assertEquals("redirect:/vehicles", vehicleService.pageAfterDelete(null));
    }

    @Test
    @DisplayName("Persist a single User and his Vehicle. Expect pageAfterDelete to redirect to the editpage for that User")
    void pageAfterDeleteWhenUserFound() {
        final var vehicle = new Vehicle("e", "567-890", ParkingType.NORMAL);
        final var user = new User("Fred", "fred@email.nl", "ww123");
        user.addVehicle(vehicle);
        final var id = testEntityManager.persistAndGetId(user).toString();
        assertEquals("redirect:/users/".concat(id), vehicleService.pageAfterDelete(vehicle.getId()));
    }

    @Test
    @DisplayName("Persist a vehicle without a User. Expect pageAfterDelete to redirect to /vehicles")
    void pageAfterDeleteWhenUserNotFound() {
        final var vehicle = new Vehicle("f", "678-901", ParkingType.DISABLED);
        testEntityManager.persist(vehicle);
        assertNull(vehicle.getUser());
        assertEquals("redirect:/vehicles", vehicleService.pageAfterDelete(vehicle.getId()));
    }
}
