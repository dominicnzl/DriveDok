package nl.conspect.drivedok.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Reservation extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime start;
    private LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_vehicle")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "fk_parking_spot")
    private ParkingSpot parkingSpot;

    public Reservation() {
    }

    public Reservation(LocalDateTime start, LocalDateTime end, User user, Vehicle vehicle, ParkingSpot parkingSpot) {
        this.start = start;
        this.end = end;
        this.user = user;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) o;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
