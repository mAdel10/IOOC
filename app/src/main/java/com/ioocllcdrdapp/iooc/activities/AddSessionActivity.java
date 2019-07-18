package com.ioocllcdrdapp.iooc.activities;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.backend.models.Session;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.AddSessionOperation;
import com.ioocllcdrdapp.iooc.controllers.adapters.MaterialsAdapter;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.dialogs.PopupDialog;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.ioocllcdrdapp.iooc.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddSessionActivity extends BaseActivity implements RequestObserver {

    @BindView(R.id.video_url_editText)
    EditText videoUrlEditText;
    @BindView(R.id.materials_editText)
    EditText materialsEditText;
    @BindView(R.id.quiz_editText)
    EditText quizEditText;
    @BindView(R.id.date_editText)
    EditText dateEditText;
    @BindView(R.id.time_editText)
    EditText timeEditText;
    @BindView(R.id.form_createSession_button)
    Button formCreateSessionButton;
    @BindView(R.id.add_material_button)
    ImageButton addMaterialButton;

    private RecyclerView recyclerView;
    private MaterialsAdapter materialsAdapter;
    private final static int REQUEST_SESSION = 1;


    private String video_url;
    private String getMaterial;
    private List<String> materials;
    private String quiz;
    private String date;
    private String time;

    private Group group;


    public AddSessionActivity() {
        super(R.layout.activity_add_session, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        ButterKnife.bind(this);
        toolbarTextView.setText("Add Session");

        toolbarBackImageView.setVisibility(View.VISIBLE);
        toolbarBackImageView.setOnClickListener(v -> finish());

        group = (Group) getIntent().getSerializableExtra(Constants.INTENT_GROUP);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        materials = new ArrayList<>();
        materialsAdapter = new MaterialsAdapter(materials, this);
        recyclerView.setAdapter(materialsAdapter);


    }


    @OnClick({R.id.form_createSession_button, R.id.add_material_button })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.form_createSession_button:
                getInputData();
                break;
            case R.id.add_material_button:
                addItem();
                break;
        }

    }

    private void getInputData() {
        video_url = videoUrlEditText.getText().toString().trim();
        materials = materialsAdapter.getMaterial();
        quiz = quizEditText.getText().toString().trim();
        date = dateEditText.getText().toString().trim();
        time = timeEditText.getText().toString().trim();

        if (video_url.isEmpty() && materials.isEmpty() && quiz.isEmpty() && date.isEmpty() && time.isEmpty()) {
            videoUrlEditText.setError("Is Required");
            materialsEditText.setError("Is Required");
            quizEditText.setError("Is Required");
            dateEditText.setError("Is Required");
            timeEditText.setError("Is Required");
        } else if (video_url.isEmpty()) {
            videoUrlEditText.setError("Is Required");
        } else if (materials.isEmpty()) {
            materialsEditText.setError("Is Required");
        } else if (quiz.isEmpty()) {
            quizEditText.setError("Is Required");
        } else if (date.isEmpty()) {
            dateEditText.setError("Is Required");
        } else if (time.isEmpty()) {
            timeEditText.setError("Is Required");
        } else {
            addSession();
        }
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
        } else if (resultObject != null && requestId.equals(REQUEST_SESSION)) {
            PopupDialog popupDialog = new PopupDialog(new PopupDialog.ErrorDialogListener() {
                @Override
                public void onOkClick() {
                    finish();
                }

                @Override
                public void onCancelClick() {
                    finish();
                }
            });
            popupDialog.showMessageDialog("Success", "You Added Session successfully", this);
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }

    private void addSession() {
        Session session = new Session(group.getId(), video_url, materials, quiz, date + " " + time);
        AddSessionOperation operation = new AddSessionOperation(session, REQUEST_SESSION, true, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void addItem() {
        getMaterial = materialsEditText.getText().toString().trim();
        materialsAdapter.addItem(getMaterial);
        materialsEditText.setText("");
    }

}
