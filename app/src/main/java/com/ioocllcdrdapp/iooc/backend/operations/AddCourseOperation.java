package com.ioocllcdrdapp.iooc.backend.operations;


import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Course;

import java.util.List;

import okhttp3.ResponseBody;

public class AddCourseOperation extends BaseOperation<ResponseBody> {
    private Course course;

    public AddCourseOperation(Course course, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.course = course;
    }

    @Override
    public ResponseBody doMain() throws Throwable {
        return OperationsManager.getInstance().doInstractorAddCourse(course);
    }
}

