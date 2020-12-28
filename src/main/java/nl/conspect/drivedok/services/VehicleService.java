package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.repositories.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> findById(Long id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle create(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle update(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public void deleteById(Long id) {
        vehicleRepository.deleteById(id);
    }
}
