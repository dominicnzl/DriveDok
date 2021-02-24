package nl.conspect.drivedok.utilities;

import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.model.VehicleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "user", ignore = true)
    VehicleDto vehicleToDto(Vehicle vehicle);
    List<VehicleDto> vehiclesToDtos(List<Vehicle> vehicles);
    Vehicle dtoToVehicle(VehicleDto dto);
}
