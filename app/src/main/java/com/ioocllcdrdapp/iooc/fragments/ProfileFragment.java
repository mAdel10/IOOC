package com.ioocllcdrdapp.iooc.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.backend.models.User;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.CourseOperation;
import com.ioocllcdrdapp.iooc.backend.operations.UserProfileOperation;
import com.ioocllcdrdapp.iooc.controllers.adapters.ProfileCoursesAdapter;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.managers.UserManager;
import com.ioocllcdrdapp.iooc.utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileFragment extends Fragment implements RequestObserver, View.OnClickListener {

    private ImageView profileImageView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;

    private TextView upcomingCoursesTextView;
    private TextView passedCoursesTextView;
    private TextView uncompletedCoursesTextView;

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;

    private ProfileCoursesAdapter rec1Adapter;
    private List<Course> upcomingCourses;

    private ProfileCoursesAdapter rec2Adapter;
    private List<Course> passedCourses;

    private ProfileCoursesAdapter rec3Adapter;
    private List<Course> uncompletedCourses;

    private Context context;

    private static final int REQUEST_USERS_PROFILE = 1;
    private static final int GET_COURSE_UPCOMING = 2;
    private static final int GET_COURSE_PASSED = 3;
    private static final int GET_COURSE_UNCOMPLETED = 4;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getActivity().getApplicationContext();
        init(view);

        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(context));

        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(context));

        recyclerView3 = view.findViewById(R.id.recyclerView3);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(context));

        if (Utilities.isConnected(context)) {
            fillData(UserManager.getInstance().getCurrentUser());
        } else {
            getUserProfile();
        }

        getProfileCoursesUpcoming();
        getProfileCoursesPassed();
        getProfileCoursesUncompleted();

        return view;
    }

    private void init(View view) {
        profileImageView = view.findViewById(R.id.profile_profile_imageView);
        nameTextView = view.findViewById(R.id.profile_name_textView);
        emailTextView = view.findViewById(R.id.profile_email_textView);
        phoneTextView = view.findViewById(R.id.profile_phone_textView);

        upcomingCoursesTextView = view.findViewById(R.id.profile_upcomingCourses_textView);
        upcomingCoursesTextView.setOnClickListener(this);

        passedCoursesTextView = view.findViewById(R.id.profile_passedCourses_textView);
        passedCoursesTextView.setOnClickListener(this);

        uncompletedCoursesTextView = view.findViewById(R.id.profile_uncompletedCourses_textView);
        uncompletedCoursesTextView.setOnClickListener(this);
    }

    private void getUserProfile() {
        UserProfileOperation operation = new UserProfileOperation(REQUEST_USERS_PROFILE,
                false, context);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void getProfileCoursesUpcoming() {
        CourseOperation operation = new CourseOperation(GET_COURSE_UPCOMING,
                false, context);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void getProfileCoursesPassed() {
        CourseOperation operation = new CourseOperation(GET_COURSE_PASSED,
                false, context);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void getProfileCoursesUncompleted() {
        CourseOperation operation = new CourseOperation(GET_COURSE_UNCOMPLETED,
                false, context);
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
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request),
                            getString(R.string.request_server_error), getActivity());

                } else if (code != 401) {
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request),
                            errorMsg, getActivity());
                }
            }
        } else if (resultObject != null) {
            switch ((int) requestId) {
                case REQUEST_USERS_PROFILE:
                    User user = (User) resultObject;
                    fillData(user);
                    break;

                case GET_COURSE_UPCOMING:
                    upcomingCourses = (List<Course>) resultObject;
                    rec1Adapter = new ProfileCoursesAdapter(upcomingCourses, context, getActivity());
                    recyclerView1.setAdapter(rec1Adapter);
                    break;

                case GET_COURSE_PASSED:
                    passedCourses = (List<Course>) resultObject;
                    rec2Adapter = new ProfileCoursesAdapter(passedCourses, context, getActivity());
                    recyclerView2.setAdapter(rec2Adapter);
                    break;

                case GET_COURSE_UNCOMPLETED:
                    uncompletedCourses = (List<Course>) resultObject;
                    rec3Adapter = new ProfileCoursesAdapter(uncompletedCourses, context, getActivity());
                    recyclerView3.setAdapter(rec3Adapter);
                    break;
            }
        }
    }

    private void fillData(User user) {
        Picasso.with(getActivity().getApplicationContext())
                .load(user.getImage())
                .placeholder(R.drawable.img_vector_placeholder)
                .into(profileImageView);
        String name = user.getFirstName() + " " + user.getLastName();

        nameTextView.setText(name);
        emailTextView.setText(user.getEmail());
        phoneTextView.setText(user.getPhone());
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_upcomingCourses_textView:
                if (recyclerView1.getVisibility() == View.GONE && upcomingCourses == null) {
                    getProfileCoursesUpcoming();
                } else if (recyclerView1.getVisibility() == View.VISIBLE) {
                    recyclerView1.setVisibility(View.GONE);
                } else {
                    recyclerView1.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.profile_passedCourses_textView:
                if (recyclerView2.getVisibility() == View.GONE && passedCourses == null) {
                    getProfileCoursesPassed();
                } else if (recyclerView2.getVisibility() == View.VISIBLE) {
                    recyclerView2.setVisibility(View.GONE);
                } else {
                    recyclerView2.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.profile_uncompletedCourses_textView:
                if (recyclerView3.getVisibility() == View.GONE && uncompletedCourses == null) {
                    getProfileCoursesUncompleted();
                } else if (recyclerView3.getVisibility() == View.VISIBLE) {
                    recyclerView3.setVisibility(View.GONE);
                } else {
                    recyclerView3.setVisibility(View.VISIBLE);
                }
                break;

        }
    }
}
