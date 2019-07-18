package com.ioocllcdrdapp.iooc.controllers.holders;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;

public class SessionHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;
//    public ConstraintLayout layoutSession;

    public SessionHolder(View view) {
        super(view);
        titleTextView = view.findViewById(R.id.item_session_title_textView);
     //   layoutSession = view.findViewById(R.id.item_session_constraint_layout);
    }
}
