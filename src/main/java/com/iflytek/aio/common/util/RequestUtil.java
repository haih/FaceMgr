/**	
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 */
package com.iflytek.aio.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @desc: 获取IP地址的工具类
 * @author: cheney
 * @createTime: 2014-11-7
 * @version: 1.0
 */
public class RequestUtil {
	/**
	 * 获取远程IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	/**
	 * 获取真实的IP地址,包含代理
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		return request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase( // ajax超时处理
						"XMLHttpRequest");
	}

	/**
	 * @author: ycli5
	 * @createTime: 2015年1月5日 上午8:41:50
	 * @description:获取session值
	 * @param request
	 *            请求
	 * @param key
	 *            key
	 * @return T
	 */
	public static <T> T findSession(HttpServletRequest request, String key) {

		Object obj = request.getSession().getAttribute(key);
		if (obj != null) {
			return (T) obj;
		}
		return null;
	}
}
