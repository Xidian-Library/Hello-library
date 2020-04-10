package io.junq.examples.boot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class Appointmenttimer {
	 @Resource
	 private JdbcTemplate jdbcTemplate;
	 @Scheduled(cron = "0 * * ? * *")
	 public int delete() {
		 String appointment_time=null;
		 Date day=new Date();    
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 Calendar   dar=Calendar.getInstance();
		 dar.setTime(day);
		 dar.add(java.util.Calendar.HOUR_OF_DAY, -2);
		 appointment_time=df.format(dar.getTime());
		 String sql="update book set book_isappointment=\"no\" where copy_id=(select call_number from appointment where appointment_time<?)";
		 Object[] ob= {appointment_time};
		 jdbcTemplate.update(sql, ob);
		 sql="delete from appointment where appointment_time<?";
		 jdbcTemplate.update(sql, ob);
		 return 0;
	 }
}
