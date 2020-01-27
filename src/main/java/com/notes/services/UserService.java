package com.notes.services;

import com.notes.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUserName(String userName);
    Optional<User> save(User user);
    void deleteByUserName(String userName);
}
