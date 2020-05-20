package io.junq.examples.boot;

import java.io.Serializable;

import java.util.Date;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;

public class Borbook{

    private String title;

    private String date;


    public String getTitle() {
        return title;
    }

    public String getDate(){return date;}

    public void setTitle(String title) { this.title=title; }

    public void setDate(String date){ this.date=date;}


}