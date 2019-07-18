package com.ioocllcdrdapp.iooc.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.AddGroupOperation;
import com.ioocllcdrdapp.iooc.backend.operations.CoursesByInstructorByIdOperation;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.managers.UserManager;
import com.ioocllcdrdapp.iooc.utilities.Utilities;
import com.ioocllcdrdapp.iooc.views.AppSpinnerAdapter;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddGroupActivity extends BaseActivity implements RequestObserver, AdapterView.OnItemSelectedListener {

    @BindView(R.id.addGroup_course_spinner)
    Spinner courseSpinner;
    @BindView(R.id.addGroup_create_button)
    Button createButton;

    private EditText date;
    private EditText time;
    DatePickerDialog datePickerDialog;
    TimePickerDialog mTimePicker;

    private final static int REQUEST_GET_COURSE = 1;
    private final static int REQUEST_INSTRUCTOR_ADD_GROUP = 2;

    private List<Course> courses;
    private Group group;

    public AddGroupActivity() {
        super(R.layout.activity_add_group, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        ButterKnife.bind(this);

        date = findViewById(R.id.addGroup_date_editText);
        time = findViewById(R.id.addGroup_time_editText);
        group = new Group();
        getCourses();

        date.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            final int year = cal.get(Calendar.YEAR);
            final int month = cal.get(Calendar.MONTH);
            final int day = cal.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(AddGroupActivity.this, (datePicker, thisDay, thisMonth, thisYear) -> {
                String chooseDate = thisDay + "_" + (thisMonth + 1) + "_" + thisYear;
                date.setText(chooseDate);
            }, year, month, day);
            datePickerDialog.show();
        });

        time.setOnClickListener(v -> {
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);
            mTimePicker = new TimePickerDialog(AddGroupActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                String chooseTime = selectedHour + ":" + selectedMinute + ":00";
                time.setText(chooseTime);
            }, hour, minute, false);
            mTimePicker.show();

        });

    }


    @OnClick(R.id.addGroup_create_button)
    public void onViewClicked() {
        group.setStartDate(date.getText().toString().trim() + " " + time.getText().toString().trim());
        instructorAddGroup();
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
                case REQUEST_GET_COURSE:
                    courses = (List<Course>) resultObject;
                    spinnerAdapter = new AppSpinnerAdapter(this, android.R.layout.simple_list_item_1, courses);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    courseSpinner.setAdapter(spinnerAdapter);
                    courseSpinner.setOnItemSelectedListener(this);
                    break;

                case REQUEST_INSTRUCTOR_ADD_GROUP:
                    Intent i = new Intent(this, InstructorMainActivity.class);
                    this.startActivity(i);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.addGroup_course_spinner) {
            group.setCourseId(courses.get(position).getId());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getCourses() {
        String id = UserManager.getInstance().getCurrentUser().getInstructor().getId();
        CoursesByInstructorByIdOperation operation = new CoursesByInstructorByIdOperation(id, REQUEST_GET_COURSE, true, this);
        operation.addRequestObserver(this);
        operation.execute();

    }

    private void instructorAddGroup() {
        AddGroupOperation operation = new AddGroupOperation(group, REQUEST_INSTRUCTOR_ADD_GROUP, true, this);
        operation.addRequestObserver(this);
        operation.execute();
    }
}
