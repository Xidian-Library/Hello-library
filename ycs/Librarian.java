package io.junq.examples.boot;

import java.io.Serializable;

public class Librarian{
    private String id;
    private String password;

    public String getID() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}