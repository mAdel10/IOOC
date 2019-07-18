package com.ioocllcdrdapp.iooc.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CourseDetails implements Serializable {

    @SerializedName("popular")
    @Expose
    private List<Course> popularCourses;
    @SerializedName("topRated")
    @Expose
    private List<Course> topRatedCourses;
    @SerializedName("offered")
    @Expose
    private List<Course> offeredCourses;

    public CourseDetails(List<Course> popularCourses, List<Course> topRatedCourses, List<Course> offeredCourses) {
        this.popularCourses = popularCourses;
        this.topRatedCourses = topRatedCourses;
        this.offeredCourses = offeredCourses;
    }

    public CourseDetails() {

    }

    public List<Course> getPopularCourses() {
        return popularCourses;
    }

    public List<Course> getTopRatedCourses() {
        return topRatedCourses;
    }


    public List<Course> getOfferedCourses() {
        return offeredCourses;
    }

    public void setTopRatedCourses(List<Course> topRatedCourses) {
        this.topRatedCourses = topRatedCourses;
    }

    public void setOfferedCourses(List<Course> offeredCourses) {
        this.offeredCourses = offeredCourses;
    }

    public void setPopularCourses(List<Course> popularCourses) {
        this.popularCourses = popularCourses;

    }
}
