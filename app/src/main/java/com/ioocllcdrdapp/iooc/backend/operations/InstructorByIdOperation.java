package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Instructor;

public class InstructorByIdOperation extends BaseOperation<Instructor> {

    public String id;

    public InstructorByIdOperation(String id,Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.id = id;
    }

    @Override
    public Instructor doMain() throws Throwable {
        return OperationsManager.getInstance().doGetInstructorById(id);
    }
}
