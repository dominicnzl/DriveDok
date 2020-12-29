package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.repositories.ParkingZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

    @DataJpaTest
    class ParkingZoneServiceTest {

        @Autowired
        TestEntityManager testEntityManager;

        @Autowired
        ParkingZoneRepository parkingZoneRepository;

        ParkingZoneService parkingZoneService;

        ParkingSpotService parkingSpotService;

        @BeforeEach
        public void init() {
            parkingZoneService = new ParkingZoneService(parkingZoneRepository, parkingSpotService);
        }

        @Test
        @DisplayName("Create 3 ParkingZones and persist. Expect findAll() to return a list with size 3")
        void findAll() {
            ParkingZone spot1 = new ParkingZone();
            ParkingZone spot2 = new ParkingZone();
            ParkingZone spot3 = new ParkingZone();
            testEntityManager.persist(spot1);
            testEntityManager.persist(spot2);
            testEntityManager.persist(spot3);
            assertEquals(3, parkingZoneService.findAll().size());
        }

        @Test
        @DisplayName("Persist two ParkingZones. Expect findById to retrieve the correct ParkingZone. Verify by checking the name")
        void findById() {
            ParkingZone zone1 = new ParkingZone(1L, "Elsa", null, 100);
            testEntityManager.persist(zone1);
            ParkingZone zone2 = new ParkingZone(2L, "Anna", null, 300);
            testEntityManager.persist(zone2);

            String  name = parkingZoneService.findById(zone1.getId())
                    .map(ParkingZone::getName)
                    .orElse(null);
            assertEquals("Elsa", name);
            assertNotEquals("Anna", name);
        }

        @Test
        @DisplayName("Assert initial findAll() to return empty list. Expect subsequent findAll() to be > 0 after create()")
        void create() {
            assertTrue(parkingZoneService.findAll().isEmpty());

            ParkingZone zone = new ParkingZone(1L, "Elsa", null, 100);
            parkingZoneService.create(zone);
            assertEquals(1, parkingZoneService.findAll().size());
        }

        @Test
        @DisplayName("The ParkingZone should be correctly updated")
        void update() {
            ParkingZone zone = new ParkingZone(1L, "Elsa", null, 100);
            testEntityManager.persist(zone);

            ParkingZone beforeUpdateZone = parkingZoneService.findById(zone.getId()).orElse(null);
            assertNotNull(beforeUpdateZone);
            assertEquals("Elsa", beforeUpdateZone.getName());

            beforeUpdateZone.setName("Anna");
            parkingZoneService.update(beforeUpdateZone);
            ParkingZone afterUpdateSpot = parkingZoneService.findById(zone.getId()).orElse(null);
            assertNotNull(afterUpdateSpot);
            assertEquals("Anna", afterUpdateSpot.getName());
        }

        @Test
        @DisplayName("Persist 3 ParkingZones. Delete the second. Expect findAll() to have size 2")
        void deleteById() {
            ParkingZone spot1 = new ParkingZone();
            ParkingZone spot2 = new ParkingZone();
            ParkingZone spot3 = new ParkingZone();
            testEntityManager.persist(spot1);
            testEntityManager.persist(spot2);
            testEntityManager.persist(spot3);
            assertEquals(3, parkingZoneService.findAll().size());

            parkingZoneService.deleteById(spot2.getId());
            assertEquals(2, parkingZoneService.findAll().size());
        }
    }



