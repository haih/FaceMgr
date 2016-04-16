/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：SubjectMapper.java						
 * 
 * Description：	
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年7月3日上午9:32:01         Create
 */
package com.iflytek.aio.mapper;

import java.util.HashMap;
import java.util.List;

import com.iflytek.aio.entity.Subject;

/**
 * @author haihu
 *
 * @create 2015年7月3日 上午9:32:01
 *
 * @version 1.0
 * 
 * @description
 * 
 */
public interface SubjectMapper {
    /**
     * @author: haihu
     * @createTime: 2015年6月30日 下午5:09:42
     * @description: 新增单条主题模块信息
     * @param subject
     *            主题实例
     */
    void save(Subject subject);

    /**
     * @author: haihu
     * @createTime: 2015年6月30日 下午5:09:42
     * @description: 更新单条主题模块信息
     * @param subject
     *            主题实例
     */
    void update(Subject subject);

    /**
     * @descrption 通过编号删除主题信息
     * @author haihu
     * @create 2015年7月3日下午5:05:57
     * @version 1.0
     * @param id
     *            编号参数
     */
    void deleteById(Long id);

    /**
     * @descrption 通过编号查找主题信息
     * @author haihu
     * @create 2015年7月3日下午5:05:57
     * @version 1.0
     * @param id
     *            编号参数
     * @return Subject
     */
    Subject findById(Long id);

    /**
     * @description: 保存二维码存储路径
     * @author: haihu
     * @createTime: 2015年6月30日 下午5:09:42
     * @param subjectId
     *            主题ID
     * @param qrcodePath
     *            二维码存放路径
     */
    void saveQRCode(Long subjectId, String qrcodePath);

    /**
     * @description: 时间更新
     * @author: haihu
     * @createTime: 2015年6月30日 下午5:09:42
     * @param subjectId
     *            主题ID
     * @param updateTime
     *            更新时间
     */
    void timeUpdate(Long subjectId, String updateTime);

    /**
     * @descrption 通过组织id查找所有主题名称(web端主题名称展示)
     * @author haihu
     * @create 2015年7月3日下午5:05:57
     * @version 1.0
     * @param orgCode 组织编号
     * @return List<HashMap<String, String>>
     */
    List<HashMap<String, String>> findAllSubNameByOrgCode(String orgCode);

    /**
     * @descrption 通过组织id查找所有主题信息(pad端主题信息展示)
     * @author haihu
     * @create 2015年8月4日上午10:53:46
     * @version 1.0
     * @param orgCode 组织编号
     * @return List<Subject>
     */
    List<Subject> findSubjectsByOrgCode(String orgCode);

    /**
     * @descrption 查找所有主题名称下与会人员数量
     * @author haihu
     * @create 2015年7月3日下午5:05:57
     * @version 1.0
     * @param subject 主题实例
     * @return int
     */
    int findCountBySubjectName(Subject subject);

    /**
     * @descrption 通过编号查找主题名称
     * @author haihu
     * @create 2015年8月3日下午5:05:57
     * @version 1.0
     * @param id 主题编号
     * @return String
     */
    String findNameById(Long id);
}
