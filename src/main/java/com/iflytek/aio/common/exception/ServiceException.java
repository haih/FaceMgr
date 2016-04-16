package com.iflytek.aio.common.exception;
/**	
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 */

/**
 * @desc: if operation service method happend error is throws ServiceException
 *        exception.
 * @author: cheney
 * @createTime: 2014-6-9 上午10:18:42
 * @version: 2.0
 */
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4539435900462182406L;

	/**
	 * default construction method
	 */
	public ServiceException() {

	}

	/**
	 * throws a new exception,include art message.
	 * 
	 * @param message
	 *            exception message
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * throws a new exception, include art message and throwable
	 * 
	 * @param message
	 *            art message
	 * @param cause
	 *            throwable
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * throws a new exception, include throwable object
	 * 
	 * @param cause
	 *            throwable object
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}
}
