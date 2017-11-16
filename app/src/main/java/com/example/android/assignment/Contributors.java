package com.example.android.assignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Contributors {
    @Expose
    @SerializedName("login")
    String name;
    @Expose
    @SerializedName("url")
    String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
