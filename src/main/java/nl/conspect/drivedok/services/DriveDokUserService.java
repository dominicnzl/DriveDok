package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.DriveDokUser;
import nl.conspect.drivedok.repositories.DriveDokUserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DriveDokUserService {

    private final DriveDokUserRepository driveDokUserRepository;

    public DriveDokUserService(DriveDokUserRepository driveDokUserRepository) {
        this.driveDokUserRepository = driveDokUserRepository;
    }

    public Collection<DriveDokUser> findAll() {
        return driveDokUserRepository.findAll();
    }
}
