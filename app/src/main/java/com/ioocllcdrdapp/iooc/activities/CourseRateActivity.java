package com.ioocllcdrdapp.iooc.activities;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.CourseRate;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.CourseRateOperation;
import com.ioocllcdrdapp.iooc.backend.operations.LanguageOperation;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.ioocllcdrdapp.iooc.managers.UserManager;
import com.ioocllcdrdapp.iooc.utilities.Utilities;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CourseRateActivity extends BaseActivity implements RequestObserver {

    private RatingBar ratingBar;
    private Button rate;

    private CourseRate courseRate;
    private static final int REQUEST_COURSE_RATE = 1;


    public CourseRateActivity() {
        super(R.layout.activity_course_rate, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarBackImageView.setVisibility(View.VISIBLE);
        toolbarBackImageView.setOnClickListener(v -> finish());
        ratingBar = findViewById(R.id.rating_bar);
        rate = findViewById(R.id.rate_now_course);
        AtomicReference<Float> currentRate = new AtomicReference<>((float) 0);
        AtomicBoolean rated = new AtomicBoolean(false);

        String studentId = UserManager.getInstance().getCurrentUser().getId();
        String courseId = (String) getIntent().getSerializableExtra(Constants.INTENT_OBJECT);
        rate.setOnClickListener(view -> {
            if (!rated.get()) {
                currentRate.set(ratingBar.getRating());
                rated.set(true);
                courseRate = new CourseRate(studentId, courseId, ratingBar.getRating());
                setCourseRate();
                finish();
            } else {
                Toast.makeText(this, "You Rated Before", Toast.LENGTH_SHORT).show();
                ratingBar.setRating(currentRate.get());
            }


        });

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
        }


    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }

    private void setCourseRate() {
        CourseRateOperation operation = new CourseRateOperation(courseRate, REQUEST_COURSE_RATE, false, this);
        operation.addRequestObserver(this);
        operation.execute();
    }
}
