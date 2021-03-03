package nl.conspect.drivedok.utilities;

import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.UserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = {VehicleMapper.class})
public interface UserMapper {

    User dtoToUser(UserDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User patchDtoToUser(UserDto dto, @MappingTarget User user);
}
