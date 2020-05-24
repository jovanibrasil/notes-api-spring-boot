package com.notes.service;

import com.notes.controller.dto.UserDTO;

public interface UserService {
    UserDTO findByUserName(String userName);
    UserDTO save(UserDTO userDTO);
    void deleteByUserName(String userName);
}
