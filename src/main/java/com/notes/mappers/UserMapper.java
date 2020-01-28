package com.notes.mappers;

import com.notes.dtos.UserDTO;
import com.notes.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User userDtoToUser(UserDTO userDTO);
    UserDTO userToUserDto(User user);
}
