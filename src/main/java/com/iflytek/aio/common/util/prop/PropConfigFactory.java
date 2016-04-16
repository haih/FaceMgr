/**	
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 */
package com.iflytek.aio.common.util.prop;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * @desc: 读取系统中不同的配置文件
 * @author: cheney
 * @createTime: 2014-12-9
 * @version: 2.1
 */
public class PropConfigFactory {

	/**
	 * 设置默认分隔符
	 * 
	 * @author: yjleng
	 * @createTime: 2015-1-5 上午11:11:15
	 * @param delimiter
	 *            void
	 */
	public static void setDefaultListDelimiter(char delimiter) {
		AbstractConfiguration.setDefaultListDelimiter('%');
	}

	/**
	 * 读取资源信息
	 * 
	 * @return
	 * @throws ConfigurationException
	 */
	public static PropertiesConfiguration getMessageConfig()
			throws ConfigurationException {
		return new PropertiesConfiguration("message.properties");
	}

	/**
	 * 读取系统配置
	 * 
	 * @return
	 * @throws ConfigurationException
	 */
	public static PropertiesConfiguration getConfig()
			throws ConfigurationException {
		return new PropertiesConfiguration("config.properties");
	}

	/**
	 * 读取Service层配置
	 * 
	 * @return
	 * @throws ConfigurationException
	 */
	public static PropertiesConfiguration getServiceConfig()
			throws ConfigurationException {
		return new PropertiesConfiguration("service.properties");
	}

	/**
	 * 读取SQL配置
	 * 
	 * @return
	 * @throws ConfigurationException
	 */
	public static PropertiesConfiguration getSQLConfig()
			throws ConfigurationException {
		return new PropertiesConfiguration("sql.properties");
	}

	/**
	 * 读取CAS配置
	 * 
	 * @return
	 * @throws ConfigurationException
	 */
	public static PropertiesConfiguration getCASConfig()
			throws ConfigurationException {
		return new PropertiesConfiguration("cas.properties");
	}

	/**
	 * 读取resteasy配置
	 * 
	 * @author: yjleng
	 * @createTime: 2014-12-23 上午11:30:33
	 * @return
	 * @throws ConfigurationException
	 *             PropertiesConfiguration
	 */
	public static PropertiesConfiguration getRestEasyConfig()
			throws ConfigurationException {
		return new PropertiesConfiguration("resteasy.properties");
	}
}
