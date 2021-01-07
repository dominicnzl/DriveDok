package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.Zone;

import java.util.List;
import java.util.Optional;

public interface ZoneService {

    public List<Zone> findAll() ;
    public Optional<Zone> findById(Long id);
    public Zone create(Zone zone);
    public Zone update(Zone zone);
    public void deleteById(Long id);

}
