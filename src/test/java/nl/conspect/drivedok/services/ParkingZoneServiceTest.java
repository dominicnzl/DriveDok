package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.repositories.ParkingZoneRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

    @DataJpaTest
    @Transactional
    class ParkingZoneServiceTest {

        @Autowired
        TestEntityManager testEntityManager;

        @Autowired
        ParkingZoneService parkingZoneService;


        @TestConfiguration
        static class ParkingZoneServiceImplTestContextConfiguration {

            @Autowired
            ParkingZoneRepository parkingZoneRepository;

            @Bean
            public ParkingZoneService parkingZoneService() {
                return new ParkingZoneServiceImpl(parkingZoneRepository);
            }
        }

        @BeforeEach
        public void init() {

            /*ParkingSpot spot1 = new ParkingSpot();
            ParkingSpot spot2 = new ParkingSpot();
            ParkingSpot spot3 = new ParkingSpot();
            ParkingZone zone1 = new ParkingZone("Elsa", Collections.emptySet(), 100);
            ParkingZone zone2 = new ParkingZone("Anna", Collections.emptySet(), 300);
            testEntityManager.persist(spot1);
            testEntityManager.persist(spot2);
            testEntityManager.persist(spot3);
            testEntityManager.persist(zone1);
            var o = testEntityManager.persistAndGetId(zone2, Long.class);
            testEntityManager.flush();*/
        }

        @Test
        @DisplayName("Expect findAll() to return a list with size 2")
        void findAll() {
            assertEquals(0, parkingZoneService.findAll().size());
            ParkingZone zone1 = new ParkingZone("Elsa", Collections.emptySet(), 100);
            testEntityManager.persist(zone1);
            assertEquals(1, parkingZoneService.findAll().size());

        }

        @Test
        @DisplayName("Expect findById to retrieve the correct ParkingZone. Verify by checking the name")
        void findById() {
            ParkingZone zone2 = new ParkingZone("Anna", Collections.emptySet(), 300);
            var id = testEntityManager.persistAndGetId(zone2, Long.class);
            String  name = parkingZoneService.findById(id)
                    .map(ParkingZone::getName)
                    .orElse(null);
            assertEquals("Anna", name);
        }

        @Test
        @DisplayName("Assert size of list is 2. Create a new parkingzone and expect list to be size 3")
        void create() {
            assertEquals(0, parkingZoneService.findAll().size());
            ParkingSpot spot2 = new ParkingSpot();
            ParkingZone zone = new ParkingZone("Elsa", Collections.emptySet(), 100);
            zone.addParkingSpot(spot2);
            ParkingZone parkingZone = parkingZoneService.create(zone);
            assertEquals(1, parkingZoneService.findAll().size());

        }

        @Test
        @DisplayName("The ParkingZone should be correctly updated")
        void update() {

            ParkingZone beforeUpdateZone = parkingZoneService.findById(4L).orElse(null);
            assertNotNull(beforeUpdateZone);
            assertEquals("Elsa", beforeUpdateZone.getName());

            beforeUpdateZone.setName("Anna");
            parkingZoneService.update(beforeUpdateZone);
            ParkingZone afterUpdateZone = parkingZoneService.findById(4L).orElse(null);
            assertNotNull(afterUpdateZone);
            assertEquals("Anna", afterUpdateZone.getName());
        }

        @Test
        @DisplayName("Delete a parkingzone. Expect findAll() to have size 1")
        void deleteById() {
            assertEquals(2, parkingZoneService.findAll().size());
            parkingZoneService.deleteById(4L);
            testEntityManager.flush();
            testEntityManager.clear();
            assertEquals(1, parkingZoneService.findAll().size());
        }

    }



