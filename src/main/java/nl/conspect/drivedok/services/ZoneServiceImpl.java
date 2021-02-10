package nl.conspect.drivedok.services;


import nl.conspect.drivedok.exceptions.ParkingSpotUpdateException;
import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.Zone;
import nl.conspect.drivedok.repositories.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;

    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public List<Zone> findAll(){
        return  zoneRepository.findAll();
    }

    public Optional<Zone> findById(Long id){
        return zoneRepository.findById(id);
    }

    public Zone create(Zone zone){
        setParkingSpotsAtInitiation(zone);
        return zoneRepository.save(zone);
    }

    public Zone update(Zone zone) {
        //first an extra check for the zone that returns from the client, because of front-end logics
        final Set<ParkingSpot> parkingSpots = zone.getParkingSpots();
        int sumOfParkingSpots = parkingSpots.stream().mapToInt(ParkingSpot::getQuantity).sum();
        if(zone.getTotalParkingSpots() != sumOfParkingSpots){
            throw new ParkingSpotUpdateException();
        }
        parkingSpots.forEach(parkingSpot -> parkingSpot.setAvailability(parkingSpot.getQuantity()));
        return zoneRepository.save(zone);
    }

    public void deleteById(Long id){
        zoneRepository.deleteById(id);
    }

    private void setParkingSpotsAtInitiation(Zone zone){
        zone.addParkingSpot(new ParkingSpot(ParkingType.MOTOR, 0));
        zone.addParkingSpot(new ParkingSpot(ParkingType.DISABLED, 0));
        zone.addParkingSpot(new ParkingSpot(ParkingType.ELECTRIC, 0));
        zone.addParkingSpot(new ParkingSpot(ParkingType.NORMAL, zone.getTotalParkingSpots()));
    }

}
