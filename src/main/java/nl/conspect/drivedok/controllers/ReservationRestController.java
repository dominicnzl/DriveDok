package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.model.ReservationDto;
import nl.conspect.drivedok.services.ReservationService;
import nl.conspect.drivedok.utilities.ReservationMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/reservations")
public class ReservationRestController {

    private final ReservationService service;

    private final ReservationMapper mapper;

    public ReservationRestController(ReservationService service, ReservationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> findAll() {
        return ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> findById(@PathVariable Long id) {
        return of(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationDto dto) {
        return status(CREATED).body(service.save(mapper.dtoToReservation(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> update(@PathVariable Long id, @RequestBody ReservationDto dto) {
        var reservation = mapper.dtoToReservation(dto);
        reservation.setId(id);
        return ok(service.save(reservation));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Reservation> updatePartially(@PathVariable Long id, @RequestBody ReservationDto dto) {
        return service.findById(id)
                .map(e -> mapper.patchDtoToReservation(dto, e))
                .map(service::save)
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isEmpty()) {
            return notFound().build();
        }
        service.deleteById(id);
        return noContent().build();
    }
}
