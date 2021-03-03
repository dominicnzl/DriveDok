package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.repositories.ParkingSpotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingSpotServiceTest {

    @InjectMocks
    ParkingSpotService parkingSpotService;

    @Mock
    ParkingSpotRepository parkingSpotRepository;

    @Test
    @DisplayName("Expect findAll() to return all ParkingSpots")
    void shouldFindAllParkingSpots() {
        ParkingSpot spot1 = new ParkingSpot(ParkingType.DISABLED, 10);
        ParkingSpot spot2 = new ParkingSpot(ParkingType.ELECTRIC, 10);
        ParkingSpot spot3 = new ParkingSpot(ParkingType.NORMAL, 5);
        List<ParkingSpot> expected = new ArrayList<>();
        expected.add(spot1);
        expected.add(spot2);
        expected.add(spot3);

        when(parkingSpotRepository.findAll()).thenReturn(expected);

        assertEquals(expected, parkingSpotService.findAll());
    }

    @Test
    @DisplayName("Expect findById() to return the right ParkingSpot")
    void shouldFindParkingSpotById() {
        ParkingSpot spot1 = new ParkingSpot(ParkingType.DISABLED, 10);

        when(parkingSpotRepository.findById(1L)).thenReturn(Optional.of(spot1));

        assertEquals(spot1, parkingSpotService.findById(1L).get());
    }

    @Test
    @DisplayName("Assert that create() saves and returns the right ParkingSpot")
    void create() {
        Long expectedId = 1L;
        ParkingSpot spot1 = new ParkingSpot(ParkingType.DISABLED, 10);

        doAnswer( inv -> {
            ReflectionTestUtils.setField((ParkingSpot) inv.getArgument(0), "id", expectedId);
            return spot1;
        }).when(parkingSpotRepository).save(spot1);

        assertThat(parkingSpotService.create(spot1)).isEqualTo(spot1);
    }

    @Test
    @DisplayName("Expect the quantity field of the same ParkingSpot to be correctly updated")
    void shouldUpdateQuantityOfTheParkingSpot() {
        ParkingSpot spot1 = new ParkingSpot(ParkingType.DISABLED, 10);
        when(parkingSpotRepository.findById(1L)).thenReturn(Optional.of(spot1));

        ParkingSpot parkingSpot = parkingSpotService.findById(1L).get();
        parkingSpot.setQuantity(20);

        when(parkingSpotRepository.save(parkingSpot)).thenReturn(parkingSpot);
        ParkingSpot updatedSpot = parkingSpotService.update(parkingSpot);

        //first check if it is the same object
        assertEquals(parkingSpot,updatedSpot);
        assertEquals(20, updatedSpot.getQuantity());
    }
}