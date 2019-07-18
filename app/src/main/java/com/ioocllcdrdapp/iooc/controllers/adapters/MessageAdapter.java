package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Chat;
import com.ioocllcdrdapp.iooc.controllers.holders.MessageHolder;
import com.ioocllcdrdapp.iooc.managers.UserManager;

import java.util.Collection;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

    private List<Chat> chats;
    private Context context;

    public MessageAdapter(List<Chat> chats, Context context) {
        this.chats = chats;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        holder.messageTextView.setText(chats.get(position).getMessage());
        holder.dateTextView.setText(chats.get(position).getDate());

        String myId = UserManager.getInstance().getCurrentUser().getId();
        if (myId.equals(chats.get(position).getFrom())) {
            setUserView(holder);
        } else {
            setOther(holder, chats.get(position), getContext());
        }
    }

    private void setUserView(MessageHolder holder) {
        holder.messageLayout.setGravity(Gravity.END);
        holder.messageTextView.setBackground(context.getResources().getDrawable(R.drawable.back_message_accent));
        holder.senderImageView.setVisibility(View.GONE);
        holder.senderTextView.setVisibility(View.GONE);
    }

    private void setOther(MessageHolder holder, Chat chat, Context context) {
        holder.messageLayout.setGravity(Gravity.START);
        holder.messageTextView.setBackground(context.getResources().getDrawable(R.drawable.back_message_primary));
        if (chat.getUser() != null) {
            holder.senderTextView.setText(chat.getUserName());
            chat.loadImage(holder.senderImageView, context);
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @SuppressWarnings("unchecked")
    public void addItems(List<?> newItems) {
        chats.addAll((Collection<? extends Chat>) newItems);
        notifyDataSetChanged();
    }

    public void addItem(Chat newItem) {
        chats.add(newItem);
        notifyDataSetChanged();
    }
}