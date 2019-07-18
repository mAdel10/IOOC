package com.ioocllcdrdapp.iooc.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.ChatOperation;
import com.ioocllcdrdapp.iooc.controllers.adapters.ChatAdapter;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.utilities.Utilities;

import java.util.List;

public class ChatFragment extends Fragment implements RequestObserver {

    private RecyclerView recyclerView;
    private Context context;
    private static final int REQUEST_CHAT = 1;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        context = getActivity().getApplicationContext();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        getChat();

        return view;

    }

    private void getChat() {
        ChatOperation operation = new ChatOperation(REQUEST_CHAT, false, context);
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
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), getString(R.string.request_server_error), getActivity());
                } else if (code != 401) {
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), errorMsg, getActivity());
                }
            }
        } else if (requestId.equals(REQUEST_CHAT)) {
            List<Group> groups = (List<Group>) resultObject;
            ChatAdapter chatAdapter = new ChatAdapter(groups, context);
            recyclerView.setAdapter(chatAdapter);
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }
}
