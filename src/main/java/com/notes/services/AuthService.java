package com.notes.services;

import com.notes.security.TempUser;

public interface AuthService {
    TempUser checkUserToken(String token);
    String getServiceToken();
}
