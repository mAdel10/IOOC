package com.ioocllcdrdapp.iooc.managers;

import com.ioocllcdrdapp.iooc.backend.models.User;
import com.ioocllcdrdapp.iooc.utilities.CachingManager;

public class UserManager {

    private static UserManager self;
    private final static String TAG = "UserManager";
    private User currentUser;

    public static UserManager getInstance() {
        if (self == null) {
            self = new UserManager();
        }
        return self;
    }

    public UserManager() {
        currentUser = CachingManager.getInstance().loadUser();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void saveUser(User currentUser) {
        if (currentUser == null)
            return;
        this.currentUser = currentUser;
        CachingManager.getInstance().saveUser(currentUser);
    }

    public boolean isStudent() {
        return currentUser.getStudent() != null;
    }

    public boolean isInstructor() {
        return currentUser.getInstructor() != null;
    }

    public void logout() {
        TokenManager.getInstance().delete();
        CachingManager.getInstance().deleteUser();
        currentUser = null;
    }

    public boolean isUserLoggedIn() {
        return currentUser != null;
    }
}
