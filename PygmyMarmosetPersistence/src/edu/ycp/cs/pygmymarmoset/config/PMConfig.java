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

/**
 * Configuration properties object.
 */
public class PMConfig {
	// Keys
	public static final String DB_HOST = "pm.db.host";
	public static final String DB_USER = "pm.db.user";
	public static final String DB_PASSWD = "pm.db.passwd";
	public static final String DB_NAME = "pm.db.name";
	
	private static final Logger logger = LoggerFactory.getLogger(PMConfig.class);
	
	private static final PMConfig instance = new PMConfig();
	static {
		if (!loadPropertiesFromResource("pygmymarmoset.properties")) {
			// If we're running from the NestedJarClassLoader, e.g. when
			// running the "createdb" from the command line, then we need
			// to specify the location of the config properties file
			// within the uberjar.
			if (!loadPropertiesFromResource("war/WEB-INF/classes/pygmymarmoset.properties")) {
				logger.error("Could not find pygmymarmoset.properties");
			}
		}
	}
	
	private static boolean loadPropertiesFromResource(String resName) {
		InputStream in =
				PMConfig.class.getClassLoader().getResourceAsStream(resName);
		if (in == null) {
			logger.warn("Resource {} doesn't exist", resName);
			return false;
		} else {
			InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
			try {
				instance.load(reader);
				return true;
			} catch (IOException e) {
				logger.error("Error reading pygmymarmoset.properties", e);
				return false;
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
