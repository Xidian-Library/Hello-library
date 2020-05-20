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
//import sun.misc.Request;
import javax.annotation.Resource;
import java.sql.*;


@RestController
@RequestMapping("/reader")
@CrossOrigin(origins = "*")

public class OperateReaderController {
    @Autowired
    private HelloService service;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET, value = "/readerhello",
            produces = "text/plain")
    public String hello() {
        return "Hello world, ljx Spring Boot rocks test!.";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET, value = "/deletereader",
            produces = "text/plain")
    public String deleteReader(String id) {

        ResultSet ret = null;
        try {
            String sql = "select * from readers where id ='" + id + "';";
            DBHelper.BuildHelper(sql);
            ret = DBHelper.pst.executeQuery();
            int count = 0;
            while (ret.next()) {
                count++;
            }
            if (count == 0) {
                return "Not exit this person.";
            }

            sql = "select * from borrow where borrowerid ='" + id + "' and  fine!=0 ;";
            count = 0;
            DBHelper.BuildHelper(sql);
            ret = DBHelper.pst.executeQuery();
            while (ret.next()) {
                count++;
            }
            if (count != 0) {
                return "This reader has fine that has not been paid yet.";
            }

            sql = "select * from borrow where borrowerid ='" + id + "' and  return_time is null;";
            count = 0;
            DBHelper.BuildHelper(sql);
            ret = DBHelper.pst.executeQuery();
            while (ret.next()) {
                count++;
            }
            if (count != 0) {
                return "This reader has book that has not been returned yet.";
            }

            sql = " delete from readers where id='" + id + "';";
            DBHelper.BuildHelper2(sql);
            DBHelper.stmt.executeUpdate(sql);
            DBHelper.close();
            return "Success";
        } catch (SQLException sql) {
            return "Failure";
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET, value = "/returnreaderinfo",
            produces = "text/plain")
    public String returnReaderInfo(String id) {
        ResultSet ret = null;
        try {
            String sql = "select * from readers where id ='" + id + "';";
            DBHelper.BuildHelper(sql);
            ret = DBHelper.pst.executeQuery();
            int count = 0;
            while (ret.next()) {
                count++;
            }
            if (count == 0) {
                return "Not exit this person.";
            }

            Object[] info1 = new Object[2];
            DBHelper.BuildHelper(sql);
            ret = DBHelper.pst.executeQuery();
            while (ret.next()) {
                info1[0] = ret.getString(2);
                info1[1] = ret.getString(3);
            }
            String info = info1[0] + "|||" + info1[1];
            return info;
        } catch (SQLException sql) {
            return "Failure";
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET, value = "/modeifyreaderinfo",
            produces = "text/plain")
    public String returnReaderInfo(String id, String password, String email) {
        ResultSet ret = null;
        try {
            String sql = "  UPDATE readers SET password='" + password + "' WHERE id='" + id + "';";
            DBHelper.BuildHelper2(sql);
            DBHelper.stmt.executeUpdate(sql);

            sql = "  UPDATE readers SET email='" + email + "'  WHERE id='" + id + "';";
            DBHelper.BuildHelper2(sql);
            DBHelper.stmt.executeUpdate(sql);
            DBHelper.close();
            return "Success";
        } catch (SQLException sql) {
            return "Failure";
        }
    }
}