package com.ioocllcdrdapp.iooc.controllers.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;

public class EnrollmentHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView titleTextView;
    public TextView passedTextView;
    public SeekBar seekBar;

    public EnrollmentHolder(View view) {
        super(view);
        imageView = view.findViewById(R.id.item_enrollment_imageView);
        titleTextView = view.findViewById(R.id.item_enrollment_title_textView);
        passedTextView = view.findViewById(R.id.item_enrollment_passed_textView);
        seekBar = view.findViewById(R.id.item_enrollment_seekBar);
    }
}
