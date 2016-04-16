/**	
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 */
package com.iflytek.aio.common.msg;

import com.alibaba.fastjson.JSONObject;
import com.iflytek.aio.common.util.StringUtils;

/**
 * <pre>
 * *****************************************************************************
 * Copyrights @ 2015，Iflytek Information Co., Ltd All rights reserved.
 * Filename：Result
 * Description： Service返回
 * Entity Author: ycli5
 * Finished：2015年5月4日 下午4:26:15
 * ****************************************************************************
 * </pre>
 */
public class Result<T> extends Message {
	// 返回结果的状态
	private boolean status;
	// 返回对象
	private T data;

	/**
	 * @param status
	 * @param data
	 */
	public Result(boolean status, String key, T data) {
		super(key);
		this.status = status;
		this.data = data;
	}

	/**
	 * @param status
	 * @param data
	 */
	public Result(boolean status, String key) {
		super(key);
		this.status = status;
	}

	/**
	 * @param status
	 * @param data
	 */
	public Result(String key) {
		super(key);
		if (!StringUtils.isEmpty(key)) {
			if (key.startsWith(MsgKey.SUCC_PRFIX)) {
				this.status = true;
			} else if (key.startsWith(MsgKey.ERROR_PRFIX)) {
				this.status = false;
			}
		}
	}

	/**
	 * @param status
	 * @param data
	 */
	public Result(String key, T data) {
		super(key);
		this.data = data;
		if (!StringUtils.isEmpty(key)) {
			if (key.startsWith(MsgKey.SUCC_PRFIX)) {
				this.status = true;
			} else if (key.startsWith(MsgKey.ERROR_PRFIX)) {
				this.status = false;
			}
		}
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
//		return "Result [data=" + data + ", status=" + status + ", getKey()="
//				+ getKey() + ", getMessage()=" + getMessage() + "]";
	    return JSONObject.toJSONString(this);
	}

}
