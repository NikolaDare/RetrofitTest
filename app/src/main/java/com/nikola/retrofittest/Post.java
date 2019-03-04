package com.nikola.retrofittest;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("userId")
    private int user;

    private int id;

    private String title;

    @SerializedName("body")
    private String text;

    public int getUser() {
        return user;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
