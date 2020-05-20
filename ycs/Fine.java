package io.junq.examples.boot;

import java.util.Map;

public class Fine {

	static int fine = 0;
	static int delay_day = 0;
	static int security_depoisit = 0;

	static void setter(Map<String, Integer> settings) {
		Fine.fine = settings.get("book_fine");
		Fine.delay_day = settings.get("return_period");
		Fine.security_depoisit = settings.get("security_depoisit");
	}

}
