package io.junq.examples.boot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.text.SimpleDateFormat;
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
import io.junq.examples.boot.Librarian;
import io.junq.examples.boot.Borrow;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import io.junq.examples.boot.Borrower;
import io.junq.examples.boot.Fine;
import io.junq.examples.boot.Borbook;
import io.junq.examples.boot.Readers;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/service")
@CrossOrigin(origins = "*")
public class ReaderService {
    @Autowired
    private JavaMailSender mailSender;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/getpassword")
    @ResponseBody

    public void RetrievePassword(String id)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        ModelMap map = new ModelMap();
        //查询数据库查看是否逾期未还书
        String sql="select * from readers where id="+ "'" + id + "'";
        List<Readers> readersList = jdbcTemplate.query(sql, new RowMapper<Readers>() {
            Readers readers = null;

            public Readers mapRow(ResultSet rs, int rowNum) throws SQLException {
                readers = new Readers();
                readers.setID(rs.getString("id"));
                readers.setPassword(rs.getString("password"));
                readers.setEmail(rs.getString("email"));
                return readers;
            }
        });
        String text=" ";
        String to=" ";
        for(Readers read : readersList ){
            mailMessage.setSubject("Retrieve  Password");
            text="Hello  "+read.getID()+",your password is "+read.getPassword();
            mailMessage.setText(text);
            mailMessage.setFrom("bookmanager101@163.com");//发送者，本人邮箱
            to=read.getName();
            mailMessage.setTo(to);//发送给他人邮箱
            mailSender.send(mailMessage);
        }
        System.out.println("sucess!");
    }

}