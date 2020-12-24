package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.repositories.ParkingZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
        ParkingZoneRepository ParkingZoneRepository;

        ParkingZoneService ParkingZoneService;

        @BeforeEach
        public void init() {
            ParkingZoneService = new ParkingZoneService(ParkingZoneRepository);
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
            assertEquals(3, ParkingZoneService.findAll().size());
        }

        @Test
        @DisplayName("Persist two ParkingZones. Expect findById to retrieve the correct ParkingZone. Verify by checking the name")
        void findById() {
            ParkingZone zone1 = new ParkingZone(1L, "Elsa", null, 100);
            testEntityManager.persist(zone1);
            ParkingZone zone2 = new ParkingZone(2L, "Anna", null, 300);
            testEntityManager.persist(zone2);

            String  name = ParkingZoneService.findById(zone1.getId())
                    .map(ParkingZone::getName)
                    .orElse(null);
            assertEquals("Elsa", name);
            assertNotEquals("Anna", name);
        }

        @Test
        @DisplayName("Assert initial findAll() to return empty list. Expect subsequent findAll() to be > 0 after create()")
        void create() {
            assertTrue(ParkingZoneService.findAll().isEmpty());

            ParkingZone zone = new ParkingZone(1L, "Elsa", null, 100);
            ParkingZoneService.create(zone);
            assertEquals(1, ParkingZoneService.findAll().size());
        }

        @Test
        @DisplayName("The ParkingZone should be correctly updated")
        void update() {
            ParkingZone zone = new ParkingZone(1L, "Elsa", null, 100);
            testEntityManager.persist(zone);

            ParkingZone beforeUpdateZone = ParkingZoneService.findById(zone.getId()).orElse(null);
            assertNotNull(beforeUpdateZone);
            assertEquals("Elsa", beforeUpdateZone.getName());

            beforeUpdateZone.setName("Anna");
            ParkingZoneService.update(beforeUpdateZone);
            ParkingZone afterUpdateSpot = ParkingZoneService.findById(zone.getId()).orElse(null);
            assertNotNull(afterUpdateSpot);
            assertEquals("Anna", afterUpdateSpot.getName());
        }



        @Disabled // TODO: Deleting ParkingZone will be resolved with #19
        @DisplayName("Persist 3 ParkingZones. Delete the second. Expect findAll() to have size 2")
        void deleteById() {
            ParkingZone spot1 = new ParkingZone();
            ParkingZone spot2 = new ParkingZone();
            ParkingZone spot3 = new ParkingZone();
            testEntityManager.persist(spot1);
            testEntityManager.persist(spot2);
            testEntityManager.persist(spot3);
            assertEquals(3, ParkingZoneService.findAll().size());

           // ParkingZoneService.delete(spot2.getId());
            assertEquals(2, ParkingZoneService.findAll().size());
        }
    }



