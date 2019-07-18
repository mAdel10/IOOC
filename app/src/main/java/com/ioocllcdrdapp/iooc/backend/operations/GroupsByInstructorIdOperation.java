package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Group;

import java.util.List;

public class GroupsByInstructorIdOperation extends BaseOperation<List<Group>> {

    private String id;

    public GroupsByInstructorIdOperation(String id, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.id = id;
    }

    @Override
    public List<Group> doMain() throws Throwable {
        return OperationsManager.getInstance().doGetGroupsByInstructorId(id);
    }
}