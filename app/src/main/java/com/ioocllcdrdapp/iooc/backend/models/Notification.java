package com.ioocllcdrdapp.iooc.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Notification implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("created_add")
    @Expose
    private String date;
    @SerializedName("image")
    @Expose
    private String image;

    public Notification(String body, String date, String image) {
        this.body = body;
        this.date = date;
        this.image = image;
    }

    public Notification() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
