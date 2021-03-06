package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    List<Reservation> findAll();
    Optional<Reservation> findById(Long id);
    Reservation save(Reservation reservation);
    void delete(Reservation reservation);
    void deleteById(Long id);
}
