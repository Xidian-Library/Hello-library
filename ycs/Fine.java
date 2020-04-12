package io.junq.examples.boot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
@RestController
@CrossOrigin(origins = "*")
public class Fine {
	static int fine=1;
	static int delay_day=30;
	@RequestMapping("/api/setfine")
	static public int setfine(int f) {
		fine=f;
		return 0;
	}
	@RequestMapping("/api/setdelay")
	static public int setdelay(int d) {
		delay_day=d;
		return 0;
	}
}
