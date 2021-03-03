package nl.conspect.drivedok.utilities;

import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.model.VehicleDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle dtoToVehicle(VehicleDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Vehicle patchDtoToVehicle(VehicleDto dto, @MappingTarget Vehicle vehicle);
}
