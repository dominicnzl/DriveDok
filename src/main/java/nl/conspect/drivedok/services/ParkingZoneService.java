package nl.conspect.drivedok.services;


import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.repositories.ParkingZoneRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingZoneService {

    private final ParkingZoneRepository parkingZoneRepository;

    public ParkingZoneService(ParkingZoneRepository parkingZoneRepository) {
        this.parkingZoneRepository = parkingZoneRepository;

    }


    public ParkingZone createParkingZone(ParkingZone parkingZone){

        return parkingZoneRepository.save(parkingZone);
    }


}
