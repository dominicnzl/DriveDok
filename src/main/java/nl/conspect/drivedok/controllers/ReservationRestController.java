package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.model.ReservationDto;
import nl.conspect.drivedok.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/reservations")
public class ReservationRestController {

    private final ReservationService service;

    public ReservationRestController(ReservationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ReservationList> findAll() {
        var reservations = new ReservationList(service.findAll());
        return ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationDto dto, UriComponentsBuilder builder) {
        var reservation = new Reservation();
        reservation.setStart(dto.getStart());
        reservation.setEnd(dto.getEnd());
        reservation.setUser(dto.getUser());
        reservation.setVehicle(dto.getVehicle());
        reservation.setParkingSpot(dto.getParkingSpot());
        var entity = service.create(reservation);
        var uri = builder.path("/api/reservations/{id}").buildAndExpand(entity.getId()).toUri();
        return created(uri).body(entity);
    }


    static class ReservationList {
        private final List<Reservation> reservations;

        public ReservationList(List<Reservation> reservations) {
            this.reservations = reservations;
        }

        public final List<Reservation> getReservations() {
            return reservations;
        }
    }
}
