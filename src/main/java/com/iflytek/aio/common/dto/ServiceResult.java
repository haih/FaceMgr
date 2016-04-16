package com.iflytek.aio.common.dto;

import java.io.Serializable;
import java.text.MessageFormat;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.alibaba.fastjson.annotation.JSONField;
import com.iflytek.aio.common.Message;
import com.iflytek.aio.common.exception.ServiceException;
import com.iflytek.aio.common.util.LoggerUtils;
import com.iflytek.aio.common.util.StringUtils;

/**
 * @desc: 业务层的返回值
 * @author: dhzheng
 * @createTime: 2014-6-16 上午09:43:58
 * @version: 1.0
 */

public class ServiceResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4169687825182783734L;

	/**
	 * 日志及记录
	 */
	private static final LoggerUtils log = LoggerUtils
			.getLogger(ServiceResult.class);

	/**
	 * 属性文件名称
	 */
	private static String propertyFile = "config/message.properties";

	/**
	 * service 方法实际返回值
	 */
	private Object result;

	/**
	 * 消息 id, 不参与序列化
	 */
	@JSONField(serialize = false)
	private String messageId;

	/**
	 * 消息内容
	 */
	private String message;

	/**
	 * 标识操作是否成功，用于页面判断
	 */
	private boolean success;

	/**
	 * 消息类型
	 */
	private int messageType;

	/**
	 * 默认构造方法
	 */
	public ServiceResult() {
		super();
	}

	/**
	 * 带参数的构造方法
	 * 
	 * @param result
	 * @param messageId
	 * @param success
	 */
	public ServiceResult(Object result, String messageId, boolean success) {
		super();
		this.result = result;
		this.success = success;
		setMessageId(messageId);
	}

	/**
	 * 构造方法重载
	 * 
	 * @param result
	 * @param messageId
	 * @param messageFillings
	 * @param success
	 */
	public ServiceResult(Object result, String messageId,
			Object[] messageFillings, boolean success) {
		super();
		this.result = result;
		this.success = success;
		setMessageId(messageId, messageFillings);
	}

	/**
	 * 构造方法重载
	 * 
	 * @param messageId
	 * @param success
	 */
	public ServiceResult(String messageId, boolean success) {
		this(null, messageId, success);
	}

	/**
	 * 构造方法重载
	 * 
	 * @param messageId
	 * @param messageFillings
	 * @param success
	 */
	public ServiceResult(String messageId, Object[] messageFillings,
			boolean success) {
		this(null, messageId, messageFillings, success);
	}

	/**
	 * 构造方法重载
	 * 
	 * @param result
	 * @param success
	 */
	public ServiceResult(Object result, boolean success) {
		this(result, null, success);
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * 
	 * @author: dhzheng
	 * @createTime: 2014-6-20 上午08:46:44
	 * @param messageId
	 *            void
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
		this.messageType = Message.getType(messageId);

		if (!StringUtils.isBlank(messageId)) {
			setMessageId(messageId, null);
		}
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId(String messageId, Object[] args) {
		try {
			String message = new PropertiesConfiguration(propertyFile)
					.getString(messageId);

			if (!StringUtils.isBlank(message)) {
				message = MessageFormat.format(message, args);
			}

			this.message = message;

		} catch (ConfigurationException e) {

			log.error("setMessageId", "消息获取失败");
			throw new ServiceException(e);
		}

	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the messageType
	 */
	public int getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType
	 *            the messageType to set
	 */
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

}
