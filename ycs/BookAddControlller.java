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
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Constructor;
import javax.imageio.stream.FileImageOutputStream;
import com.google.zxing.EncodeHintType;
import com.google.zxing.oned.Code128Writer;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public String hello()
    {
        return "Hello world,hello ^-^   ljx Spring Boot rocks.";
    }

    public static String getCard()
    {
        Random rand=new Random();//生成随机数
        String cardNnumer="";
        for(int a=0;a<6;a++){
            cardNnumer+=rand.nextInt(10);//生成6位数字
        }
        return cardNnumer;
    }

    public static String getNotRepeatId()
    {
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

    public String getCopyId(String book_id)
    {
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

    public void writeImageFile(String barcode,BufferedImage bi) throws IOException
    {
            String loadPath = "/home/library/src/main/resources/static/barcodepic/" + barcode + ".png";
            File outputfile = new File(loadPath);
            ImageIO.write(bi, "png", outputfile);
            System.out.println("Save BarcodeImage ...");
    }


    public static BufferedImage encode(String contents)
    {
        //配置条码参数
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //设置条码两边空白边距为0，默认为10，如果宽度不是条码自动生成宽度的倍数则MARGIN无效
        hints.put(EncodeHintType.MARGIN, 0);
        //为了无边距，需设置宽度为条码自动生成规则的宽度
        int width = new Code128Writer().encode(contents).length;
        //前端可控制高度，不影响识别
        int height = 70;
        //条码放大倍数
        int codeMultiples = 1;
        //获取条码内容的宽，不含两边距，当EncodeHintType.MARGIN为0时即为条码宽度
        int codeWidth = width * codeMultiples;
        try {

            // 图像数据转换，使用了矩阵转换 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.CODE_128, codeWidth, height, hints);
//            MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream("d:/code39.png"));
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/getBarCodeImage")
    public String saveBarCodeImage(String barcode)//including the function of generating the new book's 1D code and save it into our file.
    {

        try {
            BufferedImage buffImg = encode(barcode);
            writeImageFile(barcode,buffImg);
            return "Success!";
        } catch (IOException e) {
            e.printStackTrace();
            return "false here123!";
        }
    }
//write for test
//    @RequestMapping("/showBarCodeImage")
//    @ResponseBody
//    public BufferedImage getImage(String barcode) throws IOException {
//
//        try (InputStream is = new FileInputStream("/home/library/src/main/resources/static/test4.png")) {
//            return ImageIO.read(is);
//        }
//    }
    
    @RequestMapping(value = "/add_new_book")
    @ResponseBody
    public String add_newbook(String book_name,String book_author,String book_publisher,String book_date,String book_id,String book_type,String book_address,String book_price,String book_brief)
    {
        String copyid= getCopyId(book_id);
        String barcode = book_id+getCopyId(book_id);
        saveBarCodeImage(barcode);
        String sql="insert into  book values ('"+book_name +"','"+book_author+"','"+ book_publisher+"','"+
               book_date+"','"+ book_id+"','"+copyid+"','"+ book_type+"','" + book_address+"','"+book_price+"','no','no','"+book_brief+"','"+barcode+"');";
        DBHelper.BuildHelper(sql);
        try {
            DBHelper.pst.execute();
            DBHelper.close();
        } catch (SQLException err) {
            return "failure";
        }
        return barcode;//书籍添加成功返回barcode，已便后续跳转到barcode图片页
    }


    @RequestMapping(value = "/add_new_bookpic", method = RequestMethod.POST)
    public String add_bookpic(MultipartFile file,String book_id) {
        String realPath = "/home/library/src/main/resources/static/bookpic/bookpic" + book_id+".png";
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
        return "Success";
    }
}
