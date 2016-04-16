package com.iflytek.aio.common;

public class RestEasy {

	/**
	 * 权限系统地址
	 */
	public static final String PERMISSION_URL = "permission.resteasy.url";

	/**
	 * 通过ID查询资源
	 */
	public static final String RESOURCE_FIND_BY_ID = "permission.resource.findresource";

	/**
	 * 通过用户名查询权限
	 */
	public static final String AUTH_FIND_BY_ACCOUNTNAME = "permission.auth.finduserauth";

	/**
	 * 更改密码
	 */
	public static final String USER_UPDATE_PWD = "permission.user.updatepwd";

    /**
     * 检查用户登录
     */
    public static final String USER_CHECK_LOGIN = "permission.user.checkLogin";
    
    /**
     * 查询用户对象
     */
    public static final String USER_FIND_USER = "permission.user.findUser";
    
	/**
	 * 查询所有系统
	 */
	public static final String SYSTEM_FIND_ALL = "permission.systemInfo.findall";

	/**
	 * 查询所有组织机构
	 */
	public static final String ORG_FIND_ALL = "permission.organization.findall";

	/**
	 * 通过组织机构主键查询组织机构
	 */
	public static final String ORG_FIND_BY_ID = "permission.organization.findbyid";

	/**
	 * 通过组织机构编码查询组织机构
	 */
	public static final String ORG_FIND_BY_CODE = "permission.organization.findbycode";

}
