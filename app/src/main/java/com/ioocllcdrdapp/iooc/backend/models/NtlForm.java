package com.ioocllcdrdapp.iooc.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NtlForm implements Serializable {
    @SerializedName("studentId")
    @Expose String studentId;

    @SerializedName("studentName")
    @Expose String studentName;

    @SerializedName("moocCourseId")
    @Expose String moocCourseId;

    @SerializedName("university")
    @Expose String university;

    @SerializedName("universityId")
    @Expose String universityId;

    @SerializedName("nationalId")
    @Expose String nationalId;

    @SerializedName("faculty")
    @Expose String faculty;


    public NtlForm(String studentId, String studentName, String moocCourseId, String university, String universityId, String nationalId, String faculty) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.moocCourseId = moocCourseId;
        this.university = university;
        this.universityId = universityId;
        this.nationalId = nationalId;
        this.faculty = faculty;
    }
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMoocCourseId() {
        return moocCourseId;
    }

    public void setMoocCourseId(String moocCourseId) {
        this.moocCourseId = moocCourseId;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}

