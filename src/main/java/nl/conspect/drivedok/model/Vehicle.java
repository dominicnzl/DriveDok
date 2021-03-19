package nl.conspect.drivedok.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Vehicle extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(max = 64, min = 1, message = "{valid.string.range}")
    @Column(nullable = false, length = 64)
    private String name;

    @NotNull
    @Size(max = 8, min = 6, message = "{valid.string.range")
    @Column(nullable = false, length = 8)
    private String licencePlate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParkingType parkingType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    public Vehicle() {
    }

    public Vehicle(String name, String licencePlate, ParkingType parkingType) {
        this.name = name;
        this.licencePlate = licencePlate;
        this.parkingType = parkingType;
    }

    public Vehicle(String name, String licencePlate, ParkingType parkingType, User user) {
        this.name = name;
        this.licencePlate = licencePlate;
        this.parkingType = parkingType;
        this.user = user;
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

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public ParkingType getParkingType() {
        return parkingType;
    }

    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
