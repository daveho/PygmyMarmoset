// Pygmy Marmoset - an assignment submission webapp for CS courses
// Copyright (c) 2017, David H. Hovemeyer <david.hovemeyer@gmail.com>
//
// This is free software distributed under the terms of the
// GNU Affero Public License v3 or later.  See LICENSE.txt for details.

package edu.ycp.cs.pygmymarmoset.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PMConfig {
	// Keys
	public static final String DB_HOST = "pm.db.host";
	public static final String DB_USER = "pm.db.user";
	public static final String DB_PASSWD = "pm.db.passwd";
	public static final String DB_NAME = "pm.db.name";
	
	private static final Logger logger = LoggerFactory.getLogger(PMConfig.class);
	
	private static final PMConfig instance = new PMConfig();
	static {
		InputStream in =
				PMConfig.class.getClassLoader().getResourceAsStream("pygmymarmoset.properties");
		if (in == null) {
			logger.error("pygmymarmoset.properties doesn't exist");
		} else {
			InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
			try {
				instance.load(reader);
			} catch (IOException e) {
				logger.error("Error reading pygmymarmoset.properties", e);
			}
		}
	}
	
	/**
	 * Get the singleton instance of {@link PMConfig}.
	 * 
	 * @return the singleton instance
	 */
	public static PMConfig getInstance() {
		return instance;
	}
	
	private Properties properties;
	
	private PMConfig() {
		properties = new Properties();
	}
	
	public void load(Reader reader) throws IOException {
		properties.load(reader);
	}
	
	public String getProperty(String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			throw new IllegalArgumentException("No such configuration key: " + key);
		}
		return value;
	}
}
