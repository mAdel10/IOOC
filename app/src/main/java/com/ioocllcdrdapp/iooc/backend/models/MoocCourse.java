package com.ioocllcdrdapp.iooc.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MoocCourse implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("moocProvider")
    @Expose
    private MoocPlatform moocPlatform;

    public MoocCourse(String id, String name, String price, String image, MoocPlatform moocPlatform) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.moocPlatform = moocPlatform;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MoocPlatform getMoocPlatform() {
        return moocPlatform;
    }

    public void setMoocPlatform(MoocPlatform moocPlatform) {
        this.moocPlatform = moocPlatform;
    }
}
