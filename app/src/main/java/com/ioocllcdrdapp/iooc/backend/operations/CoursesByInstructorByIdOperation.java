package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Course;

import java.util.List;

public class CoursesByInstructorByIdOperation extends BaseOperation<List<Course>> {

    private String id;

    public CoursesByInstructorByIdOperation (String id, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.id = id;
    }

    @Override
    public List<Course> doMain() throws Throwable {
        return OperationsManager.getInstance().doGetCoursesByInstructorId(id);
    }
}
