package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.repositories.ParkingZoneRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

    @DataJpaTest
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    class ParkingZoneServiceTest {

        @Autowired
        TestEntityManager testEntityManager;

        @Autowired
        ParkingZoneRepository parkingZoneRepository;

        @MockBean
        ParkingZoneService parkingZoneService;

        @MockBean
        ParkingSpotService parkingSpotService;

        ParkingSpot spot1 ;
        ParkingSpot spot2 ;
        ParkingSpot spot3 ;
        ParkingZone zone1 ;
        ParkingZone zone2 ;


        @BeforeEach
        public void init() {
            parkingZoneService = new ParkingZoneService(parkingZoneRepository, parkingSpotService);
            ParkingSpot spot1 = new ParkingSpot();
            ParkingSpot spot2 = new ParkingSpot();
            ParkingSpot spot3 = new ParkingSpot();
            ParkingZone zone1 = new ParkingZone(4L, "Elsa", null, 100);
            ParkingZone zone2 = new ParkingZone(5L, "Anna", null, 300);
            testEntityManager.merge(spot1);
            testEntityManager.merge(spot2);
            testEntityManager.merge(spot3);
            testEntityManager.merge(zone1);
            testEntityManager.merge(zone2);
        }

        @Test
        @DisplayName("Expect findAll() to return a list with size 2")
        void findAll() {
            assertEquals(2, parkingZoneService.findAll().size());
        }

        @Test
        @DisplayName("Expect findById to retrieve the correct ParkingZone. Verify by checking the name")
        void findById() {
            String  name = parkingZoneService.findById(4L)
                    .map(ParkingZone::getName)
                    .orElse(null);
            assertEquals("Elsa", name);
            assertNotEquals("Anna", name);
        }

        @Test
        @DisplayName("Assert size of list is 2. Create a new parkingzone and expect list to be size 3")
        void create() {
            assertEquals(2, parkingZoneService.findAll().size());
            ParkingZone zone = new ParkingZone(1L, "Elsa", null, 100);
            parkingZoneService.create(zone);
            assertEquals(3, parkingZoneService.findAll().size());
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
            assertEquals(1, parkingZoneService.findAll().size());
        }

        @Test
        @DisplayName("Call the initParkingZone method, expect the parkingSpots of the zone to be not null after initiation")
        void initParkingZoneShouldReturnParkingZone() {
            ParkingZone newParkingZone = new ParkingZone(6L, "Ali", null, 100);
            assertNull(newParkingZone.getParkingSpots());
            parkingZoneService.initParkingZone(newParkingZone);
            assertNotNull(newParkingZone.getParkingSpots());
        }
    }



