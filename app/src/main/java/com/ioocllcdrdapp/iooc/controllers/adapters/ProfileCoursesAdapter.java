package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.activities.CertificateActivity;

import com.ioocllcdrdapp.iooc.activities.CourseRateActivity;
import com.ioocllcdrdapp.iooc.activities.InstructorRateActivity;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.controllers.holders.ProfileCoursesHolder;

import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileCoursesAdapter extends RecyclerView.Adapter<ProfileCoursesHolder> {


    private List<Course> courses;
    private Context context;
    private Activity activity;


    public ProfileCoursesAdapter(List<Course> courses, Context context) {
        this.courses = courses;
        this.context = context;
    }

    public ProfileCoursesAdapter(List<Course> courses, Context context, Activity activity) {
        this.courses = courses;
        this.context = context;
        this.activity = activity;
    }

    private Context getContext() {
        return context;
    }


    @NonNull
    @Override
    public ProfileCoursesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_course, parent, false);
        return new ProfileCoursesHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileCoursesHolder holder, int position) {
        Course course = courses.get(position);

        Picasso.with(getContext())
                .load(course.getImage())
                .placeholder(R.drawable.img_placeholder)
                .into(holder.imageView);

        holder.titleTextView.setText(course.getName());

        holder.layout.setOnClickListener(view -> {


            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            @SuppressLint("InflateParams")
            View dialogView = inflater.inflate(R.layout.dialog_option_courses, null);

            Button certificateButton = dialogView.findViewById(R.id.certificate_button);
            Button courseRateButton = dialogView.findViewById(R.id.rate_course_button);
            Button instructorRateButton = dialogView.findViewById(R.id.rate_instructor_button);
            ImageView closeImageView = dialogView.findViewById(R.id.dialog_close_option_imageView);

            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialog.show();


            certificateButton.setOnClickListener(v -> {
                Intent i = new Intent(context, CertificateActivity.class);
                i.putExtra(Constants.INTENT_OBJECT, course);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                dialog.dismiss();
            });
            courseRateButton.setOnClickListener(v -> {
                Intent i = new Intent(context, CourseRateActivity.class);
                i.putExtra(Constants.INTENT_OBJECT, course.getId());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                dialog.dismiss();
            });

            instructorRateButton.setOnClickListener(v -> {
                Intent i = new Intent(context, InstructorRateActivity.class);
                i.putExtra(Constants.INTENT_OBJECT, course.getInstructor().getId());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                dialog.dismiss();
            });
            closeImageView.setOnClickListener(v -> {
                dialog.dismiss();
            });
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }


}
