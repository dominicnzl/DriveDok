package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingZone;

import java.util.List;
import java.util.Optional;

public interface ParkingZoneService {

    public List<ParkingZone> findAll() ;
    public Optional<ParkingZone> findById(Long id);
    public ParkingZone create(ParkingZone parkingZone);
    public ParkingZone update(ParkingZone parkingZone);
    public void deleteById(Long id);

}
