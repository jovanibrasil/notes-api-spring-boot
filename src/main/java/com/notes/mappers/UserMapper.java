package com.notes.mappers;

import org.mapstruct.Mapper;

import com.notes.controllers.dto.UserDTO;
import com.notes.model.User;

@Mapper
public interface UserMapper {
    User userDtoToUser(UserDTO userDto);
    UserDTO userToUserDto(User user);
}
