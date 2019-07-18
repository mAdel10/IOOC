package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Chat;


import okhttp3.ResponseBody;

public class SendChatOperation extends BaseOperation<ResponseBody> {

    private Chat chat;

    public SendChatOperation(Chat chat, Object requestID, boolean isShowLoadingDialog, Context context) {
        super(requestID, isShowLoadingDialog, context);
        this.chat = chat;
    }

    @Override
    public ResponseBody doMain() throws Throwable {
        return OperationsManager.getInstance().doSendChat(chat);
    }
}

