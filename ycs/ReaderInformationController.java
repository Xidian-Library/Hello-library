package io.junq.examples.boot;

import java.util.Calendar;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.annotation.Resource;

import java.sql.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ReaderInformationController {
	@RequestMapping("/setemail")
    public static int setemail(String id,String email)
    {
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://114.55.250.159:3306/library";
            Connection conn = DriverManager.getConnection(jdbc, "root", "123");
            String sql="update readers set email=? where id=";
            sql=sql+"\""+id+"\"";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setObject(1, email);
            ps.executeUpdate();
            conn.close();
            return 0;
    	}
    	catch (Exception e) {
            e.printStackTrace();
        }
    	return 1;
    }
	@RequestMapping("/setpassword")
    public static int setpassword(String id,String password)
    {
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://114.55.250.159:3306/library";
            Connection conn = DriverManager.getConnection(jdbc, "root", "123");
            String sql="update readers set password=? where id=";
            sql=sql+"\""+id+"\"";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setObject(1, password);
            ps.executeUpdate();
            conn.close();
            return 0;
    	}
    	catch (Exception e) {
            e.printStackTrace();
        }
    	return 1;
    }
}
