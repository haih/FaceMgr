package com.iflytek.aio.common.util;

import com.alibaba.fastjson.JSON;

/**
 * @desc: 信息返回类
 * @author: yhsu
 * @createTime: 2014年7月2日 上午10:43:46
 * @version: 1.0
 */
public class MessageDto<T> {

	/**
	 * 标志操作位 true：成功；false：失败
	 */
	private boolean flag;

	/**
	 * 提示信息
	 */
	private String msg;

	/**
	 * 实体类
	 */
	private T data;

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public MessageDto() {
		super();
	}

	public MessageDto(boolean flag) {
		super();
		this.flag = flag;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
