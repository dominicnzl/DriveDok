package nl.conspect.drivedok.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ParkingSpot {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ParkingType parkingType;

    private int quantity;

    public ParkingSpot(){}

    public ParkingSpot(ParkingType parkingType, int quantity) {
        this.parkingType = parkingType;
        this.quantity = quantity;
    }

    public ParkingSpot(Long id, ParkingType parkingType, int quantity) {
        this.id = id;
        this.parkingType = parkingType;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && parkingType == that.parkingType;
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
                ", quantity=" + quantity +
                '}';
    }
}
