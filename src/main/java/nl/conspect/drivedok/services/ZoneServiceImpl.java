package nl.conspect.drivedok.services;


import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.Zone;
import nl.conspect.drivedok.repositories.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;


    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public List<Zone> findAll(){
        return zoneRepository.findAll();
    }

    public Optional<Zone> findById(Long id){
        return zoneRepository.findById(id);
    }

    public Zone create(Zone zone){
        setDefaultParkingSpots(zone);
        return zoneRepository.save(zone);
    }

    public Zone update(Zone zone) {
            updateNormalParkingSpots(zone);
        return zoneRepository.save(zone);
    }

    public void deleteById(Long id){
        zoneRepository.deleteById(id);
    }


    private void setDefaultParkingSpots(Zone zone){
            zone.addParkingSpot(new ParkingSpot(ParkingType.NORMAL, zone.getTotalParkingSpots()));
            zone.addParkingSpot(new ParkingSpot(ParkingType.DISABLED, 0));
            zone.addParkingSpot(new ParkingSpot(ParkingType.ELECTRIC, 0));
    }

    private void updateNormalParkingSpots(Zone zone){
        if(null != zone.getParkingSpots()) {
            ParkingSpot spot = zone.getParkingSpots().iterator().next();
            spot.setQuantity(zone.getTotalParkingSpots());
        }
    }
}
