package com.ioocllcdrdapp.iooc.controllers.holders;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;

public class ProfileCoursesHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView titleTextView;
    public ConstraintLayout layout;

    public ProfileCoursesHolder(View view) {
        super(view);
        imageView = view.findViewById(R.id.item_profile_course_imageView);
        titleTextView = view.findViewById(R.id.item_profile_course_textView);
        layout = view.findViewById(R.id.item_profile_layout);
    }
}
