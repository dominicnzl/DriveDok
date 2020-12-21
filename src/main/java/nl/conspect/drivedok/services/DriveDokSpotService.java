package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.DriveDokSpot;
import nl.conspect.drivedok.repositories.DriveDokSpotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DriveDokSpotService {

    private final DriveDokSpotRepository driveDokSpotRepository;

    public DriveDokSpotService(DriveDokSpotRepository driveDokSpotRepository) {
        this.driveDokSpotRepository = driveDokSpotRepository;
    }

    public List<DriveDokSpot> findAll() {
        return driveDokSpotRepository.findAll();
    }

    public Optional<DriveDokSpot> findById(Long id) {
        return driveDokSpotRepository.findById(id);
    }

    public DriveDokSpot create(DriveDokSpot driveDokSpot) {
        return driveDokSpotRepository.save(driveDokSpot);
    }

    public DriveDokSpot update(DriveDokSpot driveDokSpot) {
        return driveDokSpotRepository.save(driveDokSpot);
    }

    public void deleteById(Long id) {
        driveDokSpotRepository.deleteById(id);
    }
}
