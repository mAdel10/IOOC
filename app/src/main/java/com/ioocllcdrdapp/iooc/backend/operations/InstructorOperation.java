package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Instructor;

import java.util.List;

public class InstructorOperation extends BaseOperation<List<Instructor>> {

    public InstructorOperation(Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
    }

    @Override
    public List<Instructor> doMain() throws Throwable {
        return OperationsManager.getInstance().doGetInstructor();
    }
}
