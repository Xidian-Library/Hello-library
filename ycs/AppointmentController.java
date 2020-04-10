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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import io.junq.examples.boot.Appointment;
import java.util.Date;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AppointmentController {
	@Resource
    private JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/add_appointment")
    @ResponseBody
    public int add_appointment(String id, String title,String call_num){
		if(BookInquireController.isappointment(call_num))
			return 1;
		if(BookInquireController.isborrow(call_num))
			return 2;
		String appointment_time=null;
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		appointment_time=df.format(day);
		String sql="insert into appointment (id,title,call_number,"
				+ "appointment_time,take) values (?,?,?,?,?)";
		Object[] ob= {id,title,call_num,appointment_time,0};
        jdbcTemplate.update(sql, ob);
        BookInquireController.setappointment("yes", call_num);
        return 0;
    }
	@RequestMapping("/delete_appointment")
    @ResponseBody
	public int delete_appointment(String id,String call_num)
	{
		String sql="delete from appointment where id=? and call_number=?";
		Object[] ob= {id,call_num};
		jdbcTemplate.update(sql, ob);
		BookInquireController.setappointment("no", call_num);
		return 0;
	}
	@RequestMapping("/get_personal_appointment")
    @ResponseBody
    public List get_personal_appointment(String id)
	{
		String sql="select * from appointment where id=";
		sql=sql+"\""+id+"\"";
		List<Appointment> appoint = jdbcTemplate.query(sql, new RowMapper<Appointment>() {
			Appointment ap = null;
            @Override
            public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            	ap = new Appointment();
            	ap.setid(rs.getString("id"));
            	ap.settitle(rs.getString("title"));
            	ap.setcall_number(rs.getString("call_number"));
            	ap.setcollection_location(rs.getString("collection_location"));
            	ap.setappointment_time(rs.getString("appointment_time"));
            	ap.settake(rs.getString("take"));
                return ap;
            }});
		return appoint;
		
	}
	@RequestMapping("/get_all_appointment")
    @ResponseBody
    public List get_all_appointment(String id)
	{
		String sql="select * from appointment";
		
		List<Appointment> appoint = jdbcTemplate.query(sql, new RowMapper<Appointment>() {
			Appointment ap = null;
            @Override
            public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            	ap = new Appointment();
            	ap.setid(rs.getString("id"));
            	ap.settitle(rs.getString("title"));
            	ap.setcall_number(rs.getString("call_number"));
            	ap.setcollection_location(rs.getString("collection_location"));
            	ap.setappointment_time(rs.getString("appointment_time"));
            	ap.settake(rs.getString("take"));
                return ap;
            }});
		System.out.print(appoint);
		return appoint;
		
	}
}
