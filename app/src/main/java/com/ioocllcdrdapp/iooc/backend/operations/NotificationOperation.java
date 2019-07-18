package com.ioocllcdrdapp.iooc.backend.operations;

import android.content.Context;

import com.ioocllcdrdapp.iooc.backend.api.OperationsManager;
import com.ioocllcdrdapp.iooc.backend.models.Notification;

import java.util.List;

public class NotificationOperation extends BaseOperation<List<Notification>> {

    public NotificationOperation(Object requestID, boolean isShowLoadingDialog, Context activity) {
        super(requestID, isShowLoadingDialog, activity);
    }

    @Override
    public List<Notification> doMain() throws Throwable {
        return OperationsManager.getInstance().doGetNotifications();
    }
}