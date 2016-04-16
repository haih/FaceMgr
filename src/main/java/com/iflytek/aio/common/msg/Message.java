/**	
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 */
package com.iflytek.aio.common.msg;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * <pre>
 * *****************************************************************************
 * Copyrights @ 2015，Iflytek Information Co., Ltd All rights reserved.
 * Filename：Message
 * Description：读取message配置信息
 * Entity Author: ycli5
 * Finished：2015年5月4日 下午4:25:32
 * ****************************************************************************
 * </pre>
 */
public abstract class Message {
	private String key;
	private String message;

	private static final String FILE_PATH = "/config/message.properties";

	/**
	 * @param key
	 * @param value
	 */
	public Message(String key) {
		super();
		this.key = key;
	}

	/**
	 * @return the key
	 */
	@JSONField(serialize = false, deserialize = false)
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	@JSONField(serialize = false, deserialize = false)
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		try {
			this.message = new PropertiesConfiguration(FILE_PATH)
					.getString(key);
			return this.message;
		} catch (ConfigurationException e) {
			return "提示信息未定义";
		}
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
