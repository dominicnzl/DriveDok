package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.of;
import static java.time.Month.JUNE;
import static java.time.Month.SEPTEMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ReservationServiceImplTest {

    ReservationService service;

    @Autowired
    ReservationRepository repository;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void init() {
        service = new ReservationServiceImpl(repository);
    }

    @Test
    @DisplayName("Persist 3 Reservations, expect findall size to be 3.")
    void findAll() {
        var reservationA = new Reservation();
        var reservationB = new Reservation();
        var reservationC = new Reservation();
        var reservations = List.of(reservationA, reservationB, reservationC);
        reservations.forEach(testEntityManager::persist);
        assertEquals(3, service.findAll().size());
    }

    @Test
    @DisplayName("Persist a Reservation, expect find to return an Optional of that Reservation.")
    void findById() {
        var reservation = new Reservation();
        var id = (Long) testEntityManager.persistAndGetId(reservation);
        assertEquals(Optional.of(reservation), service.findById(id));
    }

    @Test
    @DisplayName("Call the create method on Reservation and expect to find that Reservation.")
    void create() {
        var reservation = new Reservation();
        var entity = service.create(reservation);
        assertTrue(service.findById(entity.getId()).isPresent());
        assertEquals(Optional.of(reservation), service.findById(entity.getId()));
    }

    @Test
    @DisplayName("""
            Persist a Reservation with a specific date. Update the method with another date. Expect the Reservation to have 
            the new date on subsequent retrieval.
            """)
    void update() {
        var startdate = of(2021, JUNE, 1, 8, 0);
        var reservation = new Reservation();
        reservation.setStart(startdate);
        var id = (Long) testEntityManager.persistAndGetId(reservation);
        var entity = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not persisted in test"));
        assertEquals(startdate, entity.getStart());

        var updatedDate = of(2021, SEPTEMBER, 1, 8, 0);
        reservation.setStart(updatedDate);
        var updatedReservation = service.update(id, reservation);
        assertEquals(updatedDate, updatedReservation.getStart());
    }

    @Test
    @DisplayName("Persist one Reservation then expect to find none after deleting that Reservation.")
    void delete() {
        var reservation = new Reservation();
        testEntityManager.persist(reservation);
        assertEquals(1, service.findAll().size());

        service.delete(reservation);
        assertEquals(0, service.findAll().size());
    }

    @Test
    @DisplayName("Persist one Reservation then expect to find none after deleting that Reservation by id.")
    void deleteById() {
        var reservation = new Reservation();
        var id = (Long) testEntityManager.persistAndGetId(reservation);
        assertEquals(1, service.findAll().size());

        service.deleteById(id);
        assertEquals(0, service.findAll().size());
    }
}
