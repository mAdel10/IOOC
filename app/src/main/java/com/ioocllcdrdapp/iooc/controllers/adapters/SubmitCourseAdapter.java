package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Course;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.controllers.holders.SubmitCourseHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubmitCourseAdapter extends RecyclerView.Adapter<SubmitCourseHolder> {

    private List<Group> groups;
    private Context context;

    public SubmitCourseAdapter(List<Group> groups, Context context) {
        this.groups = groups;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public SubmitCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_alert_dialog, parent, false);
        return new SubmitCourseHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull SubmitCourseHolder holder, int position) {

        holder.groupTextView.setText(groups.get(position).getStartDate());
        if (groups.get(position).isSelected()) {
            holder.groupCheckBox.setChecked(true);
        } else {
            holder.groupCheckBox.setChecked(false);
        }

        holder.groupCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkedBox(position);
            }
        });

//        holder.itemView.setOnClickListener(v -> {
//            checkedBox();
//            holder.groupCheckBox.setChecked(true);
//        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    private void checkedBox(int position) {
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setSelected(false);
        }
        groups.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    public Group getSelectedGroup(){
        for (int i = 0; i < groups.size(); i++) {
            if(groups.get(i).isSelected()) return groups.get(i);
        }
        return null;
    }
}

