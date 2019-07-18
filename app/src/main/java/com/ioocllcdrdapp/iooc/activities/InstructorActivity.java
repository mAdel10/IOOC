package com.ioocllcdrdapp.iooc.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.backend.models.Instructor;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.CoursesByInstructorByIdOperation;
import com.ioocllcdrdapp.iooc.backend.operations.InstructorByIdOperation;
import com.ioocllcdrdapp.iooc.controllers.adapters.CourseAdapter;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.ioocllcdrdapp.iooc.utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructorActivity extends BaseActivity implements RequestObserver {

    @BindView(R.id.instructor_imageView)
    ImageView instructorImageView;
    @BindView(R.id.instructor_name_textView)
    TextView instructorNameText;
    @BindView(R.id.instructor_get_details_position)
    TextView instructorPosition;
    @BindView(R.id.instructor_get_details_birthDate)
    TextView instructorBirth;
    @BindView(R.id.instructor_get_details_cv)
    TextView instructorCv;
    @BindView(R.id.instructor_details_ratingBar)
    RatingBar instructorRating;
    @BindView(R.id.item_instructor_rate_textView)
    TextView instructorRatingTextView;

    private RecyclerView recyclerView;
    CourseAdapter courseAdapter;

    private final static int REQUEST_COURSE_BY_INSTRUCTOR_ID = 1;
    private final static int REQUEST_INSTRUCTOR_BY_ID = 2;

    public InstructorActivity() {
        super(R.layout.activity_instructor, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        ButterKnife.bind(this);
        toolbarTextView.setText(getString(R.string.instructor_details));

        toolbarBackImageView.setVisibility(View.VISIBLE);
        toolbarBackImageView.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));

        String instructorId = getIntent().getStringExtra(Constants.INTENT_ID);
        getCourseByInstructorId(instructorId);
        getInstructorById(instructorId);

    }

    private void getCourseByInstructorId(String id) {
        CoursesByInstructorByIdOperation operation = new CoursesByInstructorByIdOperation(id, REQUEST_COURSE_BY_INSTRUCTOR_ID,
                false, this);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void getInstructorById(String id) {
        InstructorByIdOperation operation = new InstructorByIdOperation(id, REQUEST_INSTRUCTOR_BY_ID,
                false, this);
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
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), getString(R.string.request_server_error), this);
                } else if (code != 401) {
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), errorMsg, this);
                }
            }
        } else if (requestId.equals(REQUEST_COURSE_BY_INSTRUCTOR_ID)) {
            List<Course> courses = (List<Course>) resultObject;
            courseAdapter = new CourseAdapter(courses, this);
            recyclerView.setAdapter(courseAdapter);
        } else if (requestId.equals((REQUEST_INSTRUCTOR_BY_ID))) {
            Instructor instructor = (Instructor) resultObject;
            if (instructor.getUser() != null) {
                Picasso.with(this)
                        .load(instructor.getUser().getImage())
                        .placeholder(R.drawable.img_placeholder)
                        .into(instructorImageView);

                String name = instructor.getUser().getFirstName() + " " + instructor.getUser().getLastName();
                instructorNameText.setText(name);

                instructorPosition.setText(instructor.getPosition());
                instructorBirth.setText(instructor.getBirthDate());
                instructorCv.setText(instructor.getCv());
                instructorRating.setRating(instructor.getRate());

                if (instructor.getRateCount() != 0) {
                    String rate = ((instructor.getRate() / instructor.getRateCount()) + "/5");
                    instructorRatingTextView.setText(rate);
                } else {
                    instructorRatingTextView.setText("0/5");
                }
            }
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }
}
