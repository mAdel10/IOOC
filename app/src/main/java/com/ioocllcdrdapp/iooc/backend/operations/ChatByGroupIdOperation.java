package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Chat;

import java.util.List;

public class ChatByGroupIdOperation extends BaseOperation<List<Chat>> {

    private String id;

    public ChatByGroupIdOperation(String id, Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
        this.id = id;
    }

    @Override
    public List<Chat> doMain() throws Throwable {
        return OperationsManager.getInstance().doGetChatByGroupId(id);
    }
}
