package nl.conspect.drivedok.services;

import nl.conspect.drivedok.exceptions.VehicleNotFoundException;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.repositories.VehicleRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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

    @NonNull
    public Vehicle getById(Long id) throws VehicleNotFoundException {
        return Optional.ofNullable(id)
                .flatMap(this::findById)
                .orElseThrow(() -> new VehicleNotFoundException(id));
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updatePartially(Long id, Map<String, String> properties) {
        var vehicle = getById(id);
        if (properties.containsKey("name")) {
            vehicle.setName(properties.get("name"));
        }
        if (properties.containsKey("licencePlate")) {
            vehicle.setLicencePlate(properties.get("licencePlate"));
        }
        if (properties.containsKey("parkingType")) {
            vehicle.setParkingType(ParkingType.valueOf(properties.get("parkingType")));
        }
        return save(vehicle);
    }

    public void deleteById(Long id) {
        vehicleRepository.findById(id).ifPresent(this::delete);
    }

    public void delete(Vehicle vehicle) {
        if (null != vehicle.getUser()) {
            vehicle.getUser().removeVehicle(vehicle);
        }
        vehicleRepository.delete(vehicle);
    }
}
