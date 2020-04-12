package io.junq.examples.boot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@Component
@CrossOrigin(origins = "*")
public class Finetimer {
	@Resource
	 private JdbcTemplate jdbcTemplate;
	 @Scheduled(cron = "30 0 0 * * *")
	 public void fine() {
		 String borrow_time=null;
		 String now=null;
		 Date day=new Date();    
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 
		 Calendar   dar=Calendar.getInstance();
		 dar.setTime(day);
		 dar.add(java.util.Calendar.DAY_OF_MONTH , Fine.delay_day*-1);
		 borrow_time=df.format(dar.getTime());
		 
		String sql="update borrow set fine=(select (TIMESTAMPDIFF(DAY,borrow_time,?)*?)),delay_time=(select (TIMESTAMPDIFF(DAY,borrow_time,?))) where return_time is null and borrow_time<?";
		  Object[] ob= {borrow_time,Fine.fine,borrow_time,borrow_time};
		 jdbcTemplate.update(sql, ob);
	 }
	  @RequestMapping("/api/fine_refresh")
	 @ResponseBody
	 public int fine_refresh() {
		 String borrow_time=null;
		 String now=null;
		 Date day=new Date();    
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 
		 Calendar   dar=Calendar.getInstance();
		 dar.setTime(day);
		 dar.add(java.util.Calendar.DAY_OF_MONTH , Fine.delay_day*-1);
		 borrow_time=df.format(dar.getTime());
		 
		 String sql="update borrow set fine=(select (TIMESTAMPDIFF(DAY,borrow_time,?)*?)),delay_time=(select (TIMESTAMPDIFF(DAY,borrow_time,?))) where return_time is null and borrow_time<?";
		  Object[] ob= {borrow_time,Fine.fine,borrow_time,borrow_time};
		 jdbcTemplate.update(sql, ob);
		 return 0;
	 }
	 @RequestMapping("/api/delay_message")
	 @ResponseBody
	 public List delay_message(String borrowerid) {
		 String sql="SELECT book.book_name,book.book_author,borrow.barcode,borrow.fine,borrow.delay_time " + 
		 		"FROM borrow,book WHERE borrow.barcode = book.barcode and borrow.fine>0 and borrow.ispay=0 and borrow.borrowerid=";
		 sql=sql+"\""+borrowerid+"\"";
		 List<Delay_message> delay_mess = jdbcTemplate.query(sql, new RowMapper<Delay_message>() {
			 Delay_message dm = null;
	            @Override
	            public Delay_message mapRow(ResultSet rs, int rowNum) throws SQLException {
	            	dm = new Delay_message();
	            	dm.setbook_name(rs.getString("book_name"));
	            	dm.setbook_author(rs.getString("book_author"));
	            	dm.setbarcode(rs.getString("barcode"));
	            	dm.setfine(rs.getString("fine"));
	            	dm.setdelay_time(rs.getString("delay_time"));
	                return dm;
	            }});
		 return delay_mess;
	 }
}
