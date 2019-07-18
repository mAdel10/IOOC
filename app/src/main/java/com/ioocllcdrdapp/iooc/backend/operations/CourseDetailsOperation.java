package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.CourseDetails;

public class CourseDetailsOperation extends BaseOperation<CourseDetails> {

    public CourseDetailsOperation(Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
    }

    @Override
    public CourseDetails doMain() throws Throwable {
        return OperationsManager.getInstance().doGetCourseDetails();
    }
}