package com.ioocllcdrdapp.iooc.backend.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GroupID implements Serializable {

    @SerializedName("groupId")
    @Expose
    private String groupId;

    public GroupID(String groupId) {
        this.groupId = groupId;
    }

    public GroupID() {
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }


}
