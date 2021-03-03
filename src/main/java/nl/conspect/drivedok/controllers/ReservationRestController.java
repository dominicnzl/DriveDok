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

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/reservations")
public class ReservationRestController {

    private final ReservationService reservationService;

    private final ReservationMapper mapper;

    public ReservationRestController(ReservationService reservationService, ReservationMapper mapper) {
        this.reservationService = reservationService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> findAll() {
        return ok(reservationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> findById(@PathVariable Long id) {
        return of(reservationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationDto dto) {
        var entity = reservationService.save(mapper.dtoToReservation(dto));
        var location = URI.create("/api/reservations/" + entity.getId());
        return created(location).body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> update(@PathVariable Long id, @RequestBody ReservationDto dto) {
        var reservation = mapper.dtoToReservation(dto);
        reservation.setId(id);
        return ok(reservationService.save(reservation));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Reservation> updatePartially(@PathVariable Long id, @RequestBody ReservationDto dto) {
        return reservationService.findById(id)
                .map(e -> mapper.patchDtoToReservation(dto, e))
                .map(reservationService::save)
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return reservationService.findById(id)
                .map(entity -> {
                    reservationService.delete(entity);
                    return noContent().<Void>build();
                })
                .orElseGet(() -> notFound().build());
    }
}
