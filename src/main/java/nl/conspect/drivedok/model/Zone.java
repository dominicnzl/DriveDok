package nl.conspect.drivedok.model;

import nl.conspect.drivedok.utilities.ParkingTypeComparator;
import org.hibernate.annotations.SortComparator;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class Zone extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 3, message = "{valid.string.minimum}")
    @Size(max = 20, message = "{valid.string.maximum}")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @SortComparator(ParkingTypeComparator.class)
    private Set<ParkingSpot> parkingSpots = new TreeSet<>(new ParkingTypeComparator());

    @NotNull
    @Min(value = 1, message = "{valid.total.parkingspots}")
    private int totalParkingSpots;

    public Zone() {
    }

    public Zone(String name, int totalParkingSpots) {
        this.name = name;
        this.totalParkingSpots = totalParkingSpots;
        this.parkingSpots.add(new ParkingSpot(ParkingType.MOTOR, 0));
        this.parkingSpots.add(new ParkingSpot(ParkingType.DISABLED, 0));
        this.parkingSpots.add(new ParkingSpot(ParkingType.ELECTRIC, 0));
        this.parkingSpots.add(new ParkingSpot(ParkingType.NORMAL, totalParkingSpots));
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

    public Set<ParkingSpot> getParkingSpots() {
        return Collections.unmodifiableSet(parkingSpots);
    }

    public void setParkingSpots(Set<ParkingSpot> parkingSpots) {
        Set<ParkingSpot> parkingSpots1 = new TreeSet<>(new ParkingTypeComparator());
        for (ParkingSpot parkingSpot : parkingSpots) {
            parkingSpots1.add(parkingSpot);
        }
        this.parkingSpots = parkingSpots1;
    }

    public void addParkingSpot(ParkingSpot parkingSpot) {
        parkingSpots.add(parkingSpot);
    }

    public int getTotalParkingSpots() {
        return totalParkingSpots;
    }

    public void setTotalParkingSpots(int totalParkingSpots) {
        this.totalParkingSpots = totalParkingSpots;
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
