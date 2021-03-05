package nl.conspect.drivedok.services;

import nl.conspect.drivedok.exceptions.ParkingSpotUpdateException;
import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.Zone;
import nl.conspect.drivedok.repositories.ZoneRepository;
import nl.conspect.drivedok.utilities.ParkingTypeComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasicZoneServiceTest {

    @InjectMocks
    BasicZoneService zoneService;

    @Mock
    ZoneRepository zoneRepository;

    @Test
    @DisplayName("Assert that all Zones are returned.")
    void shouldFindAllZones(){
        Zone zone1 = new Zone("Noord", 100);
        Zone zone2 = new Zone("Oost", 200);
        Zone zone3 = new Zone("Zuid", 300);
        List<Zone> expected = new ArrayList<Zone>(Arrays.asList(zone1, zone2, zone3));
        when(zoneRepository.findAll()).thenReturn(expected);

        List<Zone> actual = zoneService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Expect the right Zone to be returned when finding by Id.")
    void shouldFindZoneById(){
        Zone zone1 = new Zone("Noord", 100);
        when(zoneRepository.findById(any())).thenReturn(Optional.of(zone1));

        Zone actual = zoneService.findById(1L).get();

        assertSame(zone1, actual);
    }

    @Test
    @DisplayName("Expect the same Zone to be returned when creating the zone.")
    void shouldReturnSameZone_When_CreateZone(){
        Zone zone1 = new Zone("Noord", 100);
        when(zoneRepository.save(zone1)).thenReturn(zone1);

        Zone actual = zoneService.create(zone1);

        assertSame(zone1, actual);
    }

    @Test
    @DisplayName("Expect the same Zone to be returned when the zone is updated.")
    void shouldReturnSameZone_When_Updated(){
        Zone zone1 = new Zone("Noord", 100);
        when(zoneRepository.save(zone1)).thenReturn(zone1);

        Zone updatedZone = zoneService.update(zone1);

        assertSame(zone1, updatedZone);
    }

    @Test
    @DisplayName("Expect a ParkingSpotUpdateException when totalParkingSpots is not the same as sum of parkingspots.")
    void shouldThrowException_When_TotalParkingSpotsIsNotSumOfParkingSpotsQuantity(){
        Zone zone = new Zone();
        zone.setTotalParkingSpots(100);
        Set<ParkingSpot> parkingSpotSet = new TreeSet<>(new ParkingTypeComparator());
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setQuantity(50);
        parkingSpot.setParkingType(ParkingType.NORMAL);
        parkingSpotSet.add(parkingSpot);
        zone.setParkingSpots(parkingSpotSet);

        Executable executable = () -> zoneService.update(zone);

        assertThrows(ParkingSpotUpdateException.class, executable);
    }

}