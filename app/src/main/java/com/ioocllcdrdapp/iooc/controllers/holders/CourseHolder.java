package com.ioocllcdrdapp.iooc.controllers.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;

public class CourseHolder extends RecyclerView.ViewHolder {

    public CardView layout;
    public ImageView imageView;
    public TextView titleTextView;
    public TextView instructorTextView;
    public TextView durationTextView;
    public TextView priceTextView;
    public RatingBar ratingBar;
    public TextView rateTextView;

    public CourseHolder(View view) {
        super(view);
        layout = view.findViewById(R.id.item_course_cardView);
        imageView = view.findViewById(R.id.item_course_imageView);
        titleTextView = view.findViewById(R.id.item_course_title_textView);
        instructorTextView = view.findViewById(R.id.item_course_instructor_name_textView);
        durationTextView = view.findViewById(R.id.item_course_duration_textView);
        priceTextView = view.findViewById(R.id.item_course_price_textView);
        ratingBar = view.findViewById(R.id.item_course_ratingBar);
        rateTextView = view.findViewById(R.id.item_course_rate_textView);
    }
}