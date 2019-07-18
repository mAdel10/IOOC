package com.ioocllcdrdapp.iooc.backend.operations;


import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.EditProfileForm;

import okhttp3.ResponseBody;

public class UpdateProfileOperation extends BaseOperation<ResponseBody> {
    private EditProfileForm editProfileForm;

    public UpdateProfileOperation(EditProfileForm editProfileForm, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.editProfileForm = editProfileForm;
    }

    @Override
    public ResponseBody doMain() throws Throwable {
        return OperationsManager.getInstance().doUpdateProfile(editProfileForm);
    }
}

