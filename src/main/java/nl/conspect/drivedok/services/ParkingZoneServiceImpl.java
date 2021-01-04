package nl.conspect.drivedok.services;


import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.repositories.ParkingZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ParkingZoneServiceImpl implements ParkingZoneService {

    private final ParkingZoneRepository parkingZoneRepository;


    public ParkingZoneServiceImpl(ParkingZoneRepository parkingZoneRepository) {
        this.parkingZoneRepository = parkingZoneRepository;
    }

    public List<ParkingZone> findAll(){
        return parkingZoneRepository.findAll();
    }

    public Optional<ParkingZone> findById(Long id){
        return parkingZoneRepository.findById(id);
    }

    public ParkingZone create(ParkingZone parkingZone){
        initParkingZone(parkingZone);
        return parkingZoneRepository.save(parkingZone);
    }

    public ParkingZone update(ParkingZone parkingZone) {
        // controles
        return parkingZoneRepository.save(parkingZone);
    }

    public void deleteById(Long id){
        parkingZoneRepository.deleteById(id);
    }

    /*
     The initParkingZone method defines the default implementation of a ParkingZone.
     So when the user creates one, it has by default certain ParkingSpots.
     */
    private void initParkingZone(ParkingZone parkingZone){
        parkingZone.addParkingSpot(new ParkingSpot(ParkingType.NORMAL, parkingZone.getTotalParkingSpots()));
        parkingZone.addParkingSpot(new ParkingSpot(ParkingType.DISABLED, 0));
        parkingZone.addParkingSpot(new ParkingSpot(ParkingType.ELECTRIC, 0));
    }
}
