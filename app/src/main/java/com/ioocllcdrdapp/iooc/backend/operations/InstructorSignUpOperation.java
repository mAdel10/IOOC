package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.SignForm;

import okhttp3.ResponseBody;

public class InstructorSignUpOperation extends BaseOperation<ResponseBody> {

    private SignForm signForm;

    public InstructorSignUpOperation(SignForm signForm, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.signForm = signForm;
    }
    @Override
    public ResponseBody doMain() throws Throwable {
        return OperationsManager.getInstance().doInstructorSignUp(signForm);
    }
}
