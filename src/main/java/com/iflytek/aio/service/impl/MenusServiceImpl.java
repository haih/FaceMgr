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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.iflytek.aio.common.Constant;
import com.iflytek.aio.common.RestEasy;
import com.iflytek.aio.common.dto.MenuTree;
import com.iflytek.aio.common.exception.ServiceException;
import com.iflytek.aio.common.system.session.SessionHander;
import com.iflytek.aio.common.util.ConfigRead;
import com.iflytek.aio.common.util.EmptyUtils;
import com.iflytek.aio.common.util.LoggerUtils;
import com.iflytek.aio.service.MenusService;

/**
 * @desc: 左侧菜单的Service层实现类
 * @author: yhsu
 * @createTime: 2014-12-6 上午11:12:50
 * @version: 2.1
 */
/**
 * @desc:
 * @author: yhsu
 * @createTime: 2014-12-9 上午10:05:36
 * @version: 2.1
 */
@Service
public class MenusServiceImpl implements MenusService {

    /**
     * RestEasy工具类对象
     */
    private static RestEasyService restEasyService = RestEasyService
            .getInstance();
    /**
     * restEasy返回结果的data的key
     */
    private static final String DATA_KEY = "data";
    /**
     * 日志
     */
    private LoggerUtils logger = LoggerUtils.getLogger(this.getClass());

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.iflytek.ism.menu.service.MenuService#queryAllMenus(java.lang.String
     * [], java.util.Map)
     */
    @Override
    public List<MenuTree> queryTopMenus() throws ServiceException {
        String methodName = "queryTopMenus";
        try {
            // 查询权限系统返回的对象
            Map<String, String> params = new HashMap<String, String>();
            params.put("accountName", SessionHander.getAccountName());
            params.put("contextPath",
                    ConfigRead.newInstance().readValue(Constant.CONTEXT_PATH));
            params.put("resourcePId", null);
            String resourcesStr = restEasyService.getResult(params,
                    RestEasy.RESOURCE_FIND_BY_ID);
            // 查找出第一层的资源，并进行封装和返回
            List<MenuTree> menuTrees = transResStr2MenuTrees(resourcesStr);
            return menuTrees;
        } catch (Exception e) {
            logger.error(methodName, "查询顶部菜单时出错", e);
            throw new ServiceException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.iflytek.ism.menu.service.MenuService#queryLeftMenus(java.lang.Long,
     * java.util.Map)
     */
    @Override
    public List<MenuTree> queryLeftMenus(Long id, String type)
        throws ServiceException {
        String methodName = "queryLeftMenus";
        if (EmptyUtils.isIllegalId(id)) {
            logger.debug(methodName, "通过ID查询左侧菜单的时候传入的Id为空");
            return null;
        }
        try {

            Map<String, String> params = new HashMap<String, String>();
            params.put("accountName", SessionHander.getAccountName());
            params.put("contextPath",
                    ConfigRead.newInstance().readValue("aio.context.path"));
            params.put("resourcePId", String.valueOf(id));

            // 根据传入的ID 查询出此ID的所有子节点资源
            String resourcesStr = restEasyService.getResult(params,
                    RestEasy.RESOURCE_FIND_BY_ID);

            return transResStr2MenuTrees(resourcesStr);

        } catch (Exception e) {
            logger.error(methodName, "通过ID查询左侧菜单的时出错", e);
            throw new ServiceException(e);
        }

    }

    /**
     * 将资源的json字符串转换成树的集合
     * 
     * @author: yhsu
     * @createTime: 2014-12-6 下午04:22:47
     * @param resourcesStr
     *            是否为左侧菜单的字符转换
     * @return List<menuTreeDTO>
     */
    private List<MenuTree> transResStr2MenuTrees(String resourcesStr) {
        String methodName = "transResourceStr2menuTrees";
        if (StringUtils.isEmpty(resourcesStr)) {
            return null;
        }
        try {
            JSONObject jsonObj = JSONObject.parseObject(resourcesStr);
            String data = jsonObj.getString(DATA_KEY);
            ArrayList<MenuTree> menuTrees = JSON.parseObject(data,
                    new TypeReference<ArrayList<MenuTree>>() {
                    });
            return menuTrees;
        } catch (Exception e) {
            logger.error(methodName, "将restEasy返回的数据序列化成Tree对象时出错", e);
            return null;
        }
    }
}
