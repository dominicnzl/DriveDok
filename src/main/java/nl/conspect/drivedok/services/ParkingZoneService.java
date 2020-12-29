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
public class ParkingZoneService {

    private final ParkingZoneRepository parkingZoneRepository;
    private  final ParkingSpotService parkingSpotService;

    public ParkingZoneService(ParkingZoneRepository parkingZoneRepository, ParkingSpotService parkingSpotService) {
        this.parkingZoneRepository = parkingZoneRepository;
        this.parkingSpotService = parkingSpotService;
    }

    public List<ParkingZone> findAll(){
        return parkingZoneRepository.findAll();
    }

    public Optional<ParkingZone> findById(Long id){
        return parkingZoneRepository.findById(id);
    }

    public ParkingZone create(ParkingZone parkingZone){
        // add default types
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
    public void initParkingZone(ParkingZone parkingZone){
        ParkingSpot ps1 = new ParkingSpot(ParkingType.DISABLED, 0);
        ParkingSpot ps2 = new ParkingSpot(ParkingType.ELECTRIC, 0);
        ParkingSpot ps3 = new ParkingSpot(ParkingType.NORMAL, parkingZone.getTotalParkingSpots());
        parkingSpotService.create(ps1);
        parkingSpotService.create(ps2);
        parkingSpotService.create(ps3);
        Set<ParkingSpot>initSet = new HashSet<ParkingSpot>() ;
        initSet.add(ps1);
        initSet.add(ps2);
        initSet.add(ps3);
        parkingZone.setParkingSpots(initSet);
    }
}
