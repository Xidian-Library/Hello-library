package io.junq.examples.boot;

import java.io.Serializable;
import java.sql.*;


public class Admin{
    private int id;
    private String name;
    private String password;

    public Admin(int id,String password) {
        this.id = id;
        this.password = password;
    }

    public int getId() { return this.id; }
    public String getPassword() { return this.password; }
    public void setID(int id) { this.id = id; }
    public void setPassword(String password) { this.password = password; }

    public String getName() throws SQLException ,ClassNotFoundException{
        String username = " ";
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn= DriverManager.getConnection("jdbc:mysql://114.55.250.159:3306/library", "root", "123");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name FROM admin where id='"+id+"';");
        if(rs.next()) {
            username = rs.getString("name");
        }
        return username;
    }
}