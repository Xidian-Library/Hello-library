package io.junq.examples.boot;

import javax.annotation.Resource;
import java.util.*;
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
import io.junq.examples.boot.Librarian;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import sun.misc.Request;

import javax.annotation.Resource;
import java.sql.*;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "*")
public class BookAddControlller {
    @Autowired
    private HelloService service;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET, value = "/bookhello",
            produces = "text/plain")
    public String hello() {
        return "Hello world,hello??? ljx Spring Boot rocks.";
    }
    public static String getCard(){
        Random rand=new Random();//生成随机数
        String cardNnumer="";
        for(int a=0;a<6;a++){
            cardNnumer+=rand.nextInt(10);//生成6位数字
        }
        return cardNnumer;
    }

    public static String getNotRepeatId() {
        ResultSet ret = null;
        String cardNnumer=getCard();
        String new_bookId = "";
        int judge = 1;
        try {
            String sql = "select * from book;";
            DBHelper.BuildHelper(sql);
            ret = DBHelper.pst.executeQuery();
            int count = 0;
            while(ret.next()){
                count++;
            }
            Object[][] info2 = new Object[count][12];
            count = 0;
            DBHelper.BuildHelper(sql);
            ret = DBHelper.pst.executeQuery();
            while(ret.next()){
                info2[count][0] = ret.getString(6);
                if(info2[count][0].equals(cardNnumer)){
                    judge = 0;
                }
                count++;
            }
            if(judge==1)
                 new_bookId=cardNnumer;
            else
                new_bookId=getNotRepeatId();
        } catch (SQLException sql) {
            return "false here1";
        }
        return new_bookId;
    }
    public String getCopyId(String book_id){
        ResultSet ret = null;
        try {
            String sql = "SELECT * FROM book WHERE book_id='"+book_id+"';";
            DBHelper.BuildHelper(sql);
            ret = DBHelper.pst.executeQuery();
            int count = 0;
            while(ret.next()){
                count++;
            }
            int copyid = count+1;
            String answer = null;
            if(copyid<10){
                answer = "000"+copyid;
            }
            if(copyid>=10&&copyid<100){
                answer = "00"+copyid;
            }
            if(copyid>=100&&copyid<100){
                answer = "0"+copyid;
            }
            return answer;

        } catch (SQLException sql) {
            return "false here1";
        }
    }

    @RequestMapping(value = "/add_new_book")
    @ResponseBody
    public String add_newbook(String book_name,String book_author,String book_publisher,String book_date,String book_id,String book_type,String book_address,String book_price,String book_brief) {
        String barcode = book_id+getCopyId(book_id);
        String sql="insert into  book values ('"+book_name +"','"+book_author+"','"+ book_publisher+"','"+
               book_date+"','"+ book_id+"',0,'"+ book_type+"','" + book_address+"','"+book_price+"','no','no','"+book_brief+"','"+barcode+"');";
        DBHelper.BuildHelper(sql);
        try {
            DBHelper.pst.execute();
            DBHelper.close();
        } catch (SQLException err) {
            return "Database insert Fail";
        }
        return "Success";

    }

}
