package nl.conspect.drivedok.utilities;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.model.ReservationDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "start", expression = "java(dto.getStartDate().atTime(dto.getStartTime()))")
    @Mapping(target = "end", expression = "java(dto.getEndDate().atTime(dto.getEndTime()))")
    Reservation dtoToReservation(ReservationDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Reservation patchDtoToReservation(ReservationDto dto, @MappingTarget Reservation reservation);

    @Mapping(target = "startDate", source = "start", qualifiedByName = "dateConverter")
    @Mapping(target = "startTime", source = "start", qualifiedByName = "timeConverter")
    @Mapping(target = "endDate", source = "end", qualifiedByName = "dateConverter")
    @Mapping(target = "endTime", source = "end", qualifiedByName = "timeConverter")
    ReservationDto reservationToDto(Reservation reservation);

    @Named("dateConverter")
    default LocalDate toLocalDate(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();
    }

    @Named("timeConverter")
    default LocalTime toLocalTime(LocalDateTime localDateTime) {
        return localDateTime.toLocalTime();
    }
}
