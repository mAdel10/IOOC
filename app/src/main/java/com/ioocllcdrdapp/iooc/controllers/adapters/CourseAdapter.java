package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.activities.CourseActivity;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.controllers.holders.CourseHolder;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseHolder> {

    private List<Course> courses;
    private Context context;
    private int mode;

    public static int PORTRAIT = R.layout.item_course;
    public static int LAND_SCAPE = R.layout.item_course_landscape;

    public CourseAdapter(List<Course> courses, Context context, int mode) {
        this.courses = courses;
        this.context = context;
        this.mode = mode;
    }

    public CourseAdapter(List<Course> courses, Context context) {
        this.courses = courses;
        this.context = context;
        this.mode = R.layout.item_course;
    }

    private Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mode, parent, false);
        return new CourseHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        final Course course = courses.get(position);

        Picasso.with(getContext())
                .load(course.getImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.img_placeholder)
                .into(holder.imageView);

        holder.titleTextView.setText(course.getName());
        if (course.getInstructor() != null && course.getInstructor().getUser() != null) {
            String name = course.getInstructor().getUser().getFirstName() + " " + course.getInstructor().getUser().getLastName();
            holder.instructorTextView.setText(name);
        }

        holder.durationTextView.setText(String.valueOf(course.getNumberOfSessions()));
        holder.priceTextView.setText(String.valueOf(course.getPrice()));
        holder.ratingBar.setRating(course.getRate());

        if (course.getRateCount() != 0) {
            String rate = ((course.getRate() / course.getRateCount()) + "/5");
            holder.rateTextView.setText(rate);
        } else {
            holder.rateTextView.setText("0/5");
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CourseActivity.class);
            intent.putExtra(Constants.INTENT_OBJECT, course);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}