package nl.conspect.drivedok.repositories;

import nl.conspect.drivedok.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
