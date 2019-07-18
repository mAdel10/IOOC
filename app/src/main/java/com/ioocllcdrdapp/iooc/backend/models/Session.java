package com.ioocllcdrdapp.iooc.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Session implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("groupId")
    @Expose
    private String groupId;
    @SerializedName("materialsURL")
    @Expose
    private List<String> materials;
    @SerializedName("quizURL")
    @Expose
    private String quizURL;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("videoURL")
    @Expose
    private String videoURL;
    private String sessionNumber;



    public Session(String groupId, String videoURL, List<String> materials, String quizURL, String date) {
        this.groupId = groupId;
        this.materials = materials;
        this.quizURL = quizURL;
        this.date = date;
        this.videoURL = videoURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }

    public String getQuizURL() {
        return quizURL;
    }

    public void setQuizURL(String quizURL) {
        this.quizURL = quizURL;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(String sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

}
