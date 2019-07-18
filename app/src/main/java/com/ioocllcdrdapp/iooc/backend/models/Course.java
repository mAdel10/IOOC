package com.ioocllcdrdapp.iooc.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Course implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("numberOfSessions")
    @Expose
    private String numberOfSessions;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("priceAfterDiscount")
    @Expose
    private double priceAfterDiscount;
    @SerializedName("rate")
    @Expose
    private float rate;
    @SerializedName("rateCount")
    @Expose
    private float rateCount;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("isOffered")
    @Expose
    private boolean isOffered;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("maxNumbers")
    @Expose
    private int maxNumbers;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("languageId")
    @Expose
    private String languageId;
    @SerializedName("instructor")
    @Expose
    private Instructor instructor;



    public Course(String id, String name, String numberOfSessions, double price,
                  double priceAfterDiscount, float rate, float rateCount, String image,
                  boolean isOffered, String description, String content, int maxNumbers,
                  String categoryId, String languageId, Instructor instructor) {
        this.id = id;
        this.name = name;
        this.numberOfSessions = numberOfSessions;
        this.price = price;
        this.priceAfterDiscount = priceAfterDiscount;
        this.rate = rate;
        this.rateCount = rateCount;
        this.image = image;
        this.isOffered = isOffered;
        this.description = description;
        this.content = content;
        this.maxNumbers = maxNumbers;
        this.categoryId = categoryId;
        this.languageId = languageId;
        this.instructor = instructor;
    }

    public Course(String name, String description, String content, int maxNumbers,
                  String numberOfSessions, double price ,String categoryId, String languageId) {
        this.name = name;
        this.numberOfSessions = numberOfSessions;
        this.price = price;
        this.description = description;
        this.content = content;
        this.maxNumbers = maxNumbers;
        this.categoryId = categoryId;
        this.languageId = languageId;
    }

    public Course() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(String numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(double priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getRateCount() {
        return rateCount;
    }

    public void setRateCount(float rateCount) {
        this.rateCount = rateCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isOffered() {
        return isOffered;
    }

    public void setOffered(boolean offered) {
        isOffered = offered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMaxNumbers() {
        return maxNumbers;
    }

    public void setMaxNumbers(int maxNumbers) {
        this.maxNumbers = maxNumbers;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return name;
    }
}





