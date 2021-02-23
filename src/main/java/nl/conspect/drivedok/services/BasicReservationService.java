package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BasicReservationService implements ReservationService {

    private final ReservationRepository repository;

    public BasicReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Reservation> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Reservation create(Reservation reservation) {
        return repository.save(reservation);
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        var entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
        entity.setStart(reservation.getStart());
        entity.setEnd(reservation.getEnd());
        entity.setUser(reservation.getUser());
        entity.setVehicle(reservation.getVehicle());
        entity.setParkingSpot(reservation.getParkingSpot());
        return repository.save(entity);
    }

    @Override
    public void delete(Reservation reservation) {
        repository.delete(reservation);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
