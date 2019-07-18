package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.MoocPlatform;

import java.util.List;

public class MoocPlatformOperation extends BaseOperation<List<MoocPlatform>> {

    public MoocPlatformOperation(Object requestID, boolean isShowLoadingDialog, Context context) {
        super(requestID, isShowLoadingDialog, context);
    }

    @Override
    public List<MoocPlatform> doMain() throws Throwable {
        return OperationsManager.getInstance().doGetMoocPlatforms();
    }

}
