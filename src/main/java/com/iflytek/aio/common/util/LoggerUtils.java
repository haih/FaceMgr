package com.iflytek.aio.common.util;

import org.apache.log4j.Logger;


/**
 * @desc: 日志记录
 * @author: dhzheng
 * @createTime: 2014-6-6 下午04:30:53
 * @version: 1.0
 */
public class LoggerUtils {

	/**
	 * 按名称构造
	 * 
	 * @param name
	 *            名称
	 */
	private LoggerUtils(String name) {
		logger = Logger.getLogger(name);
	}

	/**
	 * 按类构造
	 * 
	 * @param clazz
	 *            类
	 */
	private LoggerUtils(Class<?> clazz) {
		logger = Logger.getLogger(clazz);
	}

	/**
	 * 获取对象
	 * 
	 * @author: dhzheng
	 * @createTime: 2014-6-6 下午04:32:06
	 * @param name
	 *            名称
	 * @return LoggerUtils
	 */
	public static LoggerUtils getLogger(String name) {
		return new LoggerUtils(name);
	}

	/**
	 * 获取对象
	 * 
	 * @author: dhzheng
	 * @createTime: 2014-6-6 下午04:32:21
	 * @param clazz
	 *            类
	 * @return LoggerUtils
	 */
	public static LoggerUtils getLogger(Class<?> clazz) {
		return new LoggerUtils(clazz);
	}

	/**
	 * 格式化消息
	 * 
	 * @author: dhzheng
	 * @createTime: 2014-6-6 下午04:32:35
	 * @param methodName
	 *            方法名
	 * @param message
	 *            错误信息
	 * @return String
	 */
	private String getMessage(String methodName, String message) {

		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isBlank(methodName)) {
			sb.append(methodName);
		}
		if (!StringUtils.isBlank(message)) {
			sb.append(message);
		}

		return sb.toString();
	}

	/**
	 * 输出 info
	 * 
	 * @author: dhzheng
	 * @createTime: 2014-6-6 下午04:32:52
	 * @param methodName
	 *            方法名
	 * @param message
	 *            错误信息
	 * @param t
	 *            void
	 */
	public void info(String methodName, String message, Throwable t) {
		if (!logger.isInfoEnabled()) {
			return;
		} else {

			logger.info(getMessage(methodName, message), t);
			return;
		}
	}

	/**
	 * 输出 info
	 * 
	 * @author: dhzheng
	 * @createTime: 2014-6-6 下午04:33:19
	 * @param methodName
	 *            方法名
	 * @param message
	 *            void
	 */
	public void info(String methodName, String message) {
		if (!logger.isInfoEnabled()) {
			return;
		} else {

			logger.info(getMessage(methodName, message));
			return;
		}
	}

	/**
	 * 输出 debug
	 * 
	 * @author: dhzheng
	 * @createTime: 2014-6-6 下午04:33:29
	 * @param methodName
	 *            方法名
	 * @param message
	 *            错误信息
	 * @param t
	 *            void
	 */
	public void debug(String methodName, String message, Throwable t) {
		if (!logger.isDebugEnabled()) {
			return;
		} else {

			logger.debug(getMessage(methodName, message), t);
			return;
		}
	}

	/**
	 * 输出 debug
	 * 
	 * @author: dhzheng
	 * @createTime: 2014-6-6 下午04:33:41
	 * @param methodName
	 *            方法名
	 * @param message
	 *            void
	 */
	public void debug(String methodName, String message) {
		if (!logger.isDebugEnabled()) {
			return;
		} else {

			logger.debug(getMessage(methodName, message));
			return;
		}
	}

	/**
	 * 输出 warn
	 * 
	 * @author: dhzheng
	 * @createTime: 2014-6-6 下午04:33:50
	 * @param methodName
	 *            方法名
	 * @param message
	 *            错误信息
	 * @param t
	 *            void
	 */
	public void warn(String methodName, String message, Throwable t) {
		logger.warn(getMessage(methodName, message), t);
	}

	/**
	 * 输出 warn
	 * 
	 * @author: dhzheng
	 * @createTime: 2014-6-6 下午04:33:59
	 * @param methodName
	 *            方法名
	 * @param message
	 *            void
	 */
	public void warn(String methodName, String message) {
		logger.warn(getMessage(methodName, message));
	}

	/**
	 * 输出 error
	 * 
	 * @author: dhzheng
	 * @createTime: 2014-6-6 下午04:34:11
	 * @param methodName
	 *            方法名
	 * @param message
	 *            错误信息
	 * @param t
	 *            void
	 */
	public void error(String methodName, String message, Throwable t) {
		logger.error(getMessage(methodName, message), t);
	}

	/**
	 * 输出 error
	 * 
	 * @author: dhzheng
	 * @createTime: 2014-6-6 下午04:34:27
	 * @param methodName
	 *            方法名
	 * @param message
	 *            void
	 */
	public void error(String methodName, String message) {
		logger.error(getMessage(methodName, message));
	}

	/**
	 * 输出 fatal
	 * 
	 * @author: dhzheng
	 * @createTime: 2014-6-6 下午04:34:34
	 * @param methodName
	 *            方法名
	 * @param message
	 *            错误信息
	 * @param t
	 *            void
	 */
	public void fatal(String methodName, String message, Throwable t) {
		logger.fatal(getMessage(methodName, message), t);
	}

	/**
	 * fatal
	 * 
	 * @author: dhzheng
	 * @createTime: 2014-6-6 下午04:34:41
	 * @param methodName
	 *            方法名
	 * @param message
	 *            void
	 */
	public void fatal(String methodName, String message) {
		logger.fatal(getMessage(methodName, message));
	}

	/**
	 * 引用 log4j
	 */
	private Logger logger;
}
