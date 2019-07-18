package com.ioocllcdrdapp.iooc.backend.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ioocllcdrdapp.iooc.backend.models.Token;
import com.ioocllcdrdapp.iooc.managers.TokenManager;
import com.ioocllcdrdapp.iooc.managers.UserManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {

    private static final String TAG = "AuthInterceptor";
    private static String accessToken;
    private static String refreshToken;
    private int refreshCount = 3;

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request request = chain.request();
        Request modifiedRequest;

        APITokenManager apiTokenManager = new APITokenManager() {

            @Override
            public String getToken() {
                Log.v(TAG, "getToken");
                return TokenManager.getInstance().getAccessToken();
            }

            @Override
            public boolean hasToken() {
                Log.v(TAG, "hasToken");
                accessToken = TokenManager.getInstance().getAccessToken();
                return accessToken != null && !accessToken.isEmpty();
            }

            @Override
            public void clearToken() {
                Log.v(TAG, "clearToken");
                TokenManager.getInstance().delete();
            }

            @Override
            public String refreshToken() {
                final String accessToken = "";
                refreshToken = TokenManager.getInstance().getAccessToken();
                // try {
                // Token token = OperationsManager.getInstance().doRefreshToken(refreshToken);
                // TokenManager.getInstance().saveToken(token);

                // return token.getAuthToken();
                // }
                // catch (IOException e) {
                // e.printStackTrace();
                // }
                return accessToken;
            }
        };

        Response response = chain.proceed(request);
        boolean unauthorized = false;
        if (response.code() == 401) {
            Log.v(TAG, "refreshToken unauthorized");
            unauthorized = true;
        }

        if (unauthorized && UserManager.getInstance().isUserLoggedIn()) {
            refreshCount--;
            if (refreshCount <= 0) {
                Log.v(TAG, "refreshToken fail");
                UserManager.getInstance().logout();
                apiTokenManager.clearToken();
                return null;
            }

            accessToken = apiTokenManager.refreshToken();
            if (apiTokenManager.hasToken()) {
                Log.v(TAG, "refreshToken success");
                modifiedRequest = request.newBuilder()
                        .addHeader("Authorization", "Bearer " + apiTokenManager.getToken())
                        .build();
                return chain.proceed(modifiedRequest);
            }
        }
        return response;
    }
}