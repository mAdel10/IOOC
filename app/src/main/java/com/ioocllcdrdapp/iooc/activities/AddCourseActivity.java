package com.ioocllcdrdapp.iooc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Category;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.backend.models.Language;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.AddCourseOperation;
import com.ioocllcdrdapp.iooc.backend.operations.CategoryOperation;
import com.ioocllcdrdapp.iooc.backend.operations.LanguageOperation;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.fragments.MoreFragment;
import com.ioocllcdrdapp.iooc.managers.UserManager;
import com.ioocllcdrdapp.iooc.utilities.Utilities;
import com.ioocllcdrdapp.iooc.views.AppSpinnerAdapter;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCourseActivity extends BaseActivity implements View.OnClickListener,
        RequestObserver, AdapterView.OnItemSelectedListener {

    @BindView(R.id.form_courseName_editText)
    EditText courseNameEditText;
    @BindView(R.id.form_courseDesc_editText)
    EditText courseDescEditText;
    @BindView(R.id.form_courseContent_editText)
    EditText courseContentEditText;
    @BindView(R.id.form_courseMaxNum_editText)
    EditText courseMaxNumEditText;
    @BindView(R.id.form_numOfSessions_editText)
    EditText numOfSessionsEditText;
    @BindView(R.id.form_price_editText)
    EditText priceEditText;
    @BindView(R.id.form_language_spinner)
    AppCompatSpinner languageSpinner;
    @BindView(R.id.form_category_spinner)
    AppCompatSpinner categorySpinner;
    Button create;

    private String courseName;
    private String courseDesc;
    private String courseContent;
    private int courseMaxNum;
    private String numOfSession;
    private double coursePrice;
    private Course course;

    private List<Category> categories;
    private List<Language> languages;

    private static final int REQUEST_INSTRUCTOR_ADD_COURSE = 1;
    private final static int REQUEST_GET_LANGUAGES = 2;
    private final static int REQUEST_GET_CATEGORIES = 3;

    public AddCourseActivity() {
        super(R.layout.activity_add_course, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        ButterKnife.bind(this);

        create = findViewById(R.id.form_createCourse_button);
        create.setOnClickListener(this);
        course = new Course();
        getLanguage();
        getCategories();

    }

    private void getInputData() {
        courseName = courseNameEditText.getText().toString().trim();
        courseDesc = courseDescEditText.getText().toString().trim();
        courseContent = courseContentEditText.getText().toString().trim();
        courseMaxNum = Integer.parseInt(courseMaxNumEditText.getText().toString().trim());
        numOfSession = numOfSessionsEditText.getText().toString().trim();
        coursePrice = Double.parseDouble(priceEditText.getText().toString().trim());
        instructorAddCourse();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.form_createCourse_button) {
            getInputData();
            Intent i = new Intent(this, AddGroupActivity.class);
            this.startActivity(i);
            finish();
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
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), this.getString(R.string.request_server_error), this);
                } else if (code != 401) {
                    ErrorDialog.showMessageDialog(this.getString(R.string.invalid_request), errorMsg, this);
                }
            }
        } else if (resultObject != null) {
            AppSpinnerAdapter spinnerAdapter;
            switch ((int) requestId) {
                case REQUEST_GET_CATEGORIES:
                    categories = (List<Category>) resultObject;
                    spinnerAdapter = new AppSpinnerAdapter(this, android.R.layout.simple_list_item_1, categories);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categorySpinner.setAdapter(spinnerAdapter);
                    categorySpinner.setOnItemSelectedListener(this);
                    break;
                case REQUEST_GET_LANGUAGES:
                    languages = (List<Language>) resultObject;
                    spinnerAdapter = new AppSpinnerAdapter(this, android.R.layout.simple_list_item_1, languages);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    languageSpinner.setAdapter(spinnerAdapter);
                    languageSpinner.setOnItemSelectedListener(this);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.form_language_spinner:
                course.setLanguageId(languages.get(position).getId());
                break;
            case R.id.form_category_spinner:
                course.setCategoryId(categories.get(position).getId());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getLanguage() {
        LanguageOperation operation = new LanguageOperation(REQUEST_GET_LANGUAGES,
                false, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void getCategories() {
        CategoryOperation operation = new CategoryOperation(REQUEST_GET_CATEGORIES,
                false, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void instructorAddCourse() {
        String y = course.getLanguageId();
        String x = course.getCategoryId();
        course = new Course(courseName, courseDesc, courseContent, courseMaxNum, numOfSession, coursePrice,y,x);
        AddCourseOperation operation = new AddCourseOperation(course, REQUEST_INSTRUCTOR_ADD_COURSE, true, this);
        operation.addRequestObserver(this);
        operation.execute();
    }
}