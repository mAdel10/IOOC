package com.ioocllcdrdapp.iooc.backend.operations;//package com.perceptivemind.rafikee.backend.operations.acl;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.SignForm;
import com.ioocllcdrdapp.iooc.backend.models.Token;
import com.ioocllcdrdapp.iooc.managers.TokenManager;

import okhttp3.ResponseBody;

public class StudentSignUpOperation extends BaseOperation<ResponseBody> {

    private SignForm signForm;

    public StudentSignUpOperation(SignForm signForm, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.signForm = signForm;
    }

    @Override
    public ResponseBody doMain() throws Throwable {
        return OperationsManager.getInstance().doStudentSignUp(signForm);
    }
}
