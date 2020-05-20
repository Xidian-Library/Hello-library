package io.junq.examples.boot;

import java.io.Serializable;

import java.util.Date;

public class Readers{
    private String id;
    private String password;
    private String email;
    private Date register_time;

    public String getID() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName(){return email;}

    public Date getRegister_time(){return register_time;}

    public void setID(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegister_time(Date register_time) {
        this.register_time = register_time;
    }

}
