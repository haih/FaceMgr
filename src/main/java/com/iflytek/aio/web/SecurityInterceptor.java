/**	
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 */
package com.iflytek.aio.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.iflytek.aio.common.Constant;
import com.iflytek.aio.common.dto.UserFully;

/**
 * <pre>
 * *****************************************************************************
 * Copyrights @ 2015，Iflytek Information Co., Ltd All rights reserved.
 * Filename：SecurityInterceptor
 * Description：全局拦截器功能
 * Entity Author: ycli5
 * Finished：2015年4月17日 下午4:11:50
 * ****************************************************************************
 * </pre>
 */
public class SecurityInterceptor implements HandlerInterceptor {
	/** (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	/** (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/** (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {		
		UserFully userFully = (UserFully) request.getSession().getAttribute(
				Constant.SESSION_KEY);
		if (null == userFully) {
//			if (createSessionUser(request)) {
//				return true;
//			}
			// 如果是ajax请求响应头会有，x-requested-with
			if (isAjaxRequest(request)) { 
				// 在响应头设置session状态
				response.setHeader("sessionstatus", "timeout");
				response.sendError(HttpStatus.UNAUTHORIZED.value(),
                      "您已经太长时间没有操作,请刷新页面");

			} else {
				response.sendRedirect(request.getContextPath() + "/aiologin.do");
				//response.sendRedirect("/biometriclogin.do");
			}
			return false;
		}
		 
		return true;
	}
	
	/** 
	 * 判断是否为Ajax请求 
	* 
	* @param request HttpServletRequest 
	* @return 是true, 否false 
	*/  
	public boolean isAjaxRequest(HttpServletRequest request) {  
		//return request.getRequestURI().startsWith("/api");  
		String requestType = request.getHeader("X-Requested-With");  
		return requestType != null && requestType.equals("XMLHttpRequest");  
	}

}
