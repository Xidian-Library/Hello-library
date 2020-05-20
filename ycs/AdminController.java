package io.junq.examples.boot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//
//
//
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AdminController {
	@Resource
	private JdbcTemplate jdbcTemplate;

	@RequestMapping("/AdminLogin")
	public int Adminlogin(int id, String password) {
		int flag = 0;
		String sql = "select * from admin where id=? and password=?";

		Admin adm = new Admin(id, password);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://114.55.250.159:3306/library", "root", "123");

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(adm.getId()));
			pstmt.setString(2, adm.getPassword());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				flag = 1;
				System.out.println("Welcome back , admin " + adm.getName() + " !");
			} else {
				System.out.println("Try again!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@RequestMapping("/ChangeAdminPassword")
	public int ChangeAdminPassword(int id, String oldpassword, String newpassword) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String jdbc = "jdbc:mysql://114.55.250.159:3306/library";
			Connection conn = DriverManager.getConnection(jdbc, "root", "123");

			String sql = "select * from admin where id=? and password=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(id));
			pstmt.setString(2, oldpassword);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String sql1 = "update admin set password=? where id=?";
				Object[] ob = { newpassword, id };
				jdbcTemplate.update(sql1, ob);
			} else {
				System.out.println("account does not exist!");
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	@RequestMapping("/RegisterLibrarian")
	public int RegisterLibrarian(String id, String name) {
		if ("".equals(id)) {
			System.out.println("Invalid ID!");
			return 0;
		}
		if ("".equals(name)) {
			System.out.println("Invalid ID!");
			return 0;
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String jdbc = "jdbc:mysql://114.55.250.159:3306/library";
			Connection conn = DriverManager.getConnection(jdbc, "root", "123");
			Statement state = conn.createStatement();

			String sql = "insert into librarian(id,password,name) value(?,'00010001',?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	@RequestMapping("/DeleteLibrarian")
	public int DeleteLibrarian(String id) {
		try {
			String sql = "delete from librarian where id=?";
			Object[] ob = { id };
			jdbcTemplate.update(sql, ob);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("account does not exist!");
			return 0;
		}
		return 1;
	}

	@RequestMapping("/EditLibrarianName")
	public int EditLibrarianName(String id, String newname) {
		try {
			String sql = "update librarian set name=? where id=?";
			Object[] ob = { newname, id };
			jdbcTemplate.update(sql, ob);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("account does not exist!");
			return 0;
		}
		return 1;
	}

	@RequestMapping("/EditLibrarianPassword")
	public int EditLibrarianPassword(String id, String newpassword) {
		try {
			String sql = "update librarian set password=? where id=?";
			Object[] ob = { newpassword, id };
			jdbcTemplate.update(sql, ob);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("account does not exist!");
			return 0;
		}
		return 1;
	}

	@RequestMapping("/SearchLibrarian")
	public List SearchLibrarian(String id) throws SQLException {
		String sql = "select * from librarian where id=";
		sql = sql + "\"" + id + "\"";
		List<Librarian> librarians = jdbcTemplate.query(sql, new RowMapper<Librarian>() {
			Librarian lib = null;

			@Override
			public Librarian mapRow(ResultSet rs, int rowNum) throws SQLException {
				lib = new Librarian();
				lib.setID(rs.getString("id"));
				lib.setPassword(rs.getString("password"));
				lib.setName(rs.getString("name"));

				return lib;
			}
		});
		return librarians;
	}

	@RequestMapping("/AllLibrarians")
	public List AllLibrarians() throws SQLException {
		String sql = "select * from librarian ";

		List<Librarian> librarians = jdbcTemplate.query(sql, new RowMapper<Librarian>() {
			Librarian lib = null;

			@Override
			public Librarian mapRow(ResultSet rs, int rowNum) throws SQLException {
				lib = new Librarian();
				lib.setID(rs.getString("id"));
				lib.setPassword(rs.getString("password"));
				lib.setName(rs.getString("name"));
				return lib;
			}
		});
		return librarians;
	}

	/*
	 * 查询指定时间范围内的图书馆收入记录接口 
	 * 计算的收入包括：
	 * 1.保证金收入，即该段时间内注册读者所缴纳的保证金总和
	 * 2.罚金收入，即该段时间内所有已经处于逾期状态的书籍产生的罚金总和
	 * 
	 * @param startDate 起始时间 
	 * @param endDate 结束时间
	 * @return 包含键 ：'fine'(罚金)和'security'(保证金)的map对象
	 * 
	 */
	@RequestMapping("/getIncome")
	public Map<String, Object> getIncome(@RequestParam(value = "start") long startDate, 
									     @RequestParam(value = "end") long endDate) {
		if (startDate > endDate)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start = df.format(startDate);
		String end = df.format(endDate);

		/**
		 * 获取罚金收入 选择borrow表中逾期日期（借阅日期+归还期限）和归还日期组成的日期范围
		 * 与参数所指定的日期范围交集不为空的行，并以该交集作为计算罚金的范围
		 * 
		 */
		String sql = " select sum(TIMESTAMPDIFF(DAY,lower,upper)) * ? as fine from "
				+ "(select  greatest(?,DATE_ADD(borrow_time,INTERVAL ? DAY)) as lower ,"
				+ "if(return_time is null,?,least(return_time,?)) as upper "
				+ "from borrow where TIMESTAMPDIFF(DAY ,borrow_time,return_time)>? "
				+ "and TIMESTAMPDIFF(DAY,borrow_time,?)>? and ? <return_time ) as temp";
		Object[] ob = { Fine.fine, start, Fine.delay_day, end, end, Fine.delay_day, end, Fine.delay_day, start };
		Map<String, Object> income = jdbcTemplate.queryForMap(sql, ob);
		
		if(income.get("fine")==null) {
			income.put("fine",0);
		}
		
		// 获取保证金收入
		sql = "select count(*) * ? as security from readers where register_time between ? and ?  ";
		Object[] ob2 = { Fine.security_depoisit, start, end };
		income.putAll(jdbcTemplate.queryForMap(sql, ob2));
		return income;
	}
}