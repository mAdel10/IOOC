package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.CourseRate;
import com.ioocllcdrdapp.iooc.backend.models.InstructorRate;

import okhttp3.ResponseBody;

public class InstructorRateOperation extends BaseOperation<ResponseBody> {

    private InstructorRate instructorRate;

    public InstructorRateOperation(InstructorRate instructorRate, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.instructorRate = instructorRate;
    }

    @Override
    public ResponseBody doMain() throws Throwable {
        return OperationsManager.getInstance().doInstructorRate(instructorRate);
    }
}
