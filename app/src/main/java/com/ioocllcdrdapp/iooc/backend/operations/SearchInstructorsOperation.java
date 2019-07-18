package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.backend.models.Instructor;
import com.ioocllcdrdapp.iooc.backend.models.SearchFrom;

import java.util.List;

public class SearchInstructorsOperation extends BaseOperation<List<Instructor>> {

    private SearchFrom searchFrom;

    public SearchInstructorsOperation(SearchFrom searchFrom, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.searchFrom = searchFrom;
    }

    @Override
    public List<Instructor> doMain() throws Throwable {
        return OperationsManager.getInstance().doSearchInstructors(searchFrom);
    }
}