package com.ioocllcdrdapp.iooc.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.backend.models.CourseDetails;
import com.ioocllcdrdapp.iooc.backend.observers.CTHttpError;
import com.ioocllcdrdapp.iooc.backend.observers.RequestObserver;
import com.ioocllcdrdapp.iooc.backend.operations.CourseDetailsOperation;
import com.ioocllcdrdapp.iooc.backend.operations.CourseOperation;
import com.ioocllcdrdapp.iooc.controllers.adapters.CourseAdapter;
import com.ioocllcdrdapp.iooc.dialogs.ErrorDialog;
import com.ioocllcdrdapp.iooc.utilities.Utilities;

import java.util.List;
import java.util.Objects;

public class CoursesFragment extends Fragment implements RequestObserver {

    private RecyclerView popularCoursesRecyclerView;
    private RecyclerView topRatedCoursesRecyclerView;
    private RecyclerView offeredCoursesRecyclerView;

    private ConstraintLayout popularCoursesLayout;
    private ConstraintLayout topRatedCoursesLayout;
    private ConstraintLayout offeredCoursesLayout;

    private View loadingView;

    private CourseAdapter popularAdapter;
    private CourseAdapter topRatedAdapter;
    private CourseAdapter offeredAdapter;

    private Context context;

    private final static int REQUEST_COURSE = 1;

    public CoursesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        context = Objects.requireNonNull(getActivity()).getApplicationContext();

        init(view);
        initRV(view);
        getCourseDetails();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    private void getCourseDetails() {
        CourseDetailsOperation operation = new CourseDetailsOperation(REQUEST_COURSE, false, context);
        operation.addRequestObserver(this);
        operation.execute();
    }

    private void init(View view) {
        popularCoursesLayout = view.findViewById(R.id.fragment_home_popular_courses_layout);
        topRatedCoursesLayout = view.findViewById(R.id.fragment_home_top_rated_layout);
        offeredCoursesLayout = view.findViewById(R.id.fragment_home_offered_layout);

        loadingView = view.findViewById(R.id.loading_view);
    }

    private void initRV(View view) {
        popularCoursesRecyclerView = view.findViewById(R.id.home_popularCourses_recycler_view);
        topRatedCoursesRecyclerView = view.findViewById(R.id.fragment_home_top_rated_recycler_view);
        offeredCoursesRecyclerView = view.findViewById(R.id.fragment_home_offered_recycler_view);

        popularCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        topRatedCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        offeredCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
    }


    @Override
    public void handleRequestFinished(Object requestId, Throwable error, Object resultObject) {
        if (error != null) {
            if (error instanceof CTHttpError) {
                int code = ((CTHttpError) error).getStatusCode();
                String errorMsg = ((CTHttpError) error).getErrorMsg();
                if (code == -1 || Utilities.isNullString(errorMsg)) {
                    ErrorDialog.showMessageDialog(getString(R.string.invalid_request), context.getString(R.string.request_server_error), getActivity());
                } else if (code != 401) {
                    ErrorDialog.showMessageDialog(context.getString(R.string.invalid_request), errorMsg, getActivity());
                }
            }
        } else if (requestId.equals(REQUEST_COURSE)) {
            CourseDetails courseDetails = (CourseDetails) resultObject;

            popularAdapter = new CourseAdapter(courseDetails.getPopularCourses(), context);
            popularCoursesRecyclerView.setAdapter(popularAdapter);

            topRatedAdapter = new CourseAdapter(courseDetails.getTopRatedCourses(), context);
            topRatedCoursesRecyclerView.setAdapter(topRatedAdapter);

            offeredAdapter = new CourseAdapter(courseDetails.getOfferedCourses(), context);
            offeredCoursesRecyclerView.setAdapter(offeredAdapter);
        }

        loadingView.setVisibility(View.GONE);

        popularCoursesLayout.setVisibility(View.VISIBLE);
        topRatedCoursesLayout.setVisibility(View.VISIBLE);
        offeredCoursesLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestCanceled(Integer requestId, Throwable error) {

    }

    @Override
    public void updateStatus(Integer requestId, String statusMsg) {

    }
}
