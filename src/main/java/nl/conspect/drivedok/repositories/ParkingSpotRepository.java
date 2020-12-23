package nl.conspect.drivedok.repositories;

import nl.conspect.drivedok.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
}
