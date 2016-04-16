package com.iflytek.aio.common.util;

import java.util.Properties;

import com.iflytek.aio.common.Constant;

/** 
 * *****************************************************************************
 * Copyrights @ 2015，Iflytek Information Co., Ltd All rights reserved.
 * Filename：ConfigRead
 * Description：读取配置在tip中的提示信息
 * Entity Author: ycli5
 * Finished：2015年4月7日 下午1:49:53
 * ****************************************************************************
 */
public class ConfigRead {
	
	/**
	 * 日志记录器
	 */
	private static LoggerUtils logger = LoggerUtils.getLogger(ConfigRead.class);
	
	/**
	* 对象
	*/
	private  static ConfigRead configRead = null;
	
	/**
	 * object
	 */
	private static Object object = new Object();
	
	/**
	 * 配置文件的读取
	 */
	private  Properties props = null;
	
	/**
	 * 单例
	 */
	private ConfigRead(){
		try {
			props = new Properties();
			props.load(ConfigRead.class.getResourceAsStream(Constant.FilePath.CONFIGFILE_PATH));
		} catch (Exception e) {
			logger.error("ConfigRead","读取配置文件 异常",e);
		}
		
	}
	
	/**
	* 单例模式
	* @author: yhsu
	* @createTime: 2014年7月3日 下午2:37:46
	* @return ReadProp
	*/
	public static ConfigRead newInstance(){
		if(configRead==null){
			synchronized(object){
				if(configRead==null){
					configRead = new ConfigRead(); 
				}
			}
		}
		return configRead;
	}
	/**
	 * 根据key读取配置文件中的参数
	 * 
	 * @param key
	 * @return value
	 */
	public String readValue(String key) {
	
		return props.getProperty(key);
	}
	
}
