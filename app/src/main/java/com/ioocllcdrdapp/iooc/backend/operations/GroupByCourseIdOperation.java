package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Group;


import java.util.List;

public class GroupByCourseIdOperation extends BaseOperation<List<Group>> {

    private String id;

    public GroupByCourseIdOperation(String id, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.id = id;
    }

    @Override
    public List<Group> doMain() throws Throwable {
        return OperationsManager.getInstance().doGetGroupsByCourseId(id);
    }
}

