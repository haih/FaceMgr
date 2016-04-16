package com.iflytek.aio.common.dto;

import java.io.Serializable;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @desc: 封装用户对象,包含用户所属组织机构和拥有的权限
 * @author: cheney
 * @createTime: 2014-12-6
 * @version: 1.0
 */
public class UserFully implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2365032617794453101L;
	/**
	 * 用户逐渐
	 */
	private Long userId;
	/**
	 * 用户账号
	 */
	private String accountName;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 英文名称
	 */
	private String userEngName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 组织机构ID
	 */
	private Long orgId;
	/**
	 * 组织机构名称
	 */
	private String orgName;
	/**
	 * 组织机构英文名称
	 */
	private String orgEngName;
	/**
	 * 组织机构编码
	 */
	private String orgCode;
	/**
	 * 组织机构父类ID
	 */
	private Long orgParentId;
	/**
	 * 系统名称
	 */
	private String systemName;
	/**
	 * 系统上下文
	 */
	private String contextPath;

	/**
	 * 资源信息
	 */
	private Map<String, ResourceAuthDTO> resourceAuths;

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userEngName
	 */
	public String getUserEngName() {
		return userEngName;
	}

	/**
	 * @param userEngName
	 *            the userEngName to set
	 */
	public void setUserEngName(String userEngName) {
		this.userEngName = userEngName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 *            the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the orgEngName
	 */
	public String getOrgEngName() {
		return orgEngName;
	}

	/**
	 * @param orgEngName
	 *            the orgEngName to set
	 */
	public void setOrgEngName(String orgEngName) {
		this.orgEngName = orgEngName;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 *            the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the orgParentId
	 */
	public Long getOrgParentId() {
		return orgParentId;
	}

	/**
	 * @param orgParentId
	 *            the orgParentId to set
	 */
	public void setOrgParentId(Long orgParentId) {
		this.orgParentId = orgParentId;
	}

	/**
	 * @return the resourceAuths
	 */
	public Map<String, ResourceAuthDTO> getResourceAuths() {
		return resourceAuths;
	}

	/**
	 * @param resourceAuths
	 *            the resourceAuths to set
	 */
	public void setResourceAuths(Map<String, ResourceAuthDTO> resourceAuths) {
		this.resourceAuths = resourceAuths;
	}

	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}

	/**
	 * @param systemName
	 *            the systemName to set
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	/**
	 * @return the contextPath
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * @param contextPath the contextPath to set
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
