/**	
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 */
package com.iflytek.aio.common.system.session;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.iflytek.aio.common.Constant;
import com.iflytek.aio.common.RestEasy;
import com.iflytek.aio.common.dto.ResourceAuthDTO;
import com.iflytek.aio.common.dto.UserFully;
import com.iflytek.aio.common.util.ConfigRead;
import com.iflytek.aio.common.util.JSONWrapper;
import com.iflytek.aio.common.util.LoggerUtils;
import com.iflytek.aio.service.impl.RestEasyService;

/**
 * @desc: 获取当前系统信息和权限
 * @author: cheney
 * @createTime: 2014-12-6
 * @version: 1.0
 */
public class SessionHander {
	/**
	 * 日志记录
	 */
	private static LoggerUtils logger = LoggerUtils
			.getLogger(SessionHander.class);

	/**
	 * 获取当前登陆用户ID
	 * 
	 * @return 用户ID
	 */
	public static String getUserId() {
		UserFully userFully = getUserFully();
		if (null != userFully) {
			return String.valueOf(userFully.getUserId());
		}
		return null;
	}

	/**
	 * 获取当前给用户的账号名称
	 * 
	 * @return 当前登陆用户的账号名称
	 */
	public static String getAccountName() {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
//				.getRequestAttributes()).getRequest();
//		return request.getRemoteUser();
		UserFully userFully = getUserFully();
		if (null != userFully) {
			return userFully.getAccountName();
		}
		return null;
	}

	/**
	 * 获取当前用户的组织机构编码
	 * 
	 * @return 当前登陆用户的组织机构编码
	 */
	public static String getOrgCode() {
		UserFully userFully = getUserFully();
		if (null != userFully) {
			return userFully.getOrgCode();
		}
		return null;
	}

	/**
	 * 获取切换后的组织机构
	 * 
	 * @return 切换后的组织机构编码 如果未切换 返回当前用户的组织机构编码
	 */
	public static String getChangedOrgCode() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		String currOrgCode = (String) session
				.getAttribute(Constant.CURRENT_ORGCODE_KEY);
		if (StringUtils.isEmpty(currOrgCode)) {
			return getOrgCode();
		}
		return currOrgCode;
	}

	/**
	 * 判断是否进行了组织机构切换
	 * 
	 * @return true 切换 false 未切换
	 */
	public static boolean isChangedOrg() {
		String orgCode = getOrgCode();
		String currOrgCode = getChangedOrgCode();
		if (StringUtils.isEmpty(orgCode) || StringUtils.isEmpty(currOrgCode)) {
			return true;
		}
		if (orgCode.equals(currOrgCode)) {
			return false;
		}
		return true;
	}

	/**
	 * 获取当前的用户信息,如果当前系统中的用户信息失效, 根据CAS提供的用户名重新获取用户信息,直到CAS失效.
	 * 
	 * @return UserFully元素,包含用户信息和拥有权限信息
	 */
	public static UserFully getUserFully() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		UserFully userFully = (UserFully) session
				.getAttribute(Constant.SESSION_KEY);
		if (null == userFully) {
			String accountName = request.getRemoteUser();
			logger.debug("getUserFully", "从CAS中获取当前登陆的用户名:" + accountName);
			Map<String, String> params = new HashMap<String, String>();
			params.put("accountName", accountName);
			params.put("contextPath", ConfigRead.newInstance().readValue(Constant.CONTEXT_PATH));
			try {
				String jsonResult = RestEasyService.getInstance().getResult(
						params, RestEasy.AUTH_FIND_BY_ACCOUNTNAME);
				logger.debug("getUserFully", "根据CAS登陆用户名获取用户权限:" + jsonResult);
				if (!StringUtils.isEmpty(jsonResult)) {
					userFully = JSONWrapper.parseObjectWrapper(jsonResult,
							UserFully.class);
					session.setAttribute(Constant.SESSION_KEY, userFully);
				}
			} catch (Exception e) {
				logger.error("getUserFully", "调用权限系统获取用户详细信息发生异常", e);
				return null;
			}
		}
		return userFully;
	}

	/**
	 * 刷新后重新设置Session内的用户信息
	 * 
	 * @author: yhsu
	 * @createTime: 2014-12-15 下午02:35:44 void
	 */
	public static void restUserFully() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
//		session.removeAttribute(Constant.SESSION_KEY);
//		session.removeAttribute(Constant.CURRENT_ORGCODE_KEY);
		getUserFully();
	}

	/**
	 * 获取当前用户的所有资源信息
	 * 
	 * @return
	 */
	public static Map<String, ResourceAuthDTO> getResources() {
		UserFully userFully = getUserFully();
		if (null != userFully) {
			return userFully.getResourceAuths();
		}
		return null;
	}

	/**
	 * 删除当前登陆用户的session信息
	 */
	public static void removeUserFully() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute(Constant.SESSION_KEY);
		session.removeAttribute(Constant.CURRENT_ORGCODE_KEY);
		session.invalidate();
	}

}
