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
import io.junq.examples.boot.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;


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

    @RequestMapping(value = "/edit_book")
    @ResponseBody
    public String edit_book(String book_name, String book_author, String book_publisher, String book_date, String book_id, String book_type, String book_address, String book_price, String book_brief) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://114.55.250.159:3306/library", "root", "123");

            String sql = "update book set book_name = ?, book_author = ?, book_publisher = ?, book_date = ?, book_type = ?, book_address = ?, book_price = ?, book_brief = ? where book_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, book_name);
            ps.setString(2, book_author);
            ps.setString(3, book_publisher);
            ps.setString(4, book_date);
            ps.setString(5, book_type);
            ps.setString(6, book_address);
            ps.setString(7, book_price);
            ps.setString(8, book_brief);
            ps.setString(9, book_id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
        return "Success";
   }

    @RequestMapping("/get_edit_book")
    public List get_edit_book(String book_name) throws SQLException {
        String sql="select * from book where book_name = \"" + book_name + "\" group by book_id" ; // limit 1" ;
        System.out.println(sql);
        List<Book> books = jdbcTemplate.query(sql, new RowMapper<Book>() {
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
                book.setBrief(rs.getString("book_brief"));
                return book;
            }});
        return books;
    }

    @RequestMapping(value = "/librarian_borrow_book")
    public String librarian_borrow_book(String borrowerid, String barcode) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc="jdbc:mysql://114.55.250.159:3306/library";
            Connection conn=DriverManager.getConnection(jdbc, "root", "123");
            Statement state=conn.createStatement();

            int count = 0;
            String sql1 = "select * from borrow where borrowerid = ?";
            PreparedStatement ps1=conn.prepareStatement(sql1);
            ps1.setString(1, borrowerid);
            ResultSet rs = ps1.executeQuery();

            while(rs.next()){
                count = count + 1;
            }
            if (count >= 3) {
                return "OVERFLOW";
            }
            else {
                String sql2="insert into borrow(borrowerid, barcode, borrow_time) value(?, ?, ?)";
                PreparedStatement ps2=conn.prepareStatement(sql2);
                ps2.setString(1, borrowerid);
                ps2.setString(2, barcode);
                ps2.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                System.out.println(ps2);
                ps2.executeUpdate();
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
        return "Success";
    }

    @RequestMapping(value = "/librarian_return_book")
    public float librarian_return_book(String borrowerid, String barcode) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://114.55.250.159:3306/library", "root", "123");
            float fine = 0;
            String sql1 = "update borrow set return_time = ? where borrowerid = ? and barcode = ?";
            PreparedStatement ps1 = con.prepareStatement(sql1);

            ps1.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps1.setString(2, borrowerid);
            ps1.setString(3, barcode);

            System.out.println(ps1);

            if(ps1.executeUpdate() == 0){
                return -1;
            }

            String sql2 = "select fine from borrow where borrowerid = ? and barcode = ?";
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setString(1, borrowerid);
            ps2.setString(2, barcode);

            ResultSet rs = ps2.executeQuery();
            while(rs.next()) {
                fine = rs.getFloat("fine");
            }
            return fine;

        } catch (Exception e) {
            e.printStackTrace();
            return -2;
        }
    }

    @RequestMapping(value = "/return_ispay")
    public String return_ispay(String barcode, int ispay) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://114.55.250.159:3306/library", "root", "123");

            String sql = "update borrow set ispay = ? where barcode = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, ispay);
            ps.setString(2, barcode);
            System.out.println(ps);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
        return "Success";
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
        if(sign==0) {
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
