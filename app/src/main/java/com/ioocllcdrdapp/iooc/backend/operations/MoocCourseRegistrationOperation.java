package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.NtlForm;

import okhttp3.ResponseBody;

public class MoocCourseRegistrationOperation extends BaseOperation<ResponseBody> {

    NtlForm ntlForm;

    public MoocCourseRegistrationOperation(Object requestID, boolean isShowLoadingDialog, Context context, NtlForm ntlForm) {
        super(requestID, isShowLoadingDialog, context);
        this.ntlForm = ntlForm;
    }

    @Override
    public ResponseBody doMain() throws Throwable {
        return OperationsManager.getInstance().doMoocCourseRegistration(ntlForm);
    }
}
