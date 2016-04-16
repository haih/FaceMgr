/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：SubjectServiceImpl.java                     
 * 
 * Description： 
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年7月3日上午9:28:58         Create
 */

package com.iflytek.aio.service;

import java.util.List;

import com.iflytek.aio.common.dto.MenuTree;
import com.iflytek.aio.common.exception.ServiceException;

/**
 * @desc: 菜单的Service层接口
 * @author: yhsu
 * @createTime: 2014-12-6 上午11:05:54
 * @version: 2.1
 */
public interface MenusService {

    /**
     * 通过传来的Type-id数组查询对应的菜单
     * 
     * @author: yhsu
     * @createTime: 2014-12-6 上午11:29:48
     * @return List<TreeNodeDTO>
     * @throws ServiceException
     *             异常
     */
    List<MenuTree> queryTopMenus() throws ServiceException;

    /**
     * 查询左侧的Tree
     * 
     * @author: yhsu
     * @createTime: 2014-12-6 下午02:31:33
     * @param id 编号
     * @param type
     *            top菜单类型标志是系统配置还是知识管理
     * @return List<TreeNodeDTO>
     * @throws ServiceException
     *             异常
     */
    List<MenuTree> queryLeftMenus(Long id, String type) throws ServiceException;

}
