package com.ioocllcdrdapp.iooc.controllers.holders;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;

public class ChatHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView titleTextView;
    public TextView dateTextView;
    public ConstraintLayout layoutChat;

    public ChatHolder(View view) {
        super(view);
        imageView = view.findViewById(R.id.item_chat_imageView);
        titleTextView = view.findViewById(R.id.item_chat_title_textView);
        dateTextView = view.findViewById(R.id.item_chat_date_textView);
        layoutChat = view.findViewById(R.id.item_chat_constraint_layout);
    }
}