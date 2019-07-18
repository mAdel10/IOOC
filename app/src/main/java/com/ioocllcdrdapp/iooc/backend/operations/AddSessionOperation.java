package com.ioocllcdrdapp.iooc.backend.operations;


import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Session;

import okhttp3.ResponseBody;

public class AddSessionOperation extends BaseOperation<ResponseBody> {
    private Session session;

    public AddSessionOperation(Session session, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.session = session;
    }

    @Override
    public ResponseBody doMain() throws Throwable {
        return OperationsManager.getInstance().doAddSession(session);
    }
}

