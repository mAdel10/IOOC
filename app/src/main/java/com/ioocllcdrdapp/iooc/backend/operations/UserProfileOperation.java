package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.User;
import com.ioocllcdrdapp.iooc.managers.UserManager;

public class UserProfileOperation extends BaseOperation<User> {


    public UserProfileOperation(Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
    }

    @Override
    public User doMain() throws Throwable {
        User user = OperationsManager.getInstance().doUserProfile();
        UserManager.getInstance().saveUser(user);
        return user;
    }
}
