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

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Instructor;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.InstructorOperation;
import com.ioocllcdrdapp.iooc.controllers.adapters.InstructorAdapter;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.utilities.Utilities;

import java.util.List;
import java.util.Objects;

public class InstructorsFragment extends Fragment implements RequestObserver {

    private RecyclerView recyclerView;
    private Context context;
    private final static int REQUEST_NOTIFICATION = 1;

    public InstructorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructors, container, false);
        context = Objects.requireNonNull(getActivity()).getApplicationContext();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        getInstructors();
        return view;
    }

    public void getInstructors() {
        InstructorOperation operation = new InstructorOperation(REQUEST_NOTIFICATION,
                false, context);
        operation.addRequestObserver(this);
        operation.execute();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @SuppressWarnings("unchecked")
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
        } else if (requestId.equals(REQUEST_NOTIFICATION)) {
            List<Instructor> instructors = (List<Instructor>) resultObject;
            InstructorAdapter instructorAdapter = new InstructorAdapter(instructors, context);
            recyclerView.setAdapter(instructorAdapter);
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }
}
