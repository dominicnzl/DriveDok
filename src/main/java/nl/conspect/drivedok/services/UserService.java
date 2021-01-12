package nl.conspect.drivedok.services;

import nl.conspect.drivedok.exceptions.UserNotFoundException;
import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.repositories.UserRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @NonNull
    public User getById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createOrUpdate(User user) {
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
        user.addVehicle(vehicle);
        return userRepository.save(user);
    }
}
