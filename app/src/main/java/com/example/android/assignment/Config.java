package com.example.android.assignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Config {
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
@Expose
        @SerializedName("description")
    String description;

    public String getRepoid() {
        return repoid;
    }

    public void setRepoid(String repoid) {
        this.repoid = repoid;
    }
 @Expose
        @SerializedName("id")
    String repoid;
    Owner owner;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    @SerializedName("watchers")
    @Expose
    String id;
    @SerializedName("full_name")
    @Expose
    String fullname;
    @SerializedName("name")
    @Expose
    String name;
    public class Owner{
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAvatarurl() {
            return avatarurl;
        }

        public void setAvatarurl(String avatarurl) {
            this.avatarurl = avatarurl;
        }

        @SerializedName("url")
        @Expose
        String url;
        @SerializedName("avatar_url")
        @Expose
        String avatarurl;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        @Expose
        @SerializedName("login")
        String user;

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }

        @Expose
        @SerializedName("html_url")
        String html_url;

    }
}
