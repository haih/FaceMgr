package com.iflytek.aio.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * @desc: 读取service.properties配置文件的工具类
 * @author: binyang4
 * @createTime: 2014-6-12 下午03:08:39
 * @version: 2.0
 */
public class ISMProperties {
	/**
	 * 记录日志
	 */
	private static LoggerUtils log = LoggerUtils.getLogger(ISMProperties.class);

	/**
	 * 配置文件名称
	 */
	private static String propertyFile;

	/**
	 * 声明Properties
	 */
	private Properties p;

	/**
	 * 声明一个ISMProperties对象
	 */
	private static ISMProperties ismp;

	/**
	 * 私有构造函数来加载文件
	 */
	private ISMProperties(String fileName) {
		ISMProperties.propertyFile = fileName;
		loadProperites();
	}

	/**
	 *获取单例的配置文件并读取
	 * 
	 * @author: binyang4
	 * @createTime: 2014-6-12 下午03:10:58
	 * @return ISMProperties
	 */
	public static ISMProperties getIns(String fileName) {

		if (StringUtils.isBlank(fileName)) {
			log.debug("getIns", "解析的 properties 文件不能为空");
			return null;
		}

		if (ismp == null) {
			ismp = new ISMProperties(fileName);
		} else if (!fileName.equals(ISMProperties.propertyFile)) {
			ismp = new ISMProperties(fileName);
		}

		return ismp;
	}

	/**
	 *加载properties
	 * 
	 * @author: binyang4
	 * @createTime: 2014-6-12 下午03:11:09 void
	 */
	public void loadProperites() {
		p = new Properties();
		InputStream in = ISMProperties.class.getClassLoader()
				.getResourceAsStream(propertyFile);
		try {
			p.load(in);
			in.close();
		} catch (IOException e) {
			log.error("loadProperites", "文件读取错误.", e);
		}
	}

	/**
	 * 根据key获取value
	 * 
	 * @author: binyang4
	 * @createTime: 2014-6-12 下午03:11:21
	 * @param key
	 *            值
	 * @return String 为空则直接返回key
	 */
	public String getValue(String key) {

		String value = p.getProperty(key);

		if (StringUtils.isBlank(value)) {
			log.debug("getValue", "未根据 key 找到 message");
			return "";
		}

		return value;
	}

	public static void main(String[] args) {
		ISMProperties ism = ISMProperties.getIns("service.properties");
		System.out.println(ism.getValue("1"));
	}
}
