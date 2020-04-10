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


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HelloRestController {
    @Autowired
    private HelloService service;

    @Resource
    private JdbcTemplate jdbcTemplate;

    //helloworld
    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET, value = "/hello",
             produces = "text/plain")
    public String hello() {
        return "Hello world,hello! Spring Boot rocks.";
    }

    @RequestMapping("/test")
    @ResponseBody
    public int test(String id, String password){
        System.out.println(id);
        System.out.println(password);
        return 1;
    }


    @RequestMapping("/login")
    public int login(String id, String password){
        ModelMap map = new ModelMap();
        String sql = "SELECT * FROM librarian";
        List<Librarian> libList = jdbcTemplate.query(sql, new RowMapper<Librarian>() {
            Librarian lib = null;
            @Override
            public Librarian mapRow(ResultSet rs, int rowNum) throws SQLException {
                lib = new Librarian();
                lib.setID(rs.getString("id"));
                lib.setPassword(rs.getString("password"));
                return lib;
            }});
        for(Librarian lib:libList){

            if(lib.getID().equals(id)){
                if(lib.getPassword().equals(password)) {
                    System.out.println("YES");
                    return 1;
                }
                else{
                    return -1;
                }
            }
            System.out.println(lib.getID());
            System.out.println(lib.getPassword());
        }
        map.addAttribute("id", libList);

        return 0;
    }
    @RequestMapping("/signup")
    public int signup(String  id){
     try{
     Class.forName("com.mysql.jdbc.Driver");
     String jdbc="jdbc:mysql://114.55.250.159:3306/library";
    Connection conn=DriverManager.getConnection(jdbc, "root", "123");
    Statement state=conn.createStatement();
    ResultSet rset = state.executeQuery("select * from readers");
    int sign=0;
  while(rset.next()) {
      String name=rset.getString("id");
    if(name.equals(id)){
    System.out.println("This account has existed!please try another one.");
   sign=1;
    }
    }
    if(sign==0)
    {
     String sql="insert into readers(id,password) value(?,'123456')";
     PreparedStatement ps=conn.prepareStatement(sql);
     ps.setString(1,id);
     ps.executeUpdate();
    }
    }
    catch (Exception e) {
        e.printStackTrace();
    }
return 1;
    }


}
