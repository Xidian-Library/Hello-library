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

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/mail")
@CrossOrigin(origins = "*")
public class MailController {
    @Autowired
    private JavaMailSender mailSender;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Scheduled(cron="0 0 9 * * * ")
    //发送普通邮箱
    @RequestMapping("/send")
    @ResponseBody
    public void send() {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        ModelMap map = new ModelMap();
        //查询数据库查看是否逾期未还书
        String sql="select * from borrow natural join readers natural join book where delay_time<>0 and return_time is null and borrowerid=id";
        List<Borrow> borrowList = jdbcTemplate.query(sql, new RowMapper<Borrow>() {
            Borrow borrow = null;

            @Override
            public Borrow mapRow(ResultSet rs, int rowNum) throws SQLException {
                borrow = new Borrow();
                borrow.setBookname(rs.getString("book_name"));
                borrow.setBorrowerid(rs.getString("borrowerid"));
                borrow.setBarcode(rs.getString("barcode"));
                borrow.setBorrowtime(rs.getDate("borrow_time"));
                borrow.setReturntime(rs.getDate("return_time"));
                borrow.setFine(rs.getFloat("fine"));
                borrow.setDelaytime(rs.getInt("delay_time"));
                borrow.setIspay(rs.getBoolean("ispay"));
                borrow.setEmail(rs.getString("email"));
                return borrow;
            }
        });
        String text=" ";
        String to=" ";
        for(Borrow bor : borrowList ){
            mailMessage.setSubject("Return The Book To Remind");
            text="The "+bor.getBookname()+"book you borrowed has not been returned, please return it as soon as possible.";
            mailMessage.setText(text);
            mailMessage.setFrom("bookmanager101@163.com");//发送者，本人邮箱
            to=bor.getEmail();
            mailMessage.setTo(to);//发送给他人邮箱
            mailSender.send(mailMessage);
        }


    }


}