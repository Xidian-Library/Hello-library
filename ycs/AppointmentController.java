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
    public int add_appointment(String rid,String barcode){
		if(BookInquireController.isappointment(barcode))
			return 1;
		if(BookInquireController.isborrow(barcode))
			return 2;
		String appointment_time=null;
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		appointment_time=df.format(day);
		String sql="insert into appointment (rid,barcode,"
				+ "appointment_time) values (?,?,?)";
		Object[] ob= {rid,barcode,appointment_time};
        jdbcTemplate.update(sql, ob);
        BookInquireController.setappointment("yes", barcode);
        return 0;
    }
	@RequestMapping("/delete_appointment")
    @ResponseBody
	public int delete_appointment(String rid,String barcode)
	{
		String sql="delete from appointment where rid=? and barcode=?";
		Object[] ob= {rid,barcode};
		jdbcTemplate.update(sql, ob);
		BookInquireController.setappointment("no", barcode);
		return 0;
	}
	@RequestMapping("/get_personal_appointment")
    @ResponseBody
    public List get_personal_appointment(String rid)
	{
		String sql="select * from appointment_information where rid=";
		sql=sql+"\""+rid+"\"";
		List<Appointment> appoint = jdbcTemplate.query(sql, new RowMapper<Appointment>() {
			Appointment ap = null;
            @Override
            public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            	ap = new Appointment();
            	ap.setrid(rs.getString("rid"));
            	ap.setbook_name(rs.getString("book_name"));
            	ap.setbarcode(rs.getString("barcode"));
            	ap.setappointment_time(rs.getString("appointment_time"));
                return ap;
            }});
		return appoint;
		
	}
	@RequestMapping("/get_all_appointment")
    @ResponseBody
    public List get_all_appointment()
	{
		String sql="select * from appointment_information";
		
		List<Appointment> appoint = jdbcTemplate.query(sql, new RowMapper<Appointment>() {
			Appointment ap = null;
            @Override
            public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            	ap = new Appointment();
            	ap.setrid(rs.getString("rid"));
            	ap.setbook_name(rs.getString("book_name"));
            	ap.setbarcode(rs.getString("barcode"));
            	ap.setappointment_time(rs.getString("appointment_time"));
                return ap;
            }});
		return appoint;
		
	}
}
