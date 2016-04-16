package com.iflytek.aio.common.util;

import java.util.List;

/**
 * @desc: 判断对象是否为空
 * @author: yhsu
 * @createTime: 2014-6-13 下午03:41:28
 * @version: 2.0
 */
public class EmptyUtils {
	

	/**
	 * 判断Long型的对象是否为空或小于零
	 * 
	 * @author: yhsu
	 * @createTime: 2014-6-13 下午03:45:28
	 * @param id
	 * @return boolean
	 */
	public static boolean isIllegalId(Long id) {

		if (null == id || id <= 0) {
			return true;
		}
		return false;
	}

	/**
	 *判断数组是否为空或零
	 * 
	 * @author: binyang4
	 * @createTime: 2014-6-23 下午08:23:06
	 * @param objs
	 * @return boolean
	 */
	public static boolean isIllegalArray(Object[] objs) {

		if (objs == null || objs.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断List是否为空或者长度小于零
	 * 
	 * @author: yhsu
	 * @createTime: 2014-6-13 下午03:47:18
	 * @param list
	 * @return boolean
	 */
	public static boolean isEmpty(List<?> list) {

		if (null == list || list.size() <= 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否为空或者为""字符串
	 * 
	 * @author: yhsu
	 * @createTime: 2014-6-13 下午03:47:18
	 * @param list
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {

		if (null == str || "".equals(str.trim())) {
			return true;
		}
		return false;
	}
}
