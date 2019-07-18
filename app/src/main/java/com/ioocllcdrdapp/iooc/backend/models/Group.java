package com.ioocllcdrdapp.iooc.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Group implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("process")
    @Expose
    private int process;
    @SerializedName("enrolledNumber")
    @Expose
    private int enrolledNumber;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("course")
    @Expose
    private Course course;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("timeTable")
    @Expose
    private Object timeTable;
    @SerializedName("courseId")
    @Expose
    private String courseId;
    @SerializedName("liveURL")
    @Expose
    private String liveURL;

    private boolean isSelected;

    public Object getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(Object timeTable) {
        this.timeTable = timeTable;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Group( String startDate, String courseId) {
        this.startDate = startDate;
        this.courseId = courseId;
    }

    public Group(int process, int enrolledNumber, String state, String id, Course course, String liveURL) {
        this.id = id;
        this.process = process;
        this.enrolledNumber = enrolledNumber;
        this.state = state;
        this.course = course;
        this.liveURL = liveURL;
    }

    public Group() {

    }

    public void setProcess(int process) {
        this.process = process;
    }

    public void setEnrolledNumber(int enrolledNumber) {
        this.enrolledNumber = enrolledNumber;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getProcess() {
        return process;
    }

    public int getEnrolledNumber() {
        return enrolledNumber;
    }

    public String getState() {
        return state;
    }

    public String getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getLiveURL() {
        return liveURL;
    }

    public void setLiveURL(String liveURL) {
        this.liveURL = liveURL;
    }
}
