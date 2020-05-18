package com.notes.services;

import com.notes.model.User;

public interface UserService {
    User findByUserName(String userName);
    User save(User user);
    void deleteByUserName(String userName);
}
