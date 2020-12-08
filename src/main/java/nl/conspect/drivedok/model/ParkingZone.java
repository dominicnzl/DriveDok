package nl.conspect.drivedok.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class ParkingZone {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private LocalDateTime version;

    private String name;

    @OneToMany
    private Set<ParkingSpot> parkingSpots;

    private int totalParkingSpots;

    public ParkingZone() {
    }

    public ParkingZone(Long id, String name, Set<ParkingSpot> parkingSpots, int totalParkingSpots) {
        this.id = id;
        this.name = name;
        this.parkingSpots = parkingSpots;
        this.totalParkingSpots = totalParkingSpots;
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

    public int getTotalParkingSpots() {
        return totalParkingSpots;
    }

    public void setTotalParkingSpots(int totalParkingSpots) {
        this.totalParkingSpots = totalParkingSpots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingZone that = (ParkingZone) o;
        return totalParkingSpots == that.totalParkingSpots && id.equals(that.id) && version.equals(that.version) && name.equals(that.name) && parkingSpots.equals(that.parkingSpots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, name, parkingSpots, totalParkingSpots);
    }

    @Override
    public String toString() {
        return "ParkingZone{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", parkingSpots=" + parkingSpots +
                ", totalParkingSpots=" + totalParkingSpots +
                '}';
    }
}
