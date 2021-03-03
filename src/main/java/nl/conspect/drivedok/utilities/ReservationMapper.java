package nl.conspect.drivedok.utilities;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.model.ReservationDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "start", expression = "java(dto.getStartDate().atTime(dto.getStartTime()))")
    @Mapping(target = "end", expression = "java(dto.getEndDate().atTime(dto.getEndTime()))")
    Reservation dtoToReservation(ReservationDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Reservation patchDtoToReservation(ReservationDto dto, @MappingTarget Reservation reservation);
}
