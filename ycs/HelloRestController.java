package io.junq.examples.boot;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import io.junq.examples.boot.Librarian;
import io.junq.examples.boot.Book;
import io.junq.examples.boot.Readers;
import io.junq.examples.boot.Deleted_book;
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
import java.io.File;
import java.io.IOException;


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
    public int login(String id, String password, String type){
        if(type.equals("Librarian")){
            System.out.println("Librarian");
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

        if(type.equals("Reader")){
            System.out.println("Reader");
            ModelMap map = new ModelMap();
            String sql = "SELECT * FROM readers";
            List<Readers> readerList = jdbcTemplate.query(sql, new RowMapper<Readers>() {
                Readers reader = null;
                @Override
                public Readers mapRow(ResultSet rs, int rowNum) throws SQLException {
                    reader = new Readers();
                    reader.setID(rs.getString("id"));
                    reader.setPassword(rs.getString("password"));
                    return reader;
                }});
            for(Readers reader:readerList){

                if(reader.getID().equals(id)){
                    if(reader.getPassword().equals(password)) {
                        System.out.println("YES");
                        return 1;
                    }
                    else{
                        return -1;
                    }
                }
                System.out.println(reader.getID());
                System.out.println(reader.getPassword());
            }
            map.addAttribute("id", readerList);

            return 0;
        }
        return -3;
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

            //可借阅天数
            int delay_day = 30;
            Timestamp timestamp_now  = new Timestamp(System.currentTimeMillis());
            long time = timestamp_now.getTime()+(long)1000*3600*24*delay_day;

            int count = 0;

            //读者id是否存在
            String sql3 = "select * from readers where id = ?";
            PreparedStatement ps3=conn.prepareStatement(sql3);

            ps3.setString(1, borrowerid);
            System.out.println(sql3);
            ResultSet rs3 = ps3.executeQuery();
            while(rs3.next()){
                count = count + 1;
            }
            if (count == 0) {
                return "IDNotMatch";
            }
            count = 0;

            //图书Barcode是否存在
            String sql4 = "select * from book where barcode = ?";

            PreparedStatement ps4 = conn.prepareStatement(sql4);
            ps4.setString(1, barcode);
            System.out.println(sql4);
            ResultSet rs4 = ps4.executeQuery();
            while(rs4.next()){
                count = count + 1;
            }
            if (count == 0) {
                return "BarcodeNotMatch";
            }
            count = 0;

            //判断是否已经被借阅
            String sql5 = "select * from borrow where barcode = ?";

            PreparedStatement ps5 = conn.prepareStatement(sql5);
            ps5.setString(1, barcode);
            System.out.println(ps5);
            ResultSet rs5 = ps5.executeQuery();
            while(rs5.next()){
                count = count + 1;
                //如果发现有未归还借阅记录，跳出循环
                if(rs5.getInt("ispay") == 0) {
                    return "Borrowed";
                }
            }
//            if (count == 0) {
//                return "Borrowed";
//            }
            count = 0;

            //判断是否借阅超过3本
            String sql1 = "select * from borrow where borrowerid = ?";
            PreparedStatement ps1=conn.prepareStatement(sql1);

            ps1.setString(1, borrowerid);
            System.out.println(sql1);
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
                //ps2.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                ps2.setTimestamp(3, timestamp_now);
                System.out.println(sql2);
                //ps2.setTimestamp(3, new Timestamp(time));
                Timestamp timestamp_delay = new Timestamp(time);


                System.out.println(ps2);
                System.out.println(timestamp_delay);

                ps2.executeUpdate();

                String[] ss = new String[2];
                ss = timestamp_delay.toString().split("\\.");
                return ss[0];
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }

    }

    @RequestMapping(value = "/librarian_return_book")
    public float librarian_return_book(String borrowerid, String barcode) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://114.55.250.159:3306/library", "root", "123");
            float fine = 0;

            // 检查是否已经还过书
            String sql3 = "select * from borrow where borrowerid = ? and barcode = ?";
            PreparedStatement ps3 = con.prepareStatement(sql3);
            ps3.setString(1, borrowerid);
            ps3.setString(2, barcode);
            ResultSet rs3 = ps3.executeQuery();
            while(rs3.next()){
                if(rs3.getString("return_time") == null){
                    break;
                }
                if(rs3.getString("return_time") != null && rs3.isLast())
                    return -3;
            }

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

    @RequestMapping(value = "/add_post", method = RequestMethod.POST)
    public String add_post(String title, MultipartFile file, String content) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://114.55.250.159:3306/library";
            Connection conn = DriverManager.getConnection(jdbc, "root", "123");
            String sql1 = "select * from post where title = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, title);
            ResultSet rs1 = ps1.executeQuery();
            int count = 0;
            while (rs1.next()) {
                count += 1;
            }
            if (count > 0) {
                return "TitleExist";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "Fail";
        }
        String realPath = "/home/library/src/main/resources/static/postpic/" + file.getOriginalFilename();
        System.out.println(title);
        System.out.println(file.getOriginalFilename());
        File dest = new File(realPath);

        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);

        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "SavePhotoError";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "SavePhotoError";
        }

        try{
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc = "jdbc:mysql://114.55.250.159:3306/library";
            Connection conn = DriverManager.getConnection(jdbc, "root", "123");
            String sql="insert into post(title, photo, content) value(?, ?, ?)";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1,title);
            ps.setString(2,"http://114.55.250.159:8080/postpic/" + file.getOriginalFilename());
            ps.setString(3,content);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return "AddDataError";
        }
        return "Success";
    }

    @RequestMapping(value = "/get_post_by_title", method = RequestMethod.POST)
    public List get_post_by_title(String title) {
        String sql="select * from post where title = \"" + title + "\" and isdelete = 0";
        System.out.println(sql);
        List<Post> posts = jdbcTemplate.query(sql, new RowMapper<Post>() {
            Post post = null;
            @Override
            public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
                post = new Post();
                post.setTitle(rs.getString("title"));
                post.setPhoto(rs.getString("photo"));
                post.setContent(rs.getString("content"));
                return post;
            }});
        return posts;
    }

    @RequestMapping(value = "/edit_post", method = RequestMethod.POST)
    public String edit_post(String title_original, String title_new, MultipartFile file, String content) {
        String realPath = "/home/library/src/main/resources/static/postpic/" + file.getOriginalFilename();
        System.out.println(title_original);
        System.out.println(title_new);
        System.out.println(file.getOriginalFilename());
        System.out.println(content);
        File dest = new File(realPath);

        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);

        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "SavePhotoError";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "SavePhotoError";
        }

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://114.55.250.159:3306/library", "root", "123");

            String sql = "update post set title = ? , photo = ?, content = ? where title = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, title_new);
            ps.setString(2, "http://114.55.250.159:8080/postpic/" + file.getOriginalFilename());
            ps.setString(3, content);
            ps.setString(4, title_original);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
        return "Success";
    }

    @RequestMapping(value = "/delete_post", method = RequestMethod.POST)
    public String delete_post(String title) {

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://114.55.250.159:3306/library", "root", "123");

            String sql = "update post set isdelete = ?  where title = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, 1);
            ps.setString(2, title);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
        return "Success";
    }

    @RequestMapping(value = "/get_all_post", method = RequestMethod.POST)
    public List get_post_by_title() {
        String sql="select * from post where isdelete = 0";
        System.out.println(sql);
        List<Post> posts = jdbcTemplate.query(sql, new RowMapper<Post>() {
            Post post = null;
            @Override
            public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
                post = new Post();
                post.setTitle(rs.getString("title"));
                post.setPhoto(rs.getString("photo"));
                post.setContent(rs.getString("content"));
                return post;
            }});
        return posts;
    }

    @RequestMapping("/get_deleted_book")
    public List get_deleted_book() throws SQLException {
        String sql="select * from deleted_book";
        System.out.println(sql);
        List<Deleted_book> deleted_books = jdbcTemplate.query(sql, new RowMapper<Deleted_book>() {
            Deleted_book deleted_book = null;
            @Override
            public Deleted_book mapRow(ResultSet rs, int rowNum) throws SQLException {
                deleted_book = new Deleted_book();
                deleted_book.setAdmin_id(rs.getString("admin_id"));
                deleted_book.setBook_id(rs.getString("book_id"));
                deleted_book.setBook_barcode(rs.getString("book_barcode"));
                deleted_book.setDate(rs.getString("date"));
                return deleted_book;
            }});
        return deleted_books;
    }

    @RequestMapping(value = "/check_fine")
    public Float return_ispay(String borrowerid) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://114.55.250.159:3306/library", "root", "123");

            String sql1 = "select fine from borrow where borrowerid = ? and ispay = -1";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.setString(1, borrowerid);
            System.out.println(sql1);
            float fine = 0;

            ResultSet rs = ps1.executeQuery();
            while(rs.next()) {
                fine = fine + rs.getFloat("fine");
            }
            return fine;


        } catch (Exception e) {
            e.printStackTrace();
            return (float)-1;
        }
    }

    @RequestMapping(value = "/all_ispay")
    public String all_ispay(String borrowerid) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://114.55.250.159:3306/library", "root", "123");

            String sql = "update borrow set ispay = 1 where borrowerid = ? and ispay = -1";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, borrowerid);
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
        int sign=1;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String jdbc="jdbc:mysql://114.55.250.159:3306/library";
            Connection conn=DriverManager.getConnection(jdbc, "root", "123");
            Statement state=conn.createStatement();
            ResultSet rset = state.executeQuery("select * from readers");

        while(rset.next()) {
            String name=rset.getString("id");
            if(name.equals(id)){
            System.out.println("This account has existed!please try another one.");
            sign=0;
            return sign;
            }
        }
        if(sign==1) {
            String sql="insert into readers(id,password) value(?,'123456')";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1,id);
            ps.executeUpdate();

        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }


}
