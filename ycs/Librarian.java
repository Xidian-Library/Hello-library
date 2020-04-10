package io.junq.examples.boot;

import java.io.Serializable;

public class Librarian{
    private String id;
    private String password;
    private String name;

    public String getID() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName(){return name;}

    public void setID(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

}
