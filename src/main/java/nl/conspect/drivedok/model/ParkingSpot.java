package nl.conspect.drivedok.model;

import java.util.Objects;

public class ParkingSpot {

    private Long id;
    private ParkingType parkingType;

    public ParkingSpot(){}

    public ParkingSpot(Long id, ParkingType parkingType) {
        this.id = id;
        this.parkingType = parkingType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingType getParkingType() {
        return parkingType;
    }

    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return id.equals(that.id) &&
                parkingType == that.parkingType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parkingType);
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "id=" + id +
                ", parkingType=" + parkingType +
                '}';
    }
}
