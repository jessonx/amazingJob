package com.xcm.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ConfigManager {
	private static Properties properties = new Properties();
	static {
		try {
			properties.load(ConfigManager.class.getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取config.properties配置文件的配置
	 * 
	 * @param key
	 *            配置key
	 * @return 配置value，key不存在时返回null
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key, null);
	}

	/**
	 * 获取config.properties配置文件的配置
	 * 
	 * @param key
	 *            配置key
	 * @param defaultValue
	 *            key不存在返回的默认值
	 * @return 配置value，key不存在时返回defaultValue
	 */
	public static String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

}
