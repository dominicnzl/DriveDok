package nl.conspect.drivedok.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Set;

public class ZoneDto {

    private Long id;

    @Size(min = 3, message = "{valid.string.minimum}")
    @Size(max = 20, message = "{valid.string.maximum}")
    private String name;

    @Min(value = 1, message = "{valid.total.parkingspots}")
    private int totalParkingSpots;

    private Set<ParkingSpot> parkingSpots;

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

    public int getTotalParkingSpots() {
        return totalParkingSpots;
    }

    public void setTotalParkingSpots(int totalParkingSpots) {
        this.totalParkingSpots = totalParkingSpots;
    }

    public Set<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(Set<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }
}
