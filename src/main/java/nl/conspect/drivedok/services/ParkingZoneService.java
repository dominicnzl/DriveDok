package nl.conspect.drivedok.services;


import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.repositories.ParkingZoneRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ParkingZoneService {

    private final ParkingZoneRepository parkingZoneRepository;

    public ParkingZoneService(ParkingZoneRepository parkingZoneRepository) {
        this.parkingZoneRepository = parkingZoneRepository;

    }

    public Collection<ParkingZone> findAllParkingZones(){
        return parkingZoneRepository.findAll();
    }

    public ParkingZone createParkingZone(ParkingZone parkingZone){

        return parkingZoneRepository.save(parkingZone);
    }



}
