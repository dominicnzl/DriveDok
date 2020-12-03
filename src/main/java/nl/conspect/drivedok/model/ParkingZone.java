package nl.conspect.drivedok.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Entity
public class ParkingZone {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany
    private Set<ParkingSpot> parkingSpots;

    public ParkingZone() {
    }

    public ParkingZone(Long id, String name, Set<ParkingSpot> parkingSpots) {
        this.id = id;
        this.name = name;
        this.parkingSpots = parkingSpots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(Set<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingZone that = (ParkingZone) o;
        return Objects.equals(id, that.id) &&
                name.equals(that.name) &&
                parkingSpots.equals(that.parkingSpots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parkingSpots);
    }

    @Override
    public String toString() {
        return "ParkingZone{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parkingSpots=" + parkingSpots +
                '}';
    }
}
