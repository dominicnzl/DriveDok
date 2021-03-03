package nl.conspect.drivedok.model;

import java.util.Set;

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<VehicleDto> vehicles;

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

    public Set<VehicleDto> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<VehicleDto> vehicles) {
        this.vehicles = vehicles;
    }
}
