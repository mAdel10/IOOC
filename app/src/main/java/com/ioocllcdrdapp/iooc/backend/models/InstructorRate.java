package com.ioocllcdrdapp.iooc.backend.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstructorRate {

    @SerializedName("studentId")
    @Expose
    private String studentId;
    @SerializedName("instructorId")
    @Expose
    private String instructorId;
    @SerializedName("rate")
    @Expose
    private float rate;

    public InstructorRate(String studentId, String instructorId, float rate) {
        this.studentId = studentId;
        this.instructorId = instructorId;
        this.rate = rate;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
