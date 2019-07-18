package com.ioocllcdrdapp.iooc.backend.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseRate {

    @SerializedName("studentId")
    @Expose
    private String studentId;
    @SerializedName("courseId")
    @Expose
    private String courseId;
    @SerializedName("rate")
    @Expose
    private float rate;

    public CourseRate(String studentId, String courseId, float rate) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.rate = rate;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
