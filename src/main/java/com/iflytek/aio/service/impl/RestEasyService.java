/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：FaceServiceImpl.java                        
 * 
 * Description： 
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年7月1日上午9:08:09         Create
 */

package com.iflytek.aio.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iflytek.aio.common.Constant;
import com.iflytek.aio.common.RestEasy;
import com.iflytek.aio.common.dto.ServiceResult;
import com.iflytek.aio.common.dto.UserFully;
import com.iflytek.aio.common.exception.ServiceException;
import com.iflytek.aio.common.util.HttpHelper;
import com.iflytek.aio.common.util.LoggerUtils;
import com.iflytek.aio.common.util.MD5Util;
import com.iflytek.aio.common.util.prop.PropConfigFactory;

/**
 * @desc: 获取RestEasy的返回结果
 * @author: yhsu
 * @createTime: 2014-12-3 上午11:23:20
 * @version: 2.0
 */
@Service
public class RestEasyService {
    /**
     * 单例对象
     */
    private static RestEasyService restEasyService = null;

    /**
     * 日志记录文件
     */
    private static LoggerUtils logger = LoggerUtils
            .getLogger(RestEasyService.class);

    /**
     * 读取restEasyService的配置文件
     */
    private static PropertiesConfiguration prop = getProperties();

    /**
     * 权限系统url
     */
    private static final String PERMISSION_URL = getPermissionUrl();

    /**
     * 私有构造方法
     */
    private RestEasyService() {
        super();
    }
    
    /**
     * 单例获取方法
     * 
     * @author: yhsu
     * @createTime: 2014-12-3 下午02:23:25
     * @return RestEasyService
     */
    public static RestEasyService getInstance() {
        if (null == restEasyService) {
            restEasyService = new RestEasyService();
        }
        return restEasyService;
    }

    /**
     * 读取restEasyService的配置文件
     * 
     * @author: yjleng
     * @createTime: 2014-12-24 上午10:45:11
     * @return PropertiesConfiguration
     * @throws ConfigurationException
     */
    private static PropertiesConfiguration getProperties() {
        try {
            return PropConfigFactory.getRestEasyConfig();
        } catch (ConfigurationException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 获取权限系统url
     * 
     * @author: yjleng
     * @createTime: 2014-12-24 上午10:39:23
     * @return String
     */
    private static String getPermissionUrl() {
        return prop.getString(RestEasy.PERMISSION_URL);
    }

    /**
     * 通过RestEasy查询结果
     * 
     * @author: yhsu
     * @createTime: 2014-12-3 下午04:01:02
     * @param params
     *            ：查询的参数，key:服务端的参数key；value:参数值
     * @param key
     *            ：查询类型 在Constant.RestEasyConfigKey中
     * @return String
     * @throws ServiceException 服务异常
     */
    public String getResult(Map<String, String> params, String key)
        throws ServiceException {
        String method = "getRoleAuth";
        if (StringUtils.isBlank(key)) {
            logger.error(method, "调用权限系统查询用户系统权限时参数错误");
            return null;
        }
        try {
            HttpHelper httpHelper = new HttpHelper();
            String url = PERMISSION_URL + prop.getString(key);
            String result = httpHelper.get(url, "UTF-8", params);
            if (!StringUtils.isBlank(httpHelper.getMessage())) {
                logger.error(method, "调用权限系统查询用户系统权限时出错");
                throw new ServiceException();
            }
            return result;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 修改密码
     * 
     * @author: yhsu
     * @createTime: 2014-12-9 下午08:25:30
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     * @return ServiceResult
     */
    public ServiceResult updatePwd(String newPassword, String oldPassword) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("userId", "userId");
            params.put("newPassword", MD5Util.MD5(newPassword));
            params.put("oldPassword", MD5Util.MD5(oldPassword));
            String result = RestEasyService.getInstance().getResult(params,
                    RestEasy.USER_UPDATE_PWD);
            if (StringUtils.isBlank(result)) {
                return new ServiceResult(Constant.MessageMapping.FAILURE, false);
            }
            JSONObject resultObj = JSONObject.parseObject(result);
            boolean status = resultObj.getBooleanValue("status");
            String message = resultObj.getString("message");
            if (!status) {
                return new ServiceResult((Object) message, false);
            }
            return new ServiceResult((Object) message, true);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 登录查询
     * 
     * @author: haihu
     * @createTime: 2015-05-18 下午05:13:29
     * @param username
     *            用户名 
     * @param password 密码
     * @return ServiceResult
     */
    public ServiceResult loginCheck(String username, String password) {
        String methodName = "loginCheck";

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return null;
        }
        try {
            Map<String, String> params = new HashMap<String, String>();
            HttpHelper httpHelper = new HttpHelper();
            String url = PERMISSION_URL
                    + prop.getString(RestEasy.USER_CHECK_LOGIN) + "/"
                    + username + "/" + MD5Util.MD5(password);
            String result = httpHelper.get(url, "UTF-8", params);
            if (StringUtils.isBlank(result)) {
                return new ServiceResult("idauth.login.error", false);
            }
            System.out.println(result);
            JSONObject resultObj = JSONObject.parseObject(result);
            boolean status = resultObj.getBooleanValue("status");
            JSONObject data = resultObj.getJSONObject("data");
            if (null == data) {
                return new ServiceResult(
                        Constant.MessageMapping.LOGIN_FAILURE_STRING, false);
            }
            JSONObject org = data.getJSONObject("org");
            if (status) {
                UserFully user = new UserFully();
                user.setOrgCode(org.getString("orgCode"));
                user.setAccountName(data.getString("accountName"));
                user.setPassword(data.getString("password"));
                user.setUserName(data.getString("name"));
                user.setUserId(data.getLong("id"));
                return new ServiceResult(user, true);
            } else {
                return new ServiceResult(
                        Constant.MessageMapping.LOGIN_FAILURE_STRING, false);
            }
        } catch (Exception e) {
            logger.error(methodName, "检测用户账号信息出错", e);
            return new ServiceResult(
                    Constant.MessageMapping.LOGIN_FAILURE_STRING, false);
        }
    }
}
