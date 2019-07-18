package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Course;

import java.util.List;

public class CourseOperation extends BaseOperation<List<Course>> {

    public CourseOperation(Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);

    }

    @Override
    public List<Course> doMain() throws Throwable {
        return OperationsManager.getInstance().doGetCourse();
    }
}
