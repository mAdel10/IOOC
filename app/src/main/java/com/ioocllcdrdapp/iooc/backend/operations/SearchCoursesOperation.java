package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.backend.models.SearchFrom;

import java.util.List;

public class SearchCoursesOperation extends BaseOperation<List<Course>> {

    private SearchFrom searchFrom;

    public SearchCoursesOperation(SearchFrom searchFrom, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.searchFrom = searchFrom;
    }

    @Override
    public List<Course> doMain() throws Throwable {
        return OperationsManager.getInstance().doSearchCourses(searchFrom);
    }
}