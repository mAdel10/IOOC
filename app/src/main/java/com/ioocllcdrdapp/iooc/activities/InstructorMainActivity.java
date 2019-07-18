package com.ioocllcdrdapp.iooc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Instructor;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.InstructorByIdOperation;
import com.ioocllcdrdapp.iooc.controllers.adapters.InstructorPageAdapter;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.managers.TokenManager;
import com.ioocllcdrdapp.iooc.managers.UserManager;
import com.ioocllcdrdapp.iooc.utilities.Utilities;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InstructorMainActivity extends BaseActivity implements RequestObserver {

    @BindView(R.id.instructor_main_imageView)
    ImageView instructorImageView;
    @BindView(R.id.instructor_main_name_textView)
    TextView instructorNameText;
    @BindView(R.id.instructor_main_email_textView)
    TextView instructorEmail;
    @BindView(R.id.instructor_main_addCourses_btn)
    Button addCourseBtn;
    @BindView(R.id.instructor_main_addGroups_btn)
    Button addGroupsBtn;
    @BindView(R.id.instructor_main_edit_btn)
    Button instructorMainEditBtn;

    private final static int REQUEST_INSTRUCTOR_BY_ID = 1;
    String id;

    public Instructor instructor;
    @BindView(R.id.instructor_main_logout_btn)
    Button instructorMainLogoutBtn;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentManager fm;
    InstructorPageAdapter pagerAdapter;


    public InstructorMainActivity() {
        super(R.layout.activity_instructor_main, true);
    }

    @Override
    protected void doOnCreate(Bundle bundle) {
        ButterKnife.bind(this);
        fm = (this).getSupportFragmentManager();
        tabLayout = findViewById(R.id.instructor_main_tabLayout);
        viewPager = findViewById(R.id.instructor_main_viewPager);

        id = UserManager.getInstance().getCurrentUser().getInstructor().getId();
        initViews();
        getInstructorById();
    }

    private void initViews() {
        pagerAdapter = new InstructorPageAdapter(fm);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void getInstructorById() {
        id = UserManager.getInstance().getCurrentUser().getInstructor().getId();
        InstructorByIdOperation operation = new InstructorByIdOperation(id, REQUEST_INSTRUCTOR_BY_ID,
                false, this);
        operation.addRequestObserver(this);
        operation.execute();
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
        } else if (requestId.equals(REQUEST_INSTRUCTOR_BY_ID)) {
            Instructor instructor = (Instructor) resultObject;
            if (instructor.getUser() != null) {
                Picasso.with(this)
                        .load(instructor.getUser().getImage())
                        .placeholder(R.drawable.img_placeholder)
                        .into(instructorImageView);

                String name = instructor.getUser().getFirstName() + " " + instructor.getUser().getLastName();
                instructorNameText.setText(name);

                String email = instructor.getUser().getEmail();
                instructorEmail.setText(email);
            }
        }
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }


    @OnClick({R.id.instructor_main_addCourses_btn, R.id.instructor_main_addGroups_btn,
            R.id.instructor_main_edit_btn, R.id.instructor_main_logout_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.instructor_main_edit_btn:
                Intent intent = new Intent(this, EditProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.instructor_main_addCourses_btn:
                 intent = new Intent(this, AddCourseActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.instructor_main_addGroups_btn:
                intent = new Intent(this, AddGroupActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.instructor_main_logout_btn:
                UserManager.getInstance().logout();
                TokenManager.getInstance().delete();
                intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}