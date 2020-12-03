package nl.conspect.drivedok.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Vehicle {

    @Id
    private Long id;
    private String name;
    private String licencePlate;
    @Enumerated(EnumType.STRING)
    private ParkingType parkingType;
    @OneToMany
    private Set<Reservation> reservations;

    public Vehicle() {
    }

    public Vehicle(Long id, String name, String licencePlate, ParkingType parkingType, Set<Reservation> reservations) {
        this.id = id;
        this.name = name;
        this.licencePlate = licencePlate;
        this.parkingType = parkingType;
        this.reservations = reservations;
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

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id) &&
                Objects.equals(name, vehicle.name) &&
                Objects.equals(licencePlate, vehicle.licencePlate) &&
                parkingType == vehicle.parkingType &&
                Objects.equals(reservations, vehicle.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, licencePlate, parkingType, reservations);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", licencePlate='" + licencePlate + '\'' +
                ", parkingType=" + parkingType +
                ", reservations=" + reservations +
                '}';
    }
}
