package com.ioocllcdrdapp.iooc.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.GroupsByInstructorIdOperation;
import com.ioocllcdrdapp.iooc.controllers.adapters.GroupsAdapter;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.managers.UserManager;
import com.ioocllcdrdapp.iooc.utilities.Utilities;

import java.util.List;
import java.util.Objects;

public class GroupByInstructorIdFragment extends Fragment implements RequestObserver {

    private RecyclerView recyclerView;
    private GroupsAdapter groupsAdapter;
    public Context context;
    private final static int REQUEST_GROUP_BY_INSTRUCTOR_ID = 1;
    String id;

    public GroupByInstructorIdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_by_instructor_id, container, false);
        context = Objects.requireNonNull(getActivity()).getApplicationContext();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        getGroupByInstructorId();

        return view;
    }

    private void getGroupByInstructorId(){
        id = UserManager.getInstance().getCurrentUser().getInstructor().getId();
        GroupsByInstructorIdOperation operation = new GroupsByInstructorIdOperation(id, REQUEST_GROUP_BY_INSTRUCTOR_ID,
                false, context);
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
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), getString(R.string.request_server_error), getActivity());
                } else if (code != 401) {
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), errorMsg, getActivity());
                }
            }
        } else if (requestId.equals(REQUEST_GROUP_BY_INSTRUCTOR_ID)) {
            List<Group> groups = (List<Group>) resultObject;
            groupsAdapter = new GroupsAdapter(groups, context, GroupsAdapter.LAND_SCAPE , getActivity());
            recyclerView.setAdapter(groupsAdapter);
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }
}