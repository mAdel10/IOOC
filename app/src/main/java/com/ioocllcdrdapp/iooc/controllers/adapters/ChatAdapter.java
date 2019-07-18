package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.activities.ChatActivity;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.controllers.holders.ChatHolder;
import com.ioocllcdrdapp.iooc.controllers.holders.CourseHolder;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {

    private List<Group> groups;
    private Context context;

    public ChatAdapter(List<Group> groups, Context context) {
        this.groups = groups;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        Group group = groups.get(position);
        holder.dateTextView.setText(group.getStartDate());

        if (group.getCourse() != null) {
            holder.titleTextView.setText(group.getCourse().getName());
            Picasso.with(getContext())
                    .load(group.getCourse().getImage())
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .resize(100, 100)
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .into(holder.imageView);
        }

        holder.layoutChat.setOnClickListener(view -> {
            Intent i = new Intent(context, ChatActivity.class);
            i.putExtra(Constants.INTENT_ID, groups.get(position).getId());
            i.putExtra(Constants.INTENT_KEY, groups.get(position).getCourse().getName());
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}