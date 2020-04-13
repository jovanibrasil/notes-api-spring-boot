package com.notes.services;

import com.notes.models.User;

public interface UserService {
    User findByUserName(String userName);
    User save(User user);
    void deleteByUserName(String userName);
}
