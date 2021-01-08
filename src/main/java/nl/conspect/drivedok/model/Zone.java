package nl.conspect.drivedok.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Zone {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min=3, message = "Your name should at least have 3 letters")
    @Size(max = 20, message = "Your name can not have more than 20 letters")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ParkingSpot> parkingSpots = new LinkedHashSet<>();

    @NotNull
    @Min(value = 1, message = "Your zone should have at least 1 parking spot")
    private int totalParkingSpots;

    public Zone() {
    }

    public Zone(String name, Set<ParkingSpot> parkingSpots, int totalParkingSpots) {
        this.name = name;
        this.parkingSpots = parkingSpots;
        this.totalParkingSpots = totalParkingSpots;
    }

    public Zone(Long id, String name, Set<ParkingSpot> parkingSpots, int totalParkingSpots) {
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
        return Collections.unmodifiableSet(parkingSpots);
    }

    public void setParkingSpots(Set<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public void addParkingSpot(ParkingSpot parkingSpot){
        parkingSpots.add(parkingSpot);
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
        Zone that = (Zone) o;
        return totalParkingSpots == that.totalParkingSpots && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(parkingSpots, that.parkingSpots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parkingSpots, totalParkingSpots);
    }

    @Override
    public String toString() {
        return "Zone{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parkingSpots=" + parkingSpots +
                ", totalParkingSpots=" + totalParkingSpots +
                '}';
    }
}
