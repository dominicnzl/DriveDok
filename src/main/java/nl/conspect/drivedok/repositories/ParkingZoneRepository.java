package nl.conspect.drivedok.repositories;


import nl.conspect.drivedok.model.ParkingZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingZoneRepository extends JpaRepository<ParkingZone, Long> {





}
