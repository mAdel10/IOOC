package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.backend.models.CourseRate;

import okhttp3.ResponseBody;

public class CourseRateOperation extends BaseOperation<ResponseBody> {

    private CourseRate courseRate;

    public CourseRateOperation(CourseRate courseRate, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.courseRate = courseRate;
    }

    @Override
    public ResponseBody doMain() throws Throwable {
        return OperationsManager.getInstance().doCourseRate(courseRate);
    }
}
