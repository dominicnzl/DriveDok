package nl.conspect.drivedok.services;


import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.model.Zone;
import nl.conspect.drivedok.repositories.ZoneRepository;
import nl.conspect.drivedok.utilities.ParkingTypeComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;


    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public List<Zone> findAll(){

        // TODO: the parkingspots of each zone need to be properly sorted according enum (natural space)

        return zoneRepository.findAll();
    }

    public Optional<Zone> findById(Long id){
        Zone zone = zoneRepository.findById(id).orElseThrow();
        orderParkingSpots(zone);
        return Optional.of(zone);
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

    private void orderParkingSpots(Zone zone){
        Set<ParkingSpot> parkingSpots = zone.getParkingSpots();
        Set<ParkingSpot> treeset = new TreeSet<ParkingSpot>(new ParkingTypeComparator());
        parkingSpots.forEach(p ->
                treeset.add(p)
        );
        zone.setParkingSpots(treeset);
    }

    private void updateNormalParkingSpots(Zone zone){
        if(null != zone.getParkingSpots()) {


            ParkingSpot spot = zone.getParkingSpots().iterator().next();

            spot.setQuantity(zone.getTotalParkingSpots());
        }
    }
}
