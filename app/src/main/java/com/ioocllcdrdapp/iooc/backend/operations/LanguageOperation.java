package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Language;

import java.util.List;

public class LanguageOperation extends BaseOperation<List<Language>> {

    public LanguageOperation(Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
    }

    @Override
    public List<Language> doMain() throws Throwable {
        return OperationsManager.getInstance().doGetLanguage();
    }

}
