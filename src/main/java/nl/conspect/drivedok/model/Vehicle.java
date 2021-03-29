package nl.conspect.drivedok.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
public class Vehicle extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(max = 64, min = 1, message = "{valid.string.range}")
    @Column(nullable = false, length = 64)
    private String name;

    @NotNull
    @Size(max = 8, min = 6, message = "{valid.string.range")
    @Column(nullable = false, length = 8)
    private String licencePlate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParkingType parkingType;

    @ManyToOne(fetch = LAZY)
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "vehicle",
            cascade = ALL,
            orphanRemoval = true,
            fetch = LAZY)
    @JsonBackReference
    private List<Reservation> reservations = new ArrayList<>();

    public Vehicle() {
    }

    public Vehicle(String name, String licencePlate, ParkingType parkingType) {
        this.name = name;
        this.licencePlate = licencePlate;
        this.parkingType = parkingType;
    }

    public Vehicle(String name, String licencePlate, ParkingType parkingType, User user) {
        this.name = name;
        this.licencePlate = licencePlate;
        this.parkingType = parkingType;
        this.user = user;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public ParkingType getParkingType() {
        return parkingType;
    }

    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Reservation> getReservations() {
        return unmodifiableList(reservations);
    }

    public List<Reservation> setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
        return getReservations();
    }

    public List<Reservation> addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setVehicle(this);
        return getReservations();
    }

    public List<Reservation> removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setVehicle(null);
        return getReservations();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicle)) {
            return false;
        }
        Vehicle other = (Vehicle) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
