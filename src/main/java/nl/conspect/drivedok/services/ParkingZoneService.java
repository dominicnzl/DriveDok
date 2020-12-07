package nl.conspect.drivedok.services;


import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.repositories.ParkingZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingZoneService {

    private final ParkingZoneRepository parkingZoneRepository;

    public ParkingZoneService(ParkingZoneRepository parkingZoneRepository) {
        this.parkingZoneRepository = parkingZoneRepository;

    }

    public List<ParkingZone> findAllParkingZones(){
        return parkingZoneRepository.findAll();
    }

    public Optional<ParkingZone> findById(Long id){
        return parkingZoneRepository.findById(id);
    }

    public ParkingZone createParkingZone(ParkingZone parkingZone){

        return parkingZoneRepository.save(parkingZone);
    }



}
