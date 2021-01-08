package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.repositories.ParkingZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        @DisplayName("Assert size of list is 0. Create a new parkingzone and expect list to be size 3")
        void create() {
            assertEquals(0, parkingZoneService.findAll().size());
            ParkingSpot spot2 = new ParkingSpot();
            Set<ParkingSpot> spotSet = new HashSet<>();
            spotSet.add(spot2);
            ParkingZone zone = new ParkingZone("Elsa", spotSet, 100);
            ParkingZone parkingZone = parkingZoneService.create(zone);
            assertEquals(1, parkingZoneService.findAll().size());

        }

        @Test
        @DisplayName("The ParkingZone should be correctly updated")
        void update() {
            ParkingSpot spot2 = new ParkingSpot();
            Set<ParkingSpot> spotSet = new HashSet<>();
            spotSet.add(spot2);
            ParkingZone zone = new ParkingZone("Elsa", spotSet, 100);
            ParkingZone beforeUpdateZone = parkingZoneService.create(zone);

            assertNotNull(beforeUpdateZone);
            assertEquals("Elsa", beforeUpdateZone.getName());

            beforeUpdateZone.setName("Anna");
            parkingZoneService.update(beforeUpdateZone);
            ParkingZone afterUpdateZone = parkingZoneService.findById(beforeUpdateZone.getId()).orElse(null);
            assertNotNull(afterUpdateZone);
            assertEquals("Anna", afterUpdateZone.getName());
        }

        @Test
        @DisplayName("Delete a parkingzone. Expect findAll() to have size 1")
        void deleteById() {
            ParkingSpot spot2 = new ParkingSpot();
            Set<ParkingSpot> spotSet = new HashSet<>();
            spotSet.add(spot2);
            ParkingZone zone = new ParkingZone("Elsa", spotSet, 100);
            ParkingZone zone1 = parkingZoneService.create(zone);
            assertEquals(1, parkingZoneService.findAll().size());
            parkingZoneService.deleteById(zone1.getId());
            testEntityManager.flush();
            testEntityManager.clear();
            assertEquals(0, parkingZoneService.findAll().size());
        }

    }



