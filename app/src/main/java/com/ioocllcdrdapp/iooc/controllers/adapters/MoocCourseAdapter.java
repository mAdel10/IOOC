package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.activities.MoocCourseFormActivity;
import com.ioocllcdrdapp.iooc.backend.models.MoocCourse;
import com.ioocllcdrdapp.iooc.controllers.holders.MoocCourseHolder;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;

public class MoocCourseAdapter extends RecyclerView.Adapter<MoocCourseHolder> {

    private List<MoocCourse> moocCourses;
    private Context context;

    public MoocCourseAdapter(List<MoocCourse> moocCourses, Context context) {
        this.moocCourses = moocCourses;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public MoocCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mooc_course, parent, false);
        return new MoocCourseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoocCourseHolder holder, int position) {
        final MoocCourse moocCourse = moocCourses.get(position);

        Picasso.with(getContext())
                .load(moocCourse.getImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.img_placeholder)
                .into(holder.moocCourseImage);

        holder.moocCourseNameTV.setText(moocCourse.getName());
        holder.moocCoursePriceTV.setText(moocCourse.getPrice());
        holder.itemView.setOnClickListener( view -> {
            Intent intent = new Intent(getContext(), MoocCourseFormActivity.class);
            String[] moocCourseData = {moocCourse.getId(), moocCourse.getName(), moocCourse.getMoocPlatform().getName()};
            intent.putExtra(Constants.INTENT_OBJECT, moocCourseData);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return moocCourses.size();
    }
}