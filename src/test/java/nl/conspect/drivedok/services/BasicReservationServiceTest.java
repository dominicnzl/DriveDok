package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BasicReservationServiceTest {

    @MockBean
    ReservationRepository repository;

    ReservationService service;

    @BeforeEach
    void init() {
        this.service = new BasicReservationService(repository);
    }

    @Test
    @DisplayName("Expect service.findAll to call repository.findAll")
    void findAll() {
        var reservations = List.of(new Reservation(), new Reservation(), new Reservation());
        when(repository.findAll()).thenReturn(reservations);

        var list = service.findAll();
        assertThat(list.size()).isEqualTo(3);
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Expect service.findById to call repository.findById")
    void findById() {
        var reservation = new Reservation();
        when(repository.findById(1L)).thenReturn(Optional.of(reservation));
        assertThat(service.findById(1L)).hasValue(reservation);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Call the create method on Reservation and expect to find that Reservation.")
    void create() {
        var reservation = new Reservation();
        when(repository.save(reservation)).thenReturn(reservation);
        var entity = service.create(reservation);
        assertThat(entity).isEqualTo(reservation);
        verify(repository, times(1)).save(reservation);
    }

    @Test
    @DisplayName("Expect service.update to call repository.save")
    void update() {
        var reservation = new Reservation();
        when(service.findById(1L)).thenReturn(Optional.of(reservation));
        when(repository.save(reservation)).thenReturn(reservation);
        var entity = service.update(1L, reservation);
        assertThat(entity).isEqualTo(reservation);
        verify(repository, times(1)).save(reservation);
    }

    @Test
    @DisplayName("Expect service.delete to call repository.delete")
    void delete() {
        doNothing().when(repository).delete(isA(Reservation.class));
        var reservation = new Reservation();
        service.delete(reservation);
        verify(repository, times(1)).delete(reservation);
    }

    @Test
    @DisplayName("Expect service.deleteById to call repository.deleteById")
    void deleteById() {
        doNothing().when(repository).deleteById(1L);
        service.deleteById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}
