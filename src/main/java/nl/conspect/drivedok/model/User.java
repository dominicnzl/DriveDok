package nl.conspect.drivedok.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
public class User extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 1, max = 64, message = "{valid.string.range}")
    @Column(nullable = false, length = 64)
    private String name;

    @Email
    private String email;

    @NotNull
    @Size(min = 4, message = "{valid.string.minimum}")
    @Column(nullable = false)
    private String password; // TODO: 02/12/2020 should probably not be a String but a Char[]

    @OneToMany(mappedBy = "user",
            cascade = ALL,
            orphanRemoval = true,
            fetch = LAZY)
    @JsonManagedReference
    private Set<Vehicle> vehicles = new HashSet<>();

    @OneToMany(mappedBy = "user",
            cascade = ALL,
            orphanRemoval = true,
            fetch = LAZY)
    @JsonManagedReference
    private List<Reservation> reservations = new ArrayList<>();

    public User() {
    }

    public User(String name, String email, String password, Set<Vehicle> vehicles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.vehicles = vehicles;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Vehicle> getVehicles() {
        return unmodifiableSet(vehicles);
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Set<Vehicle> addVehicle(Vehicle vehicle) {
        if (this.vehicles.add(vehicle)) {
            vehicle.setUser(this);
        }
        return getVehicles();
    }

    public Set<Vehicle> removeVehicle(Vehicle vehicle) {
        if (this.vehicles.remove(vehicle)) {
            vehicle.setUser(null);
        }
        return getVehicles();
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
        reservation.setUser(this);
        return getReservations();
    }

    public List<Reservation> removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setUser(null);
        return getReservations();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (! (o instanceof User)) {
            return false;
        }
        User other = (User) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
