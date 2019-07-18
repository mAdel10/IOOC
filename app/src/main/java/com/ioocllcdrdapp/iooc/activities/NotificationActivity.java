package com.ioocllcdrdapp.iooc.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Notification;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.NotificationOperation;
import com.ioocllcdrdapp.iooc.controllers.adapters.NotificationAdapter;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.utilities.Utilities;

import java.util.List;

public class NotificationActivity extends BaseActivity implements RequestObserver {

    private RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;

    private final static int REQUEST_NOTIFICATION = 1;

    public NotificationActivity() {
        super(R.layout.activity_notification, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText(getString(R.string.notifications));

        toolbarBackImageView.setVisibility(View.VISIBLE);
        toolbarBackImageView.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getNotifications();
    }

    private void getNotifications() {
        NotificationOperation operation = new NotificationOperation(REQUEST_NOTIFICATION,
                false, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleRequestFinished(Object requestId, Throwable error, Object resultObject) {
        if (error != null) {
            if (error instanceof CTHttpError) {
                int code = ((CTHttpError) error).getStatusCode();
                String errorMsg = ((CTHttpError) error).getErrorMsg();
                if (code == -1 || Utilities.isNullString(errorMsg)) {
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), getString(R.string.request_server_error), this);
                } else if (code != 401) {
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), errorMsg, this);
                }
            }
        } else if (requestId.equals(REQUEST_NOTIFICATION)) {
            List<Notification> notifications = (List<Notification>) resultObject;
            notificationAdapter = new NotificationAdapter(notifications, this);
            recyclerView.setAdapter(notificationAdapter);
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }
}
