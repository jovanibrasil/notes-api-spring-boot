package com.notes.services;

import com.notes.config.security.TempUser;

public interface AuthService {
    TempUser checkUserToken(String token);
    String getServiceToken();
}
