package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.activities.CourseActivity;
import com.ioocllcdrdapp.iooc.activities.SessionActivity;
import com.ioocllcdrdapp.iooc.activities.SessionDetailsActivity;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.controllers.holders.EnrollmentHolder;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.ioocllcdrdapp.iooc.utilities.MyApplication.getContext;

public class EnrollmentAdapter extends RecyclerView.Adapter<EnrollmentHolder> {

    private List<Group> groups;
    private Context context;

    public EnrollmentAdapter(List<Group> groups, Context context) {
        this.groups = groups;
        this.context = context;
    }

    @NonNull
    @Override
    public EnrollmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class_room, parent, false);
        return new EnrollmentHolder(v);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull EnrollmentHolder holder, int position) {
        Group group = groups.get(position);

        if (groups.get(position).getCourse() != null) {
            holder.titleTextView.setText(group.getCourse().getName());
            Picasso.with(getContext())
                    .load(groups.get(position).getCourse().getImage())
                    .placeholder(R.drawable.img_placeholder)
                    .into(holder.imageView);
        }

        String passed = "Passed: " + group.getProcess() + "%";
        holder.passedTextView.setText(passed);
        holder.seekBar.setProgress(group.getProcess());

        holder.itemView.setOnClickListener(v -> {
        });

        holder.seekBar.setOnTouchListener((view, motionEvent) -> true);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SessionActivity.class);
            intent.putExtra(Constants.INTENT_OBJECT, group);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}
