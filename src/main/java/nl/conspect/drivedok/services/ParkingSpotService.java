package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.repositories.ParkingSpotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public List<ParkingSpot> findAll() {
        return parkingSpotRepository.findAll();
    }

    public Optional<ParkingSpot> findById(Long id) {
        return parkingSpotRepository.findById(id);
    }

    public ParkingSpot create(ParkingSpot parkingSpot) {
        return parkingSpotRepository.save(parkingSpot);
    }

    public ParkingSpot update(ParkingSpot parkingSpot) {
        return parkingSpotRepository.save(parkingSpot);
    }

    public void deleteById(Long id) {
        parkingSpotRepository.deleteById(id);
    }
}
