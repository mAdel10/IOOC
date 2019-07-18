package com.ioocllcdrdapp.iooc.controllers.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;

public class MoocCourseHolder extends RecyclerView.ViewHolder {

    public TextView moocCourseNameTV, moocCoursePriceTV;
    public ImageView moocCourseImage;
    public MoocCourseHolder(View view) {
        super(view);
        moocCourseNameTV = view.findViewById(R.id.item_mooc_course_title_textView);
        moocCoursePriceTV = view.findViewById(R.id.item_mooc_course_price_textView);
        moocCourseImage = view.findViewById(R.id.item_mooc_course_imageView);
    }
}