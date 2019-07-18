package com.ioocllcdrdapp.iooc.backend.api;

public interface APITokenManager {

    String getToken();

    boolean hasToken();

    void clearToken();

    String refreshToken();
}
