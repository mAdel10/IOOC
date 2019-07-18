package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.backend.models.Notification;
import com.ioocllcdrdapp.iooc.controllers.holders.NotificationHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> {

    private List<Notification> notifications;
    private Context context;

    public NotificationAdapter(List<Notification> groups, Context context) {
        this.notifications = groups;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        if (notifications.get(position).getImage() != null) {
            Picasso.with(getContext())
                    .load(notifications.get(position).getImage())
                    .placeholder(R.drawable.img_placeholder)
                    .into(holder.imageView);
        }

        holder.bodyTextView.setText(notifications.get(position).getBody());
        holder.dateTextView.setText(notifications.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}