package nl.conspect.drivedok.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class ParkingSpot extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    @Enumerated(EnumType.STRING)
    private ParkingType parkingType;

    private int quantity;

    private int availability;

    public ParkingSpot(){}

    public ParkingSpot(ParkingType parkingType, int quantity) {
        this.parkingType = parkingType;
        this.quantity = quantity;
        this.availability = quantity;
    }

    @Override
    public Long getId() {
        return id;
    }

    public int getVersion(){return version; };

    @Override
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

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "id=" + id +
                ", version=" + version +
                ", parkingType=" + parkingType +
                ", quantity=" + quantity +
                ", availability=" + availability +
                '}';
    }
}
