package nl.conspect.drivedok.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
public class User extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password; // TODO: 02/12/2020 should probably not be a String but a Char[]
    @OneToMany(mappedBy = "user",
            cascade = ALL,
            orphanRemoval = true,
            fetch = LAZY)
    @JsonManagedReference
    private Set<Vehicle> vehicles = new HashSet<>();

    public User() {
    }

    public User(String name, String email, String password, Set<Vehicle> vehicles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.vehicles = vehicles;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        if (getVehicles().contains(vehicle)) {
            return;
        }
        getVehicles().add(vehicle);
        vehicle.setUser(this);
    }

    public void removeVehicle(Vehicle vehicle) {
        this.getVehicles().remove(vehicle);
        vehicle.setUser(null);
    }

}
