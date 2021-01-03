package nl.conspect.drivedok.services;

import nl.conspect.drivedok.exceptions.UserNotFoundException;
import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@Transactional
public class UserService {

    private final VehicleService vehicleService;

    private final UserRepository userRepository;

    public UserService(VehicleService vehicleService, UserRepository userRepository) {
        this.vehicleService = vehicleService;
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User addVehicleByUserId(Long id, Vehicle vehicle) {
        var user = findById(id).orElseThrow(() -> new UserNotFoundException(id));
        var savedVehicle = vehicleService.create(vehicle);
        if (user.getVehicles().add(savedVehicle)) {
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException(format("Vehicle could not be added to %s", user.getName()));
        }
    }
}
