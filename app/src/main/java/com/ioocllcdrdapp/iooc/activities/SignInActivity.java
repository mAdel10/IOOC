package com.ioocllcdrdapp.iooc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.SignForm;
import com.ioocllcdrdapp.iooc.backend.models.User;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.UserProfileOperation;
import com.ioocllcdrdapp.iooc.backend.operations.UserSignInOperation;
import com.ioocllcdrdapp.iooc.backend.operations.UserSignInSocialOperation;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.managers.UserManager;
import com.ioocllcdrdapp.iooc.utilities.InputValidator;
import com.ioocllcdrdapp.iooc.utilities.Utilities;

import org.json.JSONException;


import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity implements RequestObserver {

    @BindView(R.id.signIn_facebook_button)
    Button signInFacebookButton;
    @BindView(R.id.signIn_google_button)
    Button signInGoogleButton;
    @BindView(R.id.signIn_email_editText)
    EditText signInEmailEditText;
    @BindView(R.id.signIn_password_editText)
    EditText signInPasswordEditText;
    @BindView(R.id.signIn_forget_password_text_view)
    TextView signInForgetPasswordTextView;
    @BindView(R.id.signIn_sign_in_button)
    Button signInSignInButton;
    @BindView(R.id.sign_in_signUp_TextView)
    TextView signInSignUpTextView;
    @BindView(R.id.login_button)
    LoginButton loginButton;
    private String email;
    private String password;
    private CallbackManager callbackManager;

    private static final int REQUEST_USER_SIGN_IN = 1;
    private static final int REQUEST_GET_USER = 2;
    private static final int REQUEST_USER_SIGN_IN_SOCIAL = 3;

    public SignInActivity() {
        super(R.layout.activity_sign_in, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        ButterKnife.bind(this);

        toolbarTextView.setText(getString(R.string.sign_in));

        toolbarBackImageView.setVisibility(View.VISIBLE);
        toolbarBackImageView.setOnClickListener(v -> finish());

        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                Toast.makeText(SignInActivity.this, "User Logged out", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, (object, response) -> {
            try {
                String first_name = object.getString("first_name");
                String last_name = object.getString("last_name");
                String email = object.getString("email");
                userSignInSocial(email);
                String id = object.getString("id");

                String image_url = object.getString("https://graph.facebook.com/" + id + "/pictures?type=normal");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @OnClick({R.id.signIn_facebook_button, R.id.signIn_google_button,
            R.id.signIn_email_editText, R.id.signIn_password_editText,
            R.id.signIn_forget_password_text_view, R.id.signIn_sign_in_button, R.id.sign_in_signUp_TextView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signIn_facebook_button:
                loginButton.performClick();
                break;
            case R.id.signIn_google_button:
                break;
            case R.id.signIn_email_editText:
                break;
            case R.id.signIn_password_editText:
                break;
            case R.id.signIn_forget_password_text_view:
                break;
            case R.id.signIn_sign_in_button:
                if (Utilities.isConnected(this)) {
                    getInputData();
                } else {
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), getString(R.string.no_internet_connection), this);
                }
                break;
            case R.id.sign_in_signUp_TextView:
                Intent i = new Intent(this, SignUpActivity.class);
                startActivity(i);
                break;

            case R.id.login_button:
                break;
        }
    }


    private void getInputData() {
        if (!InputValidator.loginValidation(this, signInEmailEditText, signInPasswordEditText)) {
            return;
        }

        email = signInEmailEditText.getText().toString().trim();
        password = signInPasswordEditText.getText().toString().trim();

        userSignIn();
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
        } else if (resultObject != null) {
            FirebaseMessaging.getInstance().subscribeToTopic("iooc");
            switch ((int) requestId) {
                case REQUEST_USER_SIGN_IN:
                    getAccountData();
                    break;
                case REQUEST_GET_USER:
                case REQUEST_USER_SIGN_IN_SOCIAL:
                    Intent i;
                    User user = (User) resultObject;
                    switch (user.getUserType()) {
                        case User.TYPE_STUDENT:
                            i = new Intent(this, MainActivity.class);
                            break;
                        case User.TYPE_INSTRUCTOR:
                            i = new Intent(this, InstructorMainActivity.class);
                            break;
                        default:
                            i = new Intent(this, MainActivity.class);
                            break;
                    }
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
            }
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }

    private void userSignIn() {
        SignForm signForm = new SignForm(email, password);
        UserSignInOperation operation = new UserSignInOperation(signForm, REQUEST_USER_SIGN_IN, true, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void getAccountData() {
        UserProfileOperation operation = new UserProfileOperation(REQUEST_GET_USER, true, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void userSignInSocial(String email) {
        SignForm signForm = new SignForm(email);
        UserSignInSocialOperation operation = new UserSignInSocialOperation(signForm, REQUEST_USER_SIGN_IN_SOCIAL, true, this);
        operation.addRequestObserver(this);
        operation.execute();
    }
}
