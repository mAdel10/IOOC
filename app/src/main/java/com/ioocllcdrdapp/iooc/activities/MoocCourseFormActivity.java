package com.ioocllcdrdapp.iooc.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.NtlForm;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.MoocCourseRegistrationOperation;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.ioocllcdrdapp.iooc.managers.UserManager;
import com.ioocllcdrdapp.iooc.utilities.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoocCourseFormActivity extends BaseActivity implements RequestObserver {

    private static final int REQUEST_MoocCourse_Registration = 1;

    @BindView(R.id.mooc_form_provider_name)
    EditText moocFormProviderName;
    @BindView(R.id.mooc_form_course_name)
    EditText moocFormCourseName;
    @BindView(R.id.mooc_form_university)
    EditText moocFormUniversity;
    @BindView(R.id.mooc_form_faculty)
    EditText moocFormFaculty;
    @BindView(R.id.mooc_form_student_id)
    EditText moocFormUniversityId;
    @BindView(R.id.mooc_form_ssn)
    EditText moocFormSsn;
    @BindView(R.id.mooc_form_send)
    Button moocFormSend;
    private String studentId, studentName, moocCourseId, providerName, university, universityId, nationalId, faculty;
    private String[] moocCourseData;

    public MoocCourseFormActivity() {
        super(R.layout.activity_mooc_course_form, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        ButterKnife.bind(this);
        toolbarBackImageView.setVisibility(View.VISIBLE);
        toolbarBackImageView.setOnClickListener(v -> finish());

        moocCourseData = getIntent().getStringArrayExtra(Constants.INTENT_OBJECT);
        providerName = moocCourseData[2];
        moocFormCourseName.setText(moocCourseData[1]);
        moocFormProviderName.setText(providerName);
    }

    @OnClick(R.id.mooc_form_send)
    public void onViewClicked() {
        if (getData())
            moocCourseRegistration();
    }

    public boolean getData() {
        studentId = UserManager.getInstance().getCurrentUser().getId();
        studentName = UserManager.getInstance().getCurrentUser().getFirstName() + " " + UserManager.getInstance().getCurrentUser().getLastName();
        moocCourseId = moocCourseData[0];
        universityId = moocFormUniversityId.getText().toString();
        university = moocFormUniversity.getText().toString();
        nationalId = moocFormSsn.getText().toString();
        faculty = moocFormFaculty.getText().toString();

        if (nationalId.length() != 14 || university.equals("") || faculty.equals("") || universityId.equals("")) {
            if (nationalId.length() != 14)
                moocFormSsn.setError("Must be 14 number");
            else {
                if (university.equals(""))
                    moocFormUniversity.setError("Required!");
                else if (faculty.equals(""))
                    moocFormFaculty.setError("Required!");
                else moocFormUniversityId.setError("Required!");
            }
            return false;
        }

        return true;
    }

    public void moocCourseRegistration() {
        NtlForm ntlForm = new NtlForm(studentId, studentName, moocCourseId, university, universityId, nationalId, faculty);
        MoocCourseRegistrationOperation moocCourseRegistrationOperation = new MoocCourseRegistrationOperation(REQUEST_MoocCourse_Registration, true, this, ntlForm);
        moocCourseRegistrationOperation.addRequestObserver(this);
        moocCourseRegistrationOperation.execute();
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
        } else if (requestId.equals(REQUEST_MoocCourse_Registration)) {
            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
           finish();
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }
}
