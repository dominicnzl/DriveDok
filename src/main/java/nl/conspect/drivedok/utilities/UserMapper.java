package nl.conspect.drivedok.utilities;

import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToDto(User user);
    List<UserDto> usersToDtos(List<User> users);
    User dtoToUser(UserDto dto);
}
