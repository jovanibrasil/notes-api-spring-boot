package com.notes.service;

import com.notes.configuration.security.TempUser;

public interface AuthService {
    TempUser checkUserToken(String token);
    String getServiceToken();
}
