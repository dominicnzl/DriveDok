package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.Zone;
import nl.conspect.drivedok.repositories.ZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Transactional
class ZoneServiceTest {

    ZoneService zoneService;

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ZoneRepository zoneRepository;

    Zone zone1;
    Zone zone2;

    @BeforeEach
    public void init() {
        zoneService = new ZoneServiceImpl(zoneRepository);
        zone1 = new Zone("Elsa",100);
        zone2 = new Zone("Anna",200);
        zoneService.create(zone1);
        zoneService.create(zone2);
        testEntityManager.persist(zone1);
        testEntityManager.persist(zone2);
    }

    @Test
    @DisplayName("Expect findAll() to return a list with size 2")
    void findAll() {
        assertEquals(2, zoneService.findAll().size());
    }

    @Test
    @DisplayName("Expect findById to retrieve the correct Zone. Verify by checking the name")
    void findById() {
        Zone zone2 = new Zone("Anna",300);
        var id = testEntityManager.persistAndGetId(zone2, Long.class);
        String name = zoneService.findById(id)
                .map(Zone::getName)
                .orElse(null);
        assertEquals("Anna", name);
    }

    @Test
    @DisplayName("Assert size of list is 2. Create a new zone and expect list to be size 3")
    void create() {
        assertEquals(2, zoneService.findAll().size());
        Zone zone = new Zone("Elsa", 100);
        zoneService.create(zone);
        assertEquals(3, zoneService.findAll().size());

    }

    @Test
    @DisplayName("The Zone should be correctly updated")
    void updateName() {
        Zone beforeUpdateZone = zoneService.create(new Zone("Noord",100));
        assertNotNull(beforeUpdateZone);
        assertEquals("Noord", beforeUpdateZone.getName());

        beforeUpdateZone.setName("Zuid");
        zoneService.update(beforeUpdateZone);
        Zone afterUpdateZone = zoneService.findById(beforeUpdateZone.getId()).orElse(null);
        assertNotNull(afterUpdateZone);
        assertEquals("Zuid", afterUpdateZone.getName());
    }


    @Test
    @DisplayName("Delete a zone. Expect findAll() to have size 1")
    void deleteById() {
        Zone zone = new Zone("Elsa", 100);
        Zone zone1 = zoneService.create(zone);
        assertEquals(3, zoneService.findAll().size());
        zoneService.deleteById(zone1.getId());
        testEntityManager.flush();
        testEntityManager.clear();
        assertEquals(2, zoneService.findAll().size());
    }

    @Test
    @DisplayName("Create zone and expect first parkingspot of type NORMAL and quantity equal to totalParkingSpots")
    void createAndCheckNormalParkingSpots(){
        Zone zone1 = zoneService.create(new Zone("Zone 1", 500));
        assertEquals(ParkingType.NORMAL, zone1.getParkingSpots().iterator().next().getParkingType());
        assertEquals(zone1.getTotalParkingSpots(), zone1.getParkingSpots().iterator().next().getQuantity());
    }

    @Test
    @DisplayName("FindAll zones and assert their first parkingspottype is NORMAL")
    void findAllAndAssertFirstPSTypeIsNORMAL(){
        List<Zone> all = zoneService.findAll();
         all.forEach(zone -> assertEquals(ParkingType.NORMAL, zone.getParkingSpots().iterator().next().getParkingType()));
    }
}