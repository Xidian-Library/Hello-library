package io.junq.examples.boot;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import io.junq.examples.boot.Librarian;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
//
//
//
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AdminController{
    @Resource
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("/AdminLogin")
    public int Adminlogin(int id,String password) {
        int flag = 0;
        String sql="select * from admin where id=? and password=?";

        Admin adm = new Admin(id,password);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://114.55.250.159:3306/library", "root", "123");

            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, Integer.toString(adm.getId()));
            pstmt.setString(2, adm.getPassword());
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()){
                flag=1;
                System.out.println("Welcome back , admin "+adm.getName()+" !");
            }
            else {
                System.out.println("Try again!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    @RequestMapping("/ChangeAdminPassword")
    public int ChangeAdminPassword(int id,String oldpassword,String newpassword){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc="jdbc:mysql://114.55.250.159:3306/library";
            Connection conn=DriverManager.getConnection(jdbc, "root", "123");

            String sql="select * from admin where id=? and password=?";
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, Integer.toString(id));
            pstmt.setString(2, oldpassword);
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()){
                String sql1="update admin set password=? where id=?";
                Object[] ob= {newpassword,id};
                jdbcTemplate.update(sql1, ob);
            }
            else{
                System.out.println("account does not exist!");
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
    @RequestMapping("/RegisterLibrarian")
    public int RegisterLibrarian(String  id,String name){
        if("".equals(id)) {
            System.out.println("Invalid ID!");
            return 0;
        }
        if("".equals(name)) {
            System.out.println("Invalid ID!");
            return 0;
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc="jdbc:mysql://114.55.250.159:3306/library";
            Connection conn=DriverManager.getConnection(jdbc, "root", "123");
            Statement state=conn.createStatement();

            String sql="insert into librarian(id,password,name) value(?,'00010001',?)";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1,id);
            ps.setString(2,name);
            ps.executeUpdate();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
    @RequestMapping("/DeleteLibrarian")
    public int DeleteLibrarian(String id){
        try{
            String sql="delete from librarian where id=?";
            Object[] ob= {id};
            jdbcTemplate.update(sql, ob);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("account does not exist!");
            return 0;
        }
        return 1;
    }
    @RequestMapping("/EditLibrarianName")
    public int EditLibrarianName(String id,String newname){
        try{
            String sql="update librarian set name=? where id=?";
            Object[] ob= {newname,id};
            jdbcTemplate.update(sql, ob);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("account does not exist!");
            return 0;
        }
        return 1;
    }
    @RequestMapping("/EditLibrarianPassword")
    public int EditLibrarianPassword(String id,String newpassword){
        try{
            String sql="update librarian set password=? where id=?";
            Object[] ob= {newpassword,id};
            jdbcTemplate.update(sql, ob);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("account does not exist!");
            return 0;
        }
        return 1;
    }
    @RequestMapping("/SearchLibrarian")
    public List SearchLibrarian(String id) throws SQLException {
        String sql="select * from librarian where id=";
        sql=sql+"\""+id+"\"";
        List<Librarian> librarians = jdbcTemplate.query(sql, new RowMapper<Librarian>() {
            Librarian lib = null;
            @Override
            public Librarian mapRow(ResultSet rs, int rowNum) throws SQLException {
                lib = new Librarian();
                lib.setID(rs.getString("id"));
                lib.setPassword(rs.getString("password"));
                lib.setName(rs.getString("name"));

                return lib;
            }});
        return librarians;
    }

    @RequestMapping("/AllLibrarians")
    public List AllLibrarians() throws SQLException {
        String sql="select * from librarian ";

        List<Librarian> librarians = jdbcTemplate.query(sql, new RowMapper<Librarian>() {
            Librarian lib = null;
            @Override
            public Librarian mapRow(ResultSet rs, int rowNum) throws SQLException {
                lib = new Librarian();
                lib.setID(rs.getString("id"));
                lib.setPassword(rs.getString("password"));
                lib.setName(rs.getString("name"));
                return lib;
            }});
        return librarians;
    }
}