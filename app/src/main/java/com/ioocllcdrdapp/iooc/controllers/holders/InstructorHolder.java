package com.ioocllcdrdapp.iooc.controllers.holders;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;

public class InstructorHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView nameTextView;
    public TextView position;
    public RatingBar ratingBar;
    public TextView ratingBarText;
    public ConstraintLayout layout;

    public InstructorHolder(View view) {
        super(view);

        imageView = view.findViewById(R.id.item_instructor_imageView);
        nameTextView = view.findViewById(R.id.item_instructor_name_textView);
        position = view.findViewById(R.id.item_instructor_position_textView);
        ratingBar = view.findViewById(R.id.item_instructor_ratingBar);
        ratingBarText = view.findViewById(R.id.item_instructor_rate_textView);
        layout = view.findViewById(R.id.item_instructor_constraint_layout);
    }
}