package nl.conspect.drivedok.utilities;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.model.ReservationDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(uses = {BasicLocalDateTimeMapper.class},
        componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "user.vehicles", ignore = true)
    ReservationDto reservationToDto(Reservation reservation);

    List<ReservationDto> reservationsToDtos(List<Reservation> reservations);

    Reservation dtoToReservation(ReservationDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Reservation patchDtoToReservation(ReservationDto dto, @MappingTarget Reservation reservation);
}
