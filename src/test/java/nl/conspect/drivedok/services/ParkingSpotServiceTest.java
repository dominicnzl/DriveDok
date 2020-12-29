package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.repositories.ParkingSpotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ParkingSpotServiceTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ParkingSpotRepository parkingSpotRepository;

    ParkingSpotService parkingSpotService;

    @BeforeEach
    public void init() {
        parkingSpotService = new ParkingSpotService(parkingSpotRepository);
    }

    @Test
    @DisplayName("Create 3 ParkingSpots and persist. Expect findAll() to return a list with size 3")
    void findAll() {
        ParkingSpot spot1 = new ParkingSpot(ParkingType.DISABLED, 10);
        ParkingSpot spot2 = new ParkingSpot(ParkingType.ELECTRIC, 10);
        ParkingSpot spot3 = new ParkingSpot(ParkingType.NORMAL, 5);
        testEntityManager.persist(spot1);
        testEntityManager.persist(spot2);
        testEntityManager.persist(spot3);
        assertEquals(3, parkingSpotService.findAll().size());
    }

    @Test
    @DisplayName("Persist two ParkingSpots. Expect findById to retrieve the correct ParkingSpot. Verify by checking the ParkingType")
    void findById() {
        ParkingSpot spot1 = new ParkingSpot(ParkingType.ELECTRIC, 5);
        testEntityManager.persist(spot1);
        ParkingSpot spot2 = new ParkingSpot(ParkingType.NORMAL, 55);
        testEntityManager.persist(spot2);

        ParkingType parkingType = parkingSpotService.findById(spot1.getId())
                .map(ParkingSpot::getParkingType)
                .orElse(null);
        assertEquals(ParkingType.ELECTRIC, parkingType);
        assertNotEquals(ParkingType.NORMAL, parkingType);
    }

    @Test
    @DisplayName("Assert initial findAll() to return empty list. Expect subsequent findAll() to be > 0 after create()")
    void create() {
        assertTrue(parkingSpotService.findAll().isEmpty());

        ParkingSpot spot = new ParkingSpot(ParkingType.DISABLED, 2);
        parkingSpotService.create(spot);
        assertEquals(1, parkingSpotService.findAll().size());
    }

    @Test
    @DisplayName("Persist a ParkingSpot with ParkingType Disabled. Retrieve this and set the type to Electric and call update(). Expect the ParkingType to be Electric after subsequent retrieval")
    void update() {
        ParkingSpot spot = new ParkingSpot(ParkingType.DISABLED,5);
        testEntityManager.persist(spot);

        ParkingSpot beforeUpdateSpot = parkingSpotService.findById(spot.getId()).orElse(null);
        assertNotNull(beforeUpdateSpot);
        assertEquals(ParkingType.DISABLED, beforeUpdateSpot.getParkingType());

        beforeUpdateSpot.setParkingType(ParkingType.ELECTRIC);
        parkingSpotService.update(beforeUpdateSpot);
        ParkingSpot afterUpdateSpot = parkingSpotService.findById(spot.getId()).orElse(null);
        assertNotNull(afterUpdateSpot);
        assertEquals(ParkingType.ELECTRIC, afterUpdateSpot.getParkingType());
    }

    @Test
    @DisplayName("Persist 3 ParkingSpots. Delete the second. Expect findAll() to have size 2")
    void deleteById() {
        ParkingSpot spot1 = new ParkingSpot(ParkingType.DISABLED, 5);
        ParkingSpot spot2 = new ParkingSpot(ParkingType.ELECTRIC, 5);
        ParkingSpot spot3 = new ParkingSpot(ParkingType.NORMAL, 55);
        testEntityManager.persist(spot1);
        testEntityManager.persist(spot2);
        testEntityManager.persist(spot3);
        assertEquals(3, parkingSpotService.findAll().size());

        parkingSpotService.deleteById(spot2.getId());
        assertEquals(2, parkingSpotService.findAll().size());
    }
}