/**	
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 */
package com.iflytek.aio.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

/**
 * @desc: JSON WRAPPER
 * @author: cheney
 * @createTime: 2014-12-8
 * @version: 1.0
 */
public class JSONWrapper extends JSON {
	private static final String DATA_KEY = "data";

	public static final <T> T parseObjectWrapper(String text, Class<T> clazz) {
		return parseObjectWrapper(DATA_KEY, text, clazz);
	}

	public static final <T> T parseObjectWrapper(String key, String text,
			Class<T> clazz) {
		JSONObject jsonObj = JSONObject.parseObject(text);
		String data = jsonObj.getString(DATA_KEY);
		return parseObject(data, clazz, new Feature[0]);
	}
}
