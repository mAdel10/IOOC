package com.ioocllcdrdapp.iooc.controllers.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;

public class MessageHolder extends RecyclerView.ViewHolder {

    public LinearLayout messageLayout;
    public TextView messageTextView;
    public ImageView senderImageView;
    public TextView senderTextView;
    public TextView dateTextView;

    public MessageHolder(View view) {
        super(view);
        messageLayout = view.findViewById(R.id.item_message_layout);
        messageTextView = view.findViewById(R.id.item_message_textView);
        senderTextView = view.findViewById(R.id.item_message_sender_textView);
        senderImageView = view.findViewById(R.id.item_message_sender_imageView);
        dateTextView = view.findViewById(R.id.item_message_date_textView);
    }
}