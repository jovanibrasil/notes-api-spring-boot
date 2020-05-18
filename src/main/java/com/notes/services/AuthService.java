package com.notes.services;

import com.notes.configurations.security.TempUser;

public interface AuthService {
    TempUser checkUserToken(String token);
    String getServiceToken();
}
