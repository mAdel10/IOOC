package com.ioocllcdrdapp.iooc.managers;

import com.ioocllcdrdapp.iooc.backend.models.Token;
import com.ioocllcdrdapp.iooc.utilities.CachingManager;

public class TokenManager {

    private static TokenManager self;
    private Token token;

    public static TokenManager getInstance() {
        if (self == null) {
            self = new TokenManager();
        }
        return self;
    }

    private TokenManager() {
        token = CachingManager.getInstance().loadToken();
    }

    public Token getToken() {
        return token;
    }

    public void saveToken(Token token) {
        if (token == null)
            return;
        this.token = token;
        CachingManager.getInstance().saveToken(token);
    }

    public String getAccessToken() {
        return token.getAuthToken();
    }

    public boolean isTokenExixt() {
        return token != null;
    }

    public void delete() {
        CachingManager.getInstance().deleteToken();
        token = null;
    }
}
