package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Session;

import java.util.List;

public class SessionByGroupIdOperation extends BaseOperation<List<Session>>{

    public String id;

    public SessionByGroupIdOperation(String id, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.id = id;
    }

    @Override
    public List<Session> doMain() throws Throwable {
        return OperationsManager.getInstance().doGetSessionByGroupId(id);
    }
}