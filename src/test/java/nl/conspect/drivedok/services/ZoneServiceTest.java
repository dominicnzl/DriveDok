package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.Zone;
import nl.conspect.drivedok.repositories.ZoneRepository;
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
class ZoneServiceTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ZoneService zoneService;

    @TestConfiguration
    static class ZoneServiceImplTestContextConfiguration {

        @Autowired
        ZoneRepository zoneRepository;

        @Bean
        public ZoneService zoneService() {
            return new ZoneServiceImpl(zoneRepository);
        }
    }

    @BeforeEach
    public void init() {
    }

    @Test
    @DisplayName("Expect findAll() to return a list with size 2")
    void findAll() {
        assertEquals(0, zoneService.findAll().size());
        Zone zone1 = new Zone("Elsa", Collections.emptySet(), 100);
        testEntityManager.persist(zone1);
        assertEquals(1, zoneService.findAll().size());
    }

    @Test
    @DisplayName("Expect findById to retrieve the correct Zone. Verify by checking the name")
    void findById() {
        Zone zone2 = new Zone("Anna", Collections.emptySet(), 300);
        var id = testEntityManager.persistAndGetId(zone2, Long.class);
        String name = zoneService.findById(id)
                .map(Zone::getName)
                .orElse(null);
        assertEquals("Anna", name);
    }

    @Test
    @DisplayName("Assert size of list is 0. Create a new zone and expect list to be size 3")
    void create() {
        assertEquals(0, zoneService.findAll().size());
        ParkingSpot spot2 = new ParkingSpot();
        Set<ParkingSpot> spotSet = new HashSet<>();
        spotSet.add(spot2);
        Zone zone = new Zone("Elsa", spotSet, 100);
        zoneService.create(zone);
        assertEquals(1, zoneService.findAll().size());

    }

    @Test
    @DisplayName("The Zone should be correctly updated")
    void update() {
        ParkingSpot spot2 = new ParkingSpot(ParkingType.NORMAL, 3);
        Set<ParkingSpot> spotSet = new HashSet<>();
        spotSet.add(spot2);
        Zone zone = new Zone("Elsa", spotSet, 100);
        Zone beforeUpdateZone = zoneService.create(zone);

        assertNotNull(beforeUpdateZone);
        assertEquals("Elsa", beforeUpdateZone.getName());

        beforeUpdateZone.setName("Anna");
        zoneService.update(beforeUpdateZone);
        Zone afterUpdateZone = zoneService.findById(beforeUpdateZone.getId()).orElse(null);
        assertNotNull(afterUpdateZone);
        assertEquals("Anna", afterUpdateZone.getName());
    }

    @Test
    @DisplayName("Delete a zone. Expect findAll() to have size 1")
    void deleteById() {
        ParkingSpot spot2 = new ParkingSpot();
        Set<ParkingSpot> spotSet = new HashSet<>();
        spotSet.add(spot2);
        Zone zone = new Zone("Elsa", spotSet, 100);
        Zone zone1 = zoneService.create(zone);
        assertEquals(1, zoneService.findAll().size());
        zoneService.deleteById(zone1.getId());
        testEntityManager.flush();
        testEntityManager.clear();
        assertEquals(0, zoneService.findAll().size());
    }

}



