package com.ioocllcdrdapp.iooc.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchFrom implements Serializable {

    @SerializedName("keyword")
    @Expose
    private String keyword;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("languageId")
    @Expose
    private String languageId;

    public SearchFrom(String keyword, String categoryId, String languageId) {
        this.keyword = keyword;
        this.categoryId = categoryId;
        this.languageId = languageId;
    }

    public SearchFrom() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
}
