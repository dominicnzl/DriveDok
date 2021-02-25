package nl.conspect.drivedok.utilities;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.model.ReservationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {BasicLocalDateTimeMapper.class}, componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "user.vehicles", ignore = true)
    ReservationDto reservationToDto(Reservation reservation);
    List<ReservationDto> reservationsToDtos(List<Reservation> reservations);
    Reservation dtoToReservation(ReservationDto dto);
}
