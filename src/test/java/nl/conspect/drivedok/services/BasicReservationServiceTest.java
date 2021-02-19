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
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BasicReservationServiceTest {

    ReservationService service;

    @Autowired
    ReservationRepository repository;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void init() {
        service = new BasicReservationService(repository);
    }

    @Test
    @DisplayName("Persist 3 Reservations, expect findall size to be 3.")
    void findAll() {
        var reservationA = new Reservation();
        var reservationB = new Reservation();
        var reservationC = new Reservation();
        var reservations = List.of(reservationA, reservationB, reservationC);
        reservations.forEach(testEntityManager::persistAndFlush);
        testEntityManager.clear();
        assertThat(service.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Persist a Reservation, expect find to return an Optional of that Reservation.")
    void findById() {
        var reservation = new Reservation();
        var id = (Long) testEntityManager.persistAndGetId(reservation);
        testEntityManager.flush();
        testEntityManager.clear();
        assertThat(service.findById(id)).isEqualTo(Optional.of(reservation));
    }

    @Test
    @DisplayName("Call the create method on Reservation and expect to find that Reservation.")
    void create() {
        var reservation = new Reservation();
        var entity = service.create(reservation);
        testEntityManager.flush();
        testEntityManager.clear();
        assertThat(service.findById(entity.getId())).hasValue(entity);
    }

    @Test
    @DisplayName("""
            Persist a Reservation with a specific date. Update the method with another date. Expect the Reservation to 
            have the new date on subsequent retrieval.
            """)
    void update() {
        var startdate = of(2021, JUNE, 1, 8, 0);
        var reservation = new Reservation();
        reservation.setStart(startdate);
        var id = (Long) testEntityManager.persistAndGetId(reservation);
        testEntityManager.flush();
        testEntityManager.clear();
        var entity = service.findById(id).orElse(null);
        assertThat(entity).isNotNull();
        assertThat(startdate).isEqualTo(entity.getStart());

        var updatedDate = of(2021, SEPTEMBER, 1, 8, 0);
        reservation.setStart(updatedDate);
        var updatedReservation = service.update(id, reservation);
        testEntityManager.flush();
        testEntityManager.clear();
        assertThat(updatedDate).isEqualTo(updatedReservation.getStart());
    }

    @Test
    @DisplayName("Persist one Reservation then expect to find none after deleting that Reservation.")
    void delete() {
        var reservation = new Reservation();
        testEntityManager.persistAndFlush(reservation);
        testEntityManager.clear();
        assertThat(service.findAll().size()).isEqualTo(1);

        service.delete(reservation);
        testEntityManager.flush();
        testEntityManager.clear();
        assertThat(service.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Persist one Reservation then expect to find none after deleting that Reservation by id.")
    void deleteById() {
        var reservation = new Reservation();
        var id = (Long) testEntityManager.persistAndGetId(reservation);
        testEntityManager.flush();
        testEntityManager.clear();
        assertThat(service.findAll().size()).isEqualTo(1);

        service.deleteById(id);
        testEntityManager.flush();
        testEntityManager.clear();
        assertThat(service.findAll()).isEmpty();
    }
}
