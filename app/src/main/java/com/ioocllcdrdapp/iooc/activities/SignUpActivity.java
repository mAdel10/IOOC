package com.ioocllcdrdapp.iooc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Instructor;
import com.ioocllcdrdapp.iooc.backend.models.SignForm;
import com.ioocllcdrdapp.iooc.backend.models.Token;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.InstructorSignUpOperation;
import com.ioocllcdrdapp.iooc.backend.operations.StudentSignUpOperation;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.utilities.InputValidator;
import com.ioocllcdrdapp.iooc.utilities.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements RequestObserver {

    @BindView(R.id.signUp_firstName_editText)
    EditText firstNameEditText;
    @BindView(R.id.signUp_lastName_editText)
    EditText lastNameEditText;
    @BindView(R.id.signUp_email_editText)
    EditText emailEditText;
    @BindView(R.id.signUp_phone_editText)
    EditText phoneEditText;
    @BindView(R.id.signUp_password_editText)
    EditText passwordEditText;
    @BindView(R.id.signUp_confirmPassword_editText)
    EditText confirmPasswordEditText;
    @BindView(R.id.signUp_button)
    Button signUpButton;
    @BindView(R.id.signUp_terms_textView)
    TextView termsTextView;
    @BindView(R.id.sign_up_switch)
    SwitchCompat aSwitch;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;

    private static final int REQUEST_STUDENT_SIGN_UP = 1;
    private static final int REQUEST_INSTRUCTOR_SIGN_UP = 2;

    public SignUpActivity() {
        super(R.layout.activity_sign_up, true);
    }

    @OnClick({R.id.signUp_button, R.id.signUp_terms_textView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signUp_button:
                if (Utilities.isConnected(this)) {
                    if (getInputData()) {
                        if (aSwitch.isChecked()) {
                            instructorSignUp();
                        } else {
                            studentSignUp();
                        }
                    }
                } else {
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), getString(R.string.no_internet_connection), this);
                }
                break;
            case R.id.signUp_terms_textView:
                break;
        }
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        ButterKnife.bind(this);

        toolbarTextView.setText(getString(R.string.sign_up));

        toolbarBackImageView.setVisibility(View.VISIBLE);
        toolbarBackImageView.setOnClickListener(v -> finish());
    }

    private boolean getInputData() {
        if (!InputValidator.registerValidation(this, firstNameEditText, lastNameEditText, emailEditText,
                passwordEditText, confirmPasswordEditText, phoneEditText)) {
            return false;
        }

        firstName = firstNameEditText.getText().toString().trim();
        lastName = lastNameEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        phone = phoneEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();

        return true;
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
        } else if (resultObject != null && requestId.equals(REQUEST_STUDENT_SIGN_UP) || requestId.equals(REQUEST_INSTRUCTOR_SIGN_UP)) {
            Intent i = new Intent(this, SignInActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }

    private void studentSignUp() {
        SignForm signForm = new SignForm(firstName, lastName, email, phone, password);
        StudentSignUpOperation operation = new StudentSignUpOperation(signForm, REQUEST_STUDENT_SIGN_UP, true, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void instructorSignUp() {
        SignForm signForm = new SignForm(firstName, lastName, email, phone, password);
        InstructorSignUpOperation operation = new InstructorSignUpOperation(signForm, REQUEST_INSTRUCTOR_SIGN_UP, true, this);
        operation.addRequestObserver(this);
        operation.execute();
    }
}
