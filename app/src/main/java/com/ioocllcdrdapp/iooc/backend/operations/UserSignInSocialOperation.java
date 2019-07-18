package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.SignForm;
import com.ioocllcdrdapp.iooc.backend.models.Token;
import com.ioocllcdrdapp.iooc.managers.TokenManager;

public class UserSignInSocialOperation extends BaseOperation<Token> {

    private SignForm signForm;

    public UserSignInSocialOperation(SignForm signForm, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.signForm = signForm;
    }

    @Override
    public Token doMain() throws Throwable {
        Token token = OperationsManager.getInstance().doUserSignInSocial(signForm);
        TokenManager.getInstance().saveToken(token);
        return token;
    }
}
