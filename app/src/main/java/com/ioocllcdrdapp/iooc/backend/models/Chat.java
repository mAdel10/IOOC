package com.ioocllcdrdapp.iooc.backend.models;

import android.content.Context;
import android.widget.ImageView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ioocllcdrdapp.iooc.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class Chat implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("groupId")
    @Expose
    private String groupId;

    public Chat(String id, String message, String from, String date) {
        this.id = id;
        this.message = message;
        this.from = from;
        this.date = date;
    }

    public Chat(String message, String date, String groupId) {
        this.message = message;
        this.date = date;
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    public void loadImage(ImageView imageView, Context context) {
        Picasso.with(context)
                .load(user.getImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.img_placeholder)
                .into(imageView);
    }
}