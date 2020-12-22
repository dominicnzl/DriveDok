package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.DriveDokSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.repositories.DriveDokSpotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DriveDokSpotServiceTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    DriveDokSpotRepository driveDokSpotRepository;

    DriveDokSpotService driveDokSpotService;

    @BeforeEach
    public void init() {
        driveDokSpotService = new DriveDokSpotService(driveDokSpotRepository);
    }

    @Test
    @DisplayName("Create 3 DriveDokSpots and persist. Expect findAll() to return a list with size 3")
    void findAll() {
        DriveDokSpot spot1 = new DriveDokSpot(ParkingType.DISABLED);
        DriveDokSpot spot2 = new DriveDokSpot(ParkingType.ELECTRIC);
        DriveDokSpot spot3 = new DriveDokSpot(ParkingType.MOTOR);
        testEntityManager.persist(spot1);
        testEntityManager.persist(spot2);
        testEntityManager.persist(spot3);
        assertEquals(3, driveDokSpotService.findAll().size());
    }

    @Test
    @DisplayName("Persist two DriveDokSpots. Expect findById to retrieve the correct DriveDokSpot. Verify by checking the ParkingType")
    void findById() {
        DriveDokSpot spot1 = new DriveDokSpot(ParkingType.ELECTRIC);
        testEntityManager.persist(spot1);
        DriveDokSpot spot2 = new DriveDokSpot(ParkingType.MOTOR);
        testEntityManager.persist(spot2);

        ParkingType parkingType = driveDokSpotService.findById(spot1.getId())
                .map(DriveDokSpot::getParkingType)
                .orElse(null);
        assertEquals(ParkingType.ELECTRIC, parkingType);
        assertNotEquals(ParkingType.MOTOR, parkingType);
    }

    @Test
    @DisplayName("Assert initial findAll() to return empty list. Expect subsequent findAll() to be > 0 after create()")
    void create() {
        assertTrue(driveDokSpotService.findAll().isEmpty());

        DriveDokSpot spot = new DriveDokSpot(ParkingType.DISABLED);
        driveDokSpotService.create(spot);
        assertEquals(1, driveDokSpotService.findAll().size());
    }

    @Test
    @DisplayName("Persist a DriveDokSpot with ParkingType Disabled. Retrieve this and set the type to Electric and call update(). Expect the ParkingType to be Electric after subsequent retrieval")
    void update() {
        DriveDokSpot spot = new DriveDokSpot(ParkingType.DISABLED);
        testEntityManager.persist(spot);

        DriveDokSpot updatedSpot = driveDokSpotService.findById(spot.getId()).orElse(null);
        assertNotNull(updatedSpot);
        updatedSpot.setParkingType(ParkingType.ELECTRIC);
        driveDokSpotService.update(updatedSpot);

        DriveDokSpot checkSpot = driveDokSpotService.findById(spot.getId()).orElse(null);
        assertNotNull(checkSpot);
        assertEquals(ParkingType.ELECTRIC, checkSpot.getParkingType());
    }



    @Test
    @DisplayName("Persist 3 DriveDokSpots. Delete the second. Expect findAll() to have size 2")
    void deleteById() {
        DriveDokSpot spot1 = new DriveDokSpot(ParkingType.DISABLED);
        DriveDokSpot spot2 = new DriveDokSpot(ParkingType.ELECTRIC);
        DriveDokSpot spot3 = new DriveDokSpot(ParkingType.MOTOR);
        testEntityManager.persist(spot1);
        testEntityManager.persist(spot2);
        testEntityManager.persist(spot3);
        assertEquals(3, driveDokSpotService.findAll().size());

        driveDokSpotService.deleteById(spot2.getId());
        assertEquals(2, driveDokSpotService.findAll().size());
    }
}