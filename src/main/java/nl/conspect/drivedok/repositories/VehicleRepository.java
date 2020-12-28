package nl.conspect.drivedok.repositories;

import nl.conspect.drivedok.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
