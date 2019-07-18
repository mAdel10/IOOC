package com.ioocllcdrdapp.iooc.controllers.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;

public class GroupHolder extends RecyclerView.ViewHolder {

    public CardView layout;
    public TextView groupName;
    public TextView groupStartDate;


    public GroupHolder(View view) {
        super(view);
        layout = view.findViewById(R.id.item_group_cardView);
        groupName = view.findViewById(R.id.item_group_course_name);
        groupStartDate = view.findViewById(R.id.item_group_course_start_date);
    }
}
