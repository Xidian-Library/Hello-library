package io.junq.examples.boot;
//author:ljx
import java.sql.*;

public class DBHelper {
    public static final String url = "jdbc:mysql://114.55.250.159:3306/library";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "123";

    public static  Connection conn = null;
    public static  PreparedStatement pst = null;
    public static Statement stmt = null;

    public static void BuildHelper(String sql) {
        try {
            Class.forName(name);
            conn = DriverManager.getConnection(url,user,password);
            System.out.println(conn);
            pst = conn.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void BuildHelper2(String sql) {
        try {
            Class.forName(name);
            conn = DriverManager.getConnection(url,user,password);
            System.out.println(conn);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void close() {
        try {
            conn.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}