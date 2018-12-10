package com.xcm.util;

import org.slf4j.Logger;

public class LoggerManage {
	static Logger logger = null;

	public static Logger getLogger() {
		if (logger == null) {
			logger = new JobOfferLogger();
		}
		return logger;
	}

}
