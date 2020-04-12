package io.junq.examples.boot;

import javax.annotation.Resource;
import java.util.*;
import org.*;
import io.*;
import sun.misc.Request;
import javax.imageio.ImageIO;
import java.awt.*;
import java.sql.*;
import javax.annotation.Resource;
import java.util.*;
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
        return "Hello world,hello ^-^   ljx Spring Boot rocks.";
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

    private static final int BARCODE_HEIGHT = 65;
    //设置条形码默认分辨率
    private static final int BARCODE_DPI = 203;
    //设置条形码字体样式
    private static final String FONT_FAMILY = "console";
    //设置条形码字体大小
    private static final int FONT_SIZE = 13;
    //设置条形码文本
    public static String TEXT = "";
    //创建jbarcode
    private static JBarcode jbc = null;

    @RequestMapping(value = "/bookBarcode")
    @ResponseBody
    public  JBarcode getJBarcode() throws InvalidAtributeException {
        /**
         * 参考设置样式：
         *barcode.setEncoder(Code128Encoder.getInstance()); //设置编码
         *barcode.setPainter(WidthCodedPainter.getInstance());// 设置Painter
         *barcode.setTextPainter(BaseLineTextPainter.getInstance()); //设置TextPainter
         *barcode.setBarHeight(17); //设置高度
         *barcode.setWideRatio(Double.valueOf(30).doubleValue());// 设置宽度比率
         *barcode.setXDimension(Double.valueOf(2).doubleValue()); // 设置尺寸，大小 密集程度
         *barcode.setShowText(true); //是否显示文本
         *barcode.setCheckDigit(true); //是否检查数字
         *barcode.setShowCheckDigit(false); //是否显示检查数字
         */
        try {
            JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(),
                    WidthCodedPainter.getInstance(),
                    EAN13TextPainter.getInstance());

            String str = "788515004012";
            BufferedImage localBufferedImage = localJBarcode.createBarcode(str);
            saveToGIF(localBufferedImage, "EAN13.gif");
            localJBarcode.setEncoder(Code39Encoder.getInstance());
            localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
            localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
            localJBarcode.setShowCheckDigit(false);

            str = "JBARCODE-39";
            localBufferedImage = localJBarcode.createBarcode(str);
         //   saveToPNG(localBufferedImage, "Code39.png");
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return localJBarcode;
    }

    @RequestMapping(value = "/add_new_book")
    @ResponseBody
    public String add_newbook(String book_name,String book_author,String book_publisher,String book_date,String book_id,String book_type,String book_address,String book_price,String book_brief) {
        String copyid= getCopyId(book_id);
        String barcode = book_id+getCopyId(book_id);
        String sql="insert into  book values ('"+book_name +"','"+book_author+"','"+ book_publisher+"','"+
               book_date+"','"+ book_id+"','"+copyid+"','"+ book_type+"','" + book_address+"','"+book_price+"','no','no','"+book_brief+"','"+barcode+"');";
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
