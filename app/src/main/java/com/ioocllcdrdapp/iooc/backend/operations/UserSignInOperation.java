package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.SignForm;
import com.ioocllcdrdapp.iooc.backend.models.Token;
import com.ioocllcdrdapp.iooc.backend.models.User;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.managers.TokenManager;
import com.ioocllcdrdapp.iooc.managers.UserManager;

public class UserSignInOperation extends BaseOperation<Token> {

    private SignForm signForm;

    public UserSignInOperation(SignForm signForm, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.signForm = signForm;
    }

    @Override
    public Token doMain() throws Throwable {
        Token token = OperationsManager.getInstance().doUserSignIn(signForm);
        TokenManager.getInstance().saveToken(token);
        return token;
    }
}
