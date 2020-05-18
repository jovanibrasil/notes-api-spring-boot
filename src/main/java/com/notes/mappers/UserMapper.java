package com.notes.mappers;

import org.mapstruct.Mapper;

import com.notes.model.User;
import com.notes.model.dto.UserDTO;

@Mapper
public interface UserMapper {
    User userDtoToUser(UserDTO userDto);
    UserDTO userToUserDto(User user);
}
