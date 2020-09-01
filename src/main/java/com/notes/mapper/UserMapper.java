package com.notes.mapper;

import org.mapstruct.Mapper;

import com.notes.controller.dto.UserDTO;
import com.notes.model.User;

@Mapper
public interface UserMapper {
    User userDtoToUser(UserDTO userDto);
    UserDTO userToUserDto(User user);
}
