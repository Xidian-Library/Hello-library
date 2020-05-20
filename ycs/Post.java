package io.junq.examples.boot;

import java.io.Serializable;

import java.util.Date;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;

public class Post{

    private String title;

    private String photo;

    private String content;

    public String getTitle() {
        return title;
    }

    public String getPhoto() {
        return photo;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setContent(String content) {
        this.content = content;
    }

}