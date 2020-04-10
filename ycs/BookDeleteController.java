//author:LiuJx
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

public class BookDeleteController {
    @Autowired
    private HelloService service;
    @Resource
    private JdbcTemplate jdbcTemplate;

    //test api
    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET, value = "/helloDeletedBook",
            produces = "text/plain")
    public String helloService() {
        return "Hello! Book Delete service!";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET, value = "/deleteABook",
            produces = "text/plain")
    //String admin_id, String book_barcode
    public String deleteABook(String admin_id, String book_barcode) {
        ResultSet ret = null;
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);
        String date = y + "/" + m + "/" + d;
        try {
            String sql = "select * from book where barcode ='" + book_barcode + "';";
            DBHelper.BuildHelper(sql);
            ret = DBHelper.pst.executeQuery();
            int count = 0;
            while (ret.next()) {
                count++;
            }
            if (count == 0) {
                return "This Book is Not exit.";
            }


            sql=" delete from book where barcode='" + book_barcode + "';";
            DBHelper.BuildHelper2(sql);
            DBHelper.stmt.executeUpdate(sql);


            sql = "insert into deleted_book values('" + admin_id + "','Only one book','" + book_barcode + "','" + date + "');";
            DBHelper.BuildHelper(sql);
            DBHelper.pst.execute();

            DBHelper.close();

            return "Success";
        } catch (SQLException sql) {
            return "false here1";
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET, value = "/deleteAKindBook",
            produces = "text/plain")
    public String deleteAKindBook(String admin_id, String book_id) {
        ResultSet ret = null;
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);
        String date = y + "/" + m + "/" + d;
        try {
            String sql = "select barcode from book where book_id = '" + book_id + "';";
            DBHelper.BuildHelper(sql);
            ret = DBHelper.pst.executeQuery();
            int count = 0;
            while (ret.next()) {
                count++;
            }
            if (count == 0) {
                return "This kind of Books are Not exit.";
            }
            sql = "delete  from book where book_id='" + book_id + "';";
            DBHelper.BuildHelper2(sql);
            DBHelper.stmt.executeUpdate(sql);

            sql = "insert into deleted_book values('" + admin_id + "','" + book_id + "','All books','" + date + "');";
            DBHelper.BuildHelper(sql);
            DBHelper.pst.execute();
            DBHelper.close();
            return "Success";
        } catch (SQLException sql) {
            return "false here2";
        }
    }
}

