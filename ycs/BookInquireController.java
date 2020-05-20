package io.junq.examples.boot;
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


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BookInquireController {
    @Autowired
    private HelloService service;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/bookinquire")
    @ResponseBody
    public List bookinquire(String keytype, String key) {
        ModelMap map = new ModelMap();
        String sql = " ";
        if (keytype.equals("book_name")) {
            sql = "select * from book where book_name like " + "'%" + key + "%'";
        } else if (keytype.equals("book_author")) {
            sql = "select * from book where book_author like" + "'%" + key + "%'";
        } else if (keytype.equals("book_publisher")) {
            sql = "select * from book where book_publisher like " + "'%" + key + "%'";
        } else if (keytype.equals("book_id")) {
            sql = "select * from book where book_id=" + "'" + key + "'";
        } else if (keytype.equals("copy_id")) {
            sql = "select * from book where copy_id=" + "'" + key + "'";
        } else if (keytype.equals("barcode")){
            sql = "select * from book where barcode=" + "'" + key + "'";
        }
        List<Book> bookList = jdbcTemplate.query(sql, new RowMapper<Book>() {
            Book book = null;

            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                book = new Book();
                book.setName(rs.getString("book_name"));
                book.setAuthor(rs.getString("book_author"));
                book.setPublisher(rs.getString("book_publisher"));
                book.setDate(rs.getString("book_date"));
                book.setBookid(rs.getString("book_id"));
                book.setCopyid(rs.getString("copy_id"));
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

        return bookList;
    }

    @RequestMapping("/borrowrecord")
    @ResponseBody
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
                book.setDate(rs.getString("book_date"));
                book.setBookid(rs.getString("book_id"));
                book.setCopyid(rs.getString("copy_id"));
                book.setType(rs.getString("book_type"));
                book.setAddress(rs.getString("book_address"));
                book.setPrice(rs.getString("book_price"));
                book.setIsborrow(rs.getString("book_isborrow"));
                book.setIsappointment(rs.getString("book_isappointment"));
                book.setBrief(rs.getString("book_brief"));
                book.setBarcode(rs.getString("barcode"));
                book.setBorrowerid(rs.getString("borrowerid"));
                book.setBorrowtime(rs.getDate("borrow_time"));
                book.setReturntime(rs.getDate("return_time"));
                book.setFine(rs.getFloat("fine"));
                book.setDelaytime(rs.getInt("delay_time"));
                book.setIspay(rs.getBoolean("ispay"));
                return book;
            }
        });

        return bookList;
    }
    

    @RequestMapping("/borrowbook")
    @ResponseBody
    public List borrowbook(String id)
    {
        ModelMap map = new ModelMap();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String sql="select book.book_name,borrow.borrow_time from borrow natural join book where borrowerid=" + "'" + id + "' and return_time is null";
        List<Borbook> bookList = jdbcTemplate.query(sql, new RowMapper<Borbook>() {
            Borbook book = null;
            @Override
            public Borbook mapRow(ResultSet rs, int rowNum) throws SQLException {
                book = new Borbook();
                book.setTitle(rs.getString("book_name"));

                calendar.setTime(rs.getDate("borrow_time"));
                calendar.add(Calendar.DATE, Fine.delay_day);

                java.util.Date d1=calendar.getTime();
                final String returntime=df.format(d1.getTime());

                System.out.println(returntime);
                book.setDate(returntime);
                return book;
            }
        });

        return bookList;
    }
    @RequestMapping("/notReturnbook")
    @ResponseBody
    public List notReturnbook(String id) {
        ModelMap map = new ModelMap();
        String sql = " ";
        sql = "select * from borrow natural join book where borrowerid=" + "'" + id + "' and return_time is null";
        List<Book> bookList = jdbcTemplate.query(sql, new RowMapper<Book>() {
            Book book = null;
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                book = new Book();
                book.setName(rs.getString("book_name"));
                book.setAuthor(rs.getString("book_author"));
                book.setPublisher(rs.getString("book_publisher"));
                book.setDate(rs.getString("book_date"));
                book.setBookid(rs.getString("book_id"));
                book.setCopyid(rs.getString("copy_id"));
                book.setType(rs.getString("book_type"));
                book.setAddress(rs.getString("book_address"));
                book.setPrice(rs.getString("book_price"));
                book.setIsborrow(rs.getString("book_isborrow"));
                book.setIsappointment(rs.getString("book_isappointment"));
                book.setBrief(rs.getString("book_brief"));
                book.setBarcode(rs.getString("barcode"));
                book.setBorrowerid(rs.getString("borrowerid"));
                book.setBorrowtime(rs.getDate("borrow_time"));
                book.setReturntime(rs.getDate("return_time"));
                book.setFine(rs.getFloat("fine"));
                book.setDelaytime(rs.getInt("delay_time"));
                book.setIspay(rs.getBoolean("ispay"));
                return book;
            }
        });

        return bookList;
    }

   @RequestMapping("/librarianinformation")
   public List readerinformation(String id) throws SQLException {
       ModelMap map = new ModelMap();
        String sql="select * from librarian where id="+"'"+id+"'";
       System.out.println(sql);
       List<Librarian> information = jdbcTemplate.query(sql, new RowMapper<Librarian>() {
           Librarian librarian= null;
           @Override
           public Librarian mapRow(ResultSet rs, int rowNum) throws SQLException {
               librarian = new Librarian();
               librarian.setID(rs.getString("id"));
               librarian.setPassword(rs.getString("password"));
               librarian.setName(rs.getString("name"));
               return librarian;
           }});
       return information;
   }

    public static boolean isappointment(String barcode) {
    	try {
    	Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://114.55.250.159:3306/library";
            Connection conn = DriverManager.getConnection(jdbc, "root", "123");
            String sql="select book_isappointment from book where barcode=";
            sql=sql+"\""+barcode+"\"";
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
    
   public static boolean isborrow(String barcode)
    {
    	try {
    	Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://114.55.250.159:3306/library";
            Connection conn = DriverManager.getConnection(jdbc, "root", "123");
            String sql="select book_isborrow from book where barcode=";
            sql=sql+"\""+barcode+"\"";
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
    
    public static boolean setappointment(String book_isappointment,String barcode)
    {
    	try {
    Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://114.55.250.159:3306/library";
            Connection conn = DriverManager.getConnection(jdbc, "root", "123");
            String sql="update book set book_isappointment=? where barcode=";
            sql=sql+"\""+barcode+"\"";
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
