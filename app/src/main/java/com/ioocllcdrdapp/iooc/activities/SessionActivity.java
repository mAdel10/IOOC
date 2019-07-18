package com.ioocllcdrdapp.iooc.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.backend.models.Session;
import com.ioocllcdrdapp.iooc.backend.models.User;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.SessionByGroupIdOperation;
import com.ioocllcdrdapp.iooc.controllers.adapters.SessionAdapter;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.ioocllcdrdapp.iooc.managers.UserManager;
import com.ioocllcdrdapp.iooc.utilities.Utilities;

import java.util.List;

public class SessionActivity extends BaseActivity implements RequestObserver {

    private RecyclerView recyclerView;
    private static final int REQUEST_GET_SESSION = 1;

    private Group group;


    public SessionActivity() {
        super(R.layout.activity_session, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText(getString(R.string.sessions));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));

        group = (Group) getIntent().getSerializableExtra(Constants.INTENT_OBJECT);
        getSessionByGroupId(group.getId());

        if(UserManager.getInstance().isInstructor()){
                    toolbarAddSessionButton.setVisibility(View.VISIBLE);
                    toolbarAddSessionButton.setOnClickListener(v -> {
                        Intent i = new Intent(SessionActivity.this, AddSessionActivity.class);
                        i.putExtra(Constants.INTENT_GROUP, group);
                        startActivity(i);
                    });
            }
    }

    private void getSessionByGroupId(String id) {
        SessionByGroupIdOperation operation = new SessionByGroupIdOperation(id, REQUEST_GET_SESSION,
                false, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

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
        } else if (requestId.equals(REQUEST_GET_SESSION)) {
            List<Session> sessions = (List<Session>) resultObject;
            SessionAdapter sessionAdapter = new SessionAdapter(sessions, group, this);
            recyclerView.setAdapter(sessionAdapter);
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }
}
