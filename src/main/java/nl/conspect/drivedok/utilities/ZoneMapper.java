package nl.conspect.drivedok.utilities;

import nl.conspect.drivedok.model.Zone;
import nl.conspect.drivedok.model.ZoneDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ZoneMapper {

    Zone dtoToZone(ZoneDto dto);

    ZoneDto zoneToDto(Zone zone);
}
