package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.repositories.ParkingZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParkingZoneServiceTest {

    ParkingZoneRepository parkingZoneRepository;
    ParkingZoneService parkingZoneService;

    @BeforeEach
    public void init() {
        parkingZoneRepository = Mockito.mock(ParkingZoneRepository.class);
        parkingZoneService = new ParkingZoneService(parkingZoneRepository);
    }

    @Test
    public void findAllTest() {
        assertTrue(parkingZoneService.findAll().isEmpty());

        // this will fail if the repository is not called or called multiple times
        Mockito.verify(parkingZoneRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void updateTest() {
        ParkingZone newParkingZone = new ParkingZone();
        newParkingZone.setName("New name");
        ParkingZone oldParkingZone = new ParkingZone();
        oldParkingZone.setId(1L);
        oldParkingZone.setName("Old name");
        Mockito.when(parkingZoneRepository.findById(1L))
                .thenReturn(Optional.of(newParkingZone));


        parkingZoneService.update(oldParkingZone);
        assertEquals(oldParkingZone.getName(), newParkingZone.getName());
    }

}