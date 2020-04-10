package io.junq.examples.boot;
import java.sql.Date;
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


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BookInquireController {
    @Autowired
    private HelloService service;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/bookinquire")
    public List bookinquire(String keytype, String key) {
        ModelMap map = new ModelMap();
        String sql = " ";
        if (keytype.equals("book_name")) {
            sql = "select * from book where book_name=" + "'" + key + "'";
        } else if (keytype.equals("book_author")) {
            sql = "select * from book where book_author=" + "'" + key + "'";
        } else if (keytype.equals("book_publisher")) {
            sql = "select * from book where book_publisher=" + "'" + key + "'";
        } else if (keytype.equals("book_date")) {
            sql = "select * from book where book_date=" + "'" + key + "'";
        } else if (keytype.equals("book_id")) {
            sql = "select * from book where book_id=" + "'" + key + "'";
        }
        else if (keytype.equals("copy_id")) {
            sql = "select * from book where copy_id=" + "'" + key + "'";
        }
        List<Book> bookList = jdbcTemplate.query(sql, new RowMapper<Book>() {
            Book book = null;

            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                book = new Book();
                book.setName(rs.getString("book_name"));
                book.setAuthor(rs.getString("book_author"));
                book.setPublisher(rs.getString("book_publisher"));
                book.setAuthor(rs.getString("book_author"));
                book.setDate(rs.getString("book_date"));
                book.setBookid(rs.getString("book_id"));
                book.setCopyid(rs.getInt("copy_id"));
                book.setType(rs.getString("book_type"));
                book.setAddress(rs.getString("book_address"));
                book.setPrice(rs.getString("book_price"));
                book.setIsborrow(rs.getString("book_isborrow"));
                book.setIsappointment(rs.getString("book_isappointment"));
                book.setBrief(rs.getString("book_brief"));
                book.setBarcode(rs.getString("barcode"));
                return book;
            }
        });
        for (Book book : bookList) {
            book.toString();
        }

        return bookList;
    }

    @RequestMapping("/borrowrecord")
    public List borrowrecord(String id) {
        ModelMap map = new ModelMap();
        String sql = " ";
        sql = "select * from borrow natural join book where borrowerid=" + "'" + id + "' and return_time is not null";
        List<Book> bookList = jdbcTemplate.query(sql, new RowMapper<Book>() {
            Book book = null;
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                book = new Book();
                book.setName(rs.getString("book_name"));
                book.setAuthor(rs.getString("book_author"));
                book.setPublisher(rs.getString("book_publisher"));
                book.setAuthor(rs.getString("book_author"));
                book.setDate(rs.getString("book_date"));
                book.setBookid(rs.getString("book_id"));
                book.setCopyid(rs.getInt("copy_id"));
                book.setType(rs.getString("book_type"));
                book.setAddress(rs.getString("book_address"));
                book.setPrice(rs.getString("book_price"));
                book.setIsborrow(rs.getString("book_isborrow"));
                book.setIsappointment(rs.getString("book_isappointment"));
                book.setBrief(rs.getString("book_brief"));
                book.setBarcode(rs.getString("barcode"));
                book.setBorrowerid(rs.getString("borrowerid"));
                book.setBorrowtime(rs.getString("borrow_time"));
                book.setReturntime(rs.getString("return_time"));
                return book;
            }
        });
        for (Book book : bookList) {
            System.out.println(book.getName());
        }

        return bookList;
    }


    public int  notreturn(int copy_idd) {
        int signal=0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://114.55.250.159:3306/library";
            Connection conn = DriverManager.getConnection(jdbc, "root", "123");
            Statement state = conn.createStatement();
            ResultSet rset = state.executeQuery("select * from borrow");
            while (rset.next()) {
                if (rset.getInt("copy_id") == copy_idd) {
                    String returntime = rset.getString("return_time");
                    String year1 = returntime.substring(0, 4);
                    String month1 = returntime.substring(5, 7);
                    String day1 = returntime.substring(8);
                    int year = Integer.valueOf(year1);
                    int month = Integer.valueOf(month1);
                    int day = Integer.valueOf(day1);
                    Calendar cal = Calendar.getInstance();
                    int y = cal.get(Calendar.YEAR);
                    int m = cal.get(Calendar.MONTH);
                    int d = cal.get(Calendar.DATE);//当前的时间比Returntime小就说明没有�?

                    if (year >= y) {
                        if (month >= m) {
                            if (day > d) {
                                signal=1;//此时是时间没超过了归还时间。按理说应该没还
                            }
                        }
                    }
                    else {
                        signal=0;
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

return signal;

    }

    @RequestMapping("/Readerinformation")
    public void Readerinformation(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://114.55.250.159:3306/library";
            Connection conn = DriverManager.getConnection(jdbc, "root", "123");
            Statement state = conn.createStatement();
            Statement state1 = conn.createStatement();
            ResultSet rset = state.executeQuery("select * from borrow");
            ResultSet rset1 = state1.executeQuery("select * from book ");//从book表中找出book_name打印出来
            System.out.println("User's id is " + id);//打印出读者的id
            int borrownum = 0;
            String sumname=null;
            while (rset.next()) {
                String name = rset.getString("borrowerid");
                if (name.equals(id)) {
                    borrownum += 1;
                    int bookindex = rset.getInt("copy_id");//获取书的indexid
                    if (notreturn(bookindex)==1) {//没还这本�?

                        while(rset1.next())
                        {
                               if(rset1.getInt("copy_id")==bookindex)
                               {
                                   String borrowbookname = rset1.getString("book_name");
                                   System.out.println("This book is to be returned:" + borrowbookname);
                               }

                        }

                    }

                }
            }
            System.out.println("You had borrowed totally " + borrownum + " books");


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean isappointment(String copy_id) {
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://114.55.250.159:3306/library";
            Connection conn = DriverManager.getConnection(jdbc, "root", "123");
            String sql="select book_isappointment from book where copy_id="+copy_id;
            Statement state = conn.createStatement();
            ResultSet rset = state.executeQuery(sql);
            String book_isappointment=null;
            if (rset.next()) 
                book_isappointment = rset.getString("book_isappointment");
            if(book_isappointment.equals("no"))
            	return false;
    	}
    	catch (Exception e) {
            e.printStackTrace();
        }
    	return true;
    }
    
    public static boolean isborrow(String copy_id)
    {
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://114.55.250.159:3306/library";
            Connection conn = DriverManager.getConnection(jdbc, "root", "123");
            String sql="select book_isborrow from book where copy_id="+copy_id;
            Statement state = conn.createStatement();
            ResultSet rset = state.executeQuery(sql);
            String book_isborrow=null;
            if (rset.next()) 
                book_isborrow = rset.getString("book_isborrow");
            if(book_isborrow.equals("no"))
            	return false;
    	}
    	catch (Exception e) {
            e.printStackTrace();
        }
    	return true;
    }
    
    public static boolean setappointment(String book_isappointment,String copy_id)
    {
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://114.55.250.159:3306/library";
            Connection conn = DriverManager.getConnection(jdbc, "root", "123");
            String sql="update book set book_isappointment=? where copy_id="+copy_id;
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setObject(1, book_isappointment);
            ps.executeUpdate();
            conn.close();
            return true;
    	}
    	catch (Exception e) {
            e.printStackTrace();
        }
    	return false;
    }
}
