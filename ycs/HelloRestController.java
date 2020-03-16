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
        return "Hello world, Spring Boot rocks.";
    }

    @RequestMapping("/test")
    @ResponseBody
    public int test(String id, String password){
        System.out.println(id);
        System.out.println(password);
        return 0;
    }

    //账户登录
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
            //如果发现相等，返回1
            if(lib.getID().equals(id)){
                if(lib.getPassword().equals(password)) {
                    System.out.println("YES");
                    return 1;
                }
            }
            System.out.println(lib.getID());
            System.out.println(lib.getPassword());
        }
        map.addAttribute("id", libList);
        //如果没有匹配数据，返回0
        return 0;
    }
}
