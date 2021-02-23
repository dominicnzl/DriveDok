package nl.conspect.drivedok.utilities;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.model.ReservationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {BasicLocalDateTimeMapper.class}, componentModel = "spring")
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    ReservationDto reservationToDto(Reservation reservation);

    Reservation dtoToReservation(ReservationDto dto);
}
