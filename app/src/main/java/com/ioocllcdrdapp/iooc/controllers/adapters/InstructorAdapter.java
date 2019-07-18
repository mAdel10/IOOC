package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.activities.InstructorActivity;
import com.ioocllcdrdapp.iooc.backend.models.Instructor;
import com.ioocllcdrdapp.iooc.controllers.holders.InstructorHolder;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorHolder> {

    private List<Instructor> instructors;
    private Context context;

    public InstructorAdapter(List<Instructor> instructors, Context context) {
        this.instructors = instructors;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public InstructorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_instructor, parent, false);
        return new InstructorHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorHolder holder, int position) {

        if (instructors.get(position).getUser() != null) {
            Picasso.with(getContext())
                    .load(instructors.get(position).getUser().getImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .resize(100, 100)
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .into(holder.imageView);

            String name = instructors.get(position).getUser().getFirstName() + " " + instructors.get(position).getUser().getLastName();
            holder.nameTextView.setText(name);
        }

        holder.position.setText(instructors.get(position).getPosition());
        holder.position.setText(instructors.get(position).getPosition());
        holder.ratingBar.setRating(instructors.get(position).getRate());

        if (instructors.get(position).getRateCount() != 0) {
            String rate = ((instructors.get(position).getRate() / instructors.get(position).getRateCount()) + "/5");
            holder.ratingBarText.setText(rate);
        } else {
            holder.ratingBarText.setText("0/5");
        }

        holder.layout.setOnClickListener(view -> {
            Intent i = new Intent(context, InstructorActivity.class);
            i.putExtra(Constants.INTENT_ID, instructors.get(position).getId());
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return instructors.size();
    }
}