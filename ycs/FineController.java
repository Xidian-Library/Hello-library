package io.junq.examples.boot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @description: TODO
 * @author: xiean99
 * @date: 2020年4月20日 下午9:02:37
 */

@RestController
@CrossOrigin(origins = "*")
public class FineController {

	@Resource
	private JdbcTemplate jdbcTemplate;

	/*
	  * 构造函数， 根据从数据库获取的值初始化Fine类
	  * 由于@Resource的注入顺序晚于@RestController实例化，于是用线程异步初始化Fine类
	 */
	public FineController() {
		Thread fineSetter = new Thread(new Runnable() {
			@Override
			public void run() {
				while (jdbcTemplate == null);
				Map<String, Integer> settings = new HashMap<>();
				settingGetter().stream()
						.forEach(item -> settings.put((String) item.get("name"), (Integer) item.get("value")));
				Fine.setter(settings);
			}
		});
		fineSetter.start();
	}

	/*
	  *  设置书籍逾期罚金接口
	 * @param fine 罚金
	 * @return 成功时返回0
	 * 
	 */
	@RequestMapping("/api/setfine")
	public int setFine(@RequestParam(value = "f") int fine) {
		String sql = "update settings set value= ? where name='book_fine'";
		Object[] ob = { fine };
		jdbcTemplate.update(sql, ob);
		Fine.fine = fine;
		return 0;
	}
	
	/*
	  *  设置书籍归还期限接口
	 * @param delay 归还期限
	 * @return 成功时返回0
	 * 
	 */
	@RequestMapping("/api/setdelay")
	public int setDelay(@RequestParam(value = "d") int delay) {
		String sql = "update settings set value = ? where name='return_period'";
		Object[] ob = { delay };
		jdbcTemplate.update(sql, ob);
		Fine.delay_day = delay;
		return 0;
	}

	/*
	  *  设置读者创建账户时缴纳保证金接口
	 * @param security 保证金
	 * @return 成功时返回0
	 * 
	 */
	@RequestMapping("/api/setSecurity")
	public int setSecurity(@RequestParam(value = "s") int security) {
		String sql = "update settings set value = ? where name='security_depoisit'";
		Object[] ob = { security };
		jdbcTemplate.update(sql, ob);
		Fine.security_depoisit = security;
		return 0;
	}

	/*
	  * 获取setting表(包含罚金，保证金和期限的值)接口
	 * @return 由setting表所有行组成的键值对列表
	 * 
	 */
	@RequestMapping("/api/query/settings")
	public List<Map<String, Object>> settingGetter() {
		List<Map<String, Object>> settings = jdbcTemplate.queryForList("select * from settings");
		return settings;
	}

}
