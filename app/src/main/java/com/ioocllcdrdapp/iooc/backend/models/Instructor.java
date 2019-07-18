package com.ioocllcdrdapp.iooc.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Instructor implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("dateOfBirth")
    @Expose
    private String birthDate;

    @SerializedName("cvUrl")
    @Expose
    private String cv;

    @SerializedName("rate")
    @Expose
    private Float rate;
    @SerializedName("rateCount")
    @Expose
    private int rateCount;
    @SerializedName("user")
    @Expose
    private User user;

    public Instructor(String id, String position, String birthDate,String cv, Float rate, int rateCount, User user) {
        this.id = id;
        this.position = position;
        this.birthDate = birthDate;
        this.cv = cv;
        this.rate = rate;
        this.rateCount = rateCount;
        this.user = user;
    }

    public Instructor() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRateCount(int rateCount) {
        this.rateCount = rateCount;
    }

    public String getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    public Float getRate() {
        return rate;
    }

    public int getRateCount() {
        return rateCount;
    }

    public User getUser() {
        return user;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getCv() {
        return cv;
    }
}
