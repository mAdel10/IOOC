package com.ioocllcdrdapp.iooc.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.backend.models.Instructor;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.GroupByCourseIdOperation;
import com.ioocllcdrdapp.iooc.controllers.adapters.SubmitCourseAdapter;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.ioocllcdrdapp.iooc.managers.UserManager;
import com.ioocllcdrdapp.iooc.utilities.Utilities;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CourseActivity extends BaseActivity implements RequestObserver {

    @BindView(R.id.course_imageView)
    ImageView courseImageView;
    @BindView(R.id.course_apply_button)
    Button applyButton;
    @BindView(R.id.course_instructor_imageView)
    CircleImageView instructorImageView;
    @BindView(R.id.course_instructor_name_textView)
    TextView instructorNameTextView;
    @BindView(R.id.course_instructor_position_textView)
    TextView instructorPositionTextView;
    @BindView(R.id.course_instructor_layout)
    LinearLayout instructorLayout;
    @BindView(R.id.course_name_TextView)
    TextView courseNameTextView;
    @BindView(R.id.course_description_TextView)
    TextView courseDescriptionTextView;
    @BindView(R.id.course_content_textView)
    TextView courseContentTextView;
    @BindView(R.id.course_sessions_textView)
    TextView courseSessionsTextView;
    @BindView(R.id.course_maxNumber_textView)
    TextView courseMaxNumberTextView;
    @BindView(R.id.course_price_textView)
    TextView coursePriceTextView;

    private final static int REQUEST_GET_GROUPS = 1;
    private SubmitCourseAdapter submitCourseAdapter;
    private RecyclerView recyclerView;

    private Course course;
    private Instructor instructor;
    private List<Group> groups;

    public CourseActivity() {
        super(R.layout.activity_course, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        ButterKnife.bind(this);
        course = (Course) getIntent().getSerializableExtra(Constants.INTENT_OBJECT);

        toolbarTextView.setText(getString(R.string.course_details));

        toolbarBackImageView.setVisibility(View.VISIBLE);
        toolbarBackImageView.setOnClickListener(v -> finish());

        Picasso.with(this)
                .load(course.getImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.img_placeholder)
                .into(courseImageView);

        if (course.getInstructor() != null && course.getInstructor().getUser() != null) {
            Picasso.with(this)
                    .load(course.getInstructor().getUser().getImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .resize(100, 100)
                    .centerCrop()
                    .placeholder(R.drawable.img_vector_placeholder)
                    .into(instructorImageView);

            String name = course.getInstructor().getUser().getFirstName() + " "
                    + course.getInstructor().getUser().getLastName();
            instructorNameTextView.setText(name);
            instructorPositionTextView.setText(course.getInstructor().getPosition());
        }

        courseNameTextView.setText(course.getName());
        courseDescriptionTextView.setText(course.getDescription());
        courseContentTextView.setText(course.getContent());
        courseSessionsTextView.setText(String.valueOf(course.getNumberOfSessions()));
        courseMaxNumberTextView.setText(String.valueOf(course.getMaxNumbers()));
        coursePriceTextView.setText(String.valueOf(course.getPrice()));

        getGroups(course.getId());
    }

    @OnClick({R.id.course_apply_button, R.id.course_instructor_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.course_apply_button:
                if(groups == null){
                    getGroups(course.getId());
                    Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_SHORT).show();
                    return;
                } else if(groups.isEmpty()){
                    ErrorDialog.showMessageDialog("" , "No Group Found" , this);
                }
                else {
                    openGroupDialog();
                }
                break;
            case R.id.course_instructor_layout:
                Intent i = new Intent(this, InstructorActivity.class);
                i.putExtra(Constants.INTENT_ID, course.getInstructor().getId());
                startActivity(i);
                break;
        }
    }


    public void openGroupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams")
        View dialogView = inflater.inflate(R.layout.dialog_choose_group, null);

        Button submitButton = dialogView.findViewById(R.id.submit_button);
        ImageView closeImageView = dialogView.findViewById(R.id.dialog_close_imageView);
        recyclerView = dialogView.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        submitButton.setOnClickListener(v -> {
            if (submitCourseAdapter.getSelectedGroup() != null) {
                dialog.dismiss();
                Intent i = new Intent(CourseActivity.this, PaymentActivity.class);
                i.putExtra(Constants.INTENT_OBJECT, submitCourseAdapter.getSelectedGroup());
                startActivity(i);
                finish();
            } else {
                ErrorDialog.showMessageDialog(getString(R.string.invalid_request), "Select group.", this);
            }
        });

        closeImageView.setOnClickListener(v -> dialog.dismiss());

        if (groups != null) {
            submitCourseAdapter = new SubmitCourseAdapter(groups, this);
            recyclerView.setAdapter(submitCourseAdapter);
        } else {
            getGroups(course.getId());
        }
    }

    private void getGroups(String id) {
        GroupByCourseIdOperation operation = new GroupByCourseIdOperation(id, REQUEST_GET_GROUPS, false, this);
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
        } else if (requestId.equals(REQUEST_GET_GROUPS)) {
            groups = (List<Group>) resultObject;
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }
}
