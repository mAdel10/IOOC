package com.ioocllcdrdapp.iooc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.EditProfileForm;
import com.ioocllcdrdapp.iooc.backend.models.User;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.UpdateProfileOperation;
import com.ioocllcdrdapp.iooc.backend.operations.UserProfileOperation;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.managers.UserManager;
import com.ioocllcdrdapp.iooc.utilities.Utilities;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener, RequestObserver {
    private EditText firstName_editText;
    private EditText lastName_editText;
    private EditText phoneNumber_editText;
    private Button editProfile_btn;


    private String firstName;
    private String lastName;
    private String phone;

    private static final int REQUEST_EDIT_PROFILE = 1;
    private static final int REQUEST_REFRESH_PROFILE = 2;

    public EditProfileActivity() {
        super(R.layout.activity_edit_profile, true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit_profile_button) {
            if (getInputData()) {
                updateProfile();
            }

        }
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setVisibility(View.VISIBLE);
        toolbarTextView.setText(getText(R.string.edit_profile));

        firstName_editText = findViewById(R.id.edit_profile_firstName_editText);
        lastName_editText = findViewById(R.id.edit_profile_lastName_editText);
        phoneNumber_editText = findViewById(R.id.edit_profile_phone_editText);

        editProfile_btn = findViewById(R.id.edit_profile_button);
        editProfile_btn.setOnClickListener(this);

        User user = UserManager.getInstance().getCurrentUser();

        fillData(user);

    }

    @Override
    public void handleRequestFinished(Object requestId, Throwable error, Object resultObject) {
        if (error != null) {
            if (error instanceof CTHttpError) {
                int code = ((CTHttpError) error).getStatusCode();
                String errorMsg = ((CTHttpError) error).getErrorMsg();
                if (code == -1 || Utilities.isNullString(errorMsg)) {
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), this.getString(R.string.request_server_error), this);
                } else if (code != 401) {
                    ErrorDialog.showMessageDialog(this.getString(R.string.invalid_request), errorMsg, this);
                }
            }
        } else if (resultObject != null && requestId.equals(REQUEST_EDIT_PROFILE)) {

            refreshProfile();
        } else if (resultObject != null && requestId.equals(REQUEST_REFRESH_PROFILE)) {
            User user = (User) resultObject;
            switch (user.getUserType()) {
                case User.TYPE_STUDENT:
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();
                    break;
                case User.TYPE_INSTRUCTOR:
                    i = new Intent(this, InstructorMainActivity.class);
                    startActivity(i);
                    finish();
                    break;
            }
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }

    private void fillData(User user) {
        firstName_editText.setText(user.getFirstName());
        lastName_editText.setText(user.getLastName());
        phoneNumber_editText.setText(user.getPhone());
    }

    private boolean getInputData() {

        firstName = firstName_editText.getText().toString().trim();
        lastName = lastName_editText.getText().toString().trim();
        phone = phoneNumber_editText.getText().toString().trim();

        if (firstName.isEmpty()) {
            firstName_editText.setError("First name required");
            return false;
        } else if (lastName.isEmpty()) {
            lastName_editText.setError("Last name required");
            return false;
        } else if (phone.isEmpty()) {
            phoneNumber_editText.setError("phone number required");
            return false;
        }

        return true;
    }

    private void updateProfile() {
        EditProfileForm editProfileForm = new EditProfileForm(firstName, lastName, phone);
        UpdateProfileOperation operation = new UpdateProfileOperation(editProfileForm, REQUEST_EDIT_PROFILE, true, this);
        operation.addRequestObserver(this);
        operation.execute();

    }

    private void refreshProfile() {
        UserProfileOperation operation = new UserProfileOperation(REQUEST_REFRESH_PROFILE, true, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

}
