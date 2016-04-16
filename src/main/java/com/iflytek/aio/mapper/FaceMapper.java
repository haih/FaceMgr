/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：FaceMapper.java						
 * 
 * Description：	人脸信息表
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年6月30日下午2:00:38         Create
 */
package com.iflytek.aio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iflytek.aio.common.util.Pager;
import com.iflytek.aio.entity.Face;
import com.iflytek.aio.entity.Subject;

/**
 * @author haihu
 *
 * @create 2015年6月30日 下午2:00:38
 *
 * @version 1.0
 * 
 * @description 封装人脸库模块的持久层操作
 * 
 */
public interface FaceMapper {
    /**
     * @author: haihu
     * @createTime: 2015年6月30日 下午5:09:42
     * @description: 增加与会人员信息
     * @param faceInfo
     *            参数实例
     */
    void save(Face faceInfo);

    /**
     * @author: haihu
     * @createTime: 2015年6月30日 下午5:09:42
     * @description: 批量增加人脸库信息
     * @param faceInfoList
     *            参数列表
     */
    void insertBatchFaceInfo(
            @Param(value = "faceInfoList") List<Face> faceInfoList);

    /**
     * @descrption 通过编号删除人脸信息
     * @author haihu
     * @create 2015年6月30日下午5:05:57
     * @version 1.0
     * @param id
     *            编号
     */
    void deleteById(Long id);

    /**
     * @author: haihu
     * @createTime: 2015年6月30日 下午5:09:42
     * @description: 更新与会人员信息
     * @param faceInfo
     *            参数实例
     */
    void update(Face faceInfo);

    /**
     * @descrption 复制与会人员信息
     * @author admin
     * @create 2015年8月17日上午11:20:25
     * @version 1.0
     * @param oldSubjectId
     *            旧主题编号
     * @param newSubjectId
     *            新主题编号
     */
    void copy(Long oldSubjectId, Long newSubjectId);

    /**
     * @author: haihu
     * @createTime: 2015年7月1日 上午12:14:01
     * @description: 通过faceId逻辑删除(即将删除标记修改为已删除)
     * @param id
     *            编号
     * @param updateDate
     *            更新时间
     * @return
     */
    void updateDelStatus(Long id, String updateDate);

    /**
     * @author: haihu
     * @createTime: 2015年7月1日 上午12:14:01
     * @description: 通过faceId更新共享库关联的id号
     * @param id
     *            编号
     * @param sharingId
     *            公共库人员编号
     * @return
     */
    void updateSharingId(Long id, Long sharingId);

    /**
     * @author: haihu
     * @createTime: 2015年7月15日下午5:09:57
     * @description:通过主题ID查询所有人脸信息集合
     * @param subjectId
     *            主题编号
     * @return List<Face>
     */
    List<Face> findAllBySubId(Long subjectId);

    /**
     * @author: haihu
     * @createTime: 2015年8月15日下午5:09:57
     * @description:通过主题ID查询所有更新过的人脸信息集合
     * @param subjectId
     *            主题ID
     * @param dateLine
     *            更新时间
     * @return List<Face>
     */
    List<Face> findUpdateInfoById(Long subjectId, String dateLine);

    /**
     * @descrption 统计指定主题的所有人员信息数量
     * @author haihu
     * @create 2015年6月30日下午5:47:59
     * @version 1.0
     * @param subjectId
     *            主
     * @return 人脸信息总数
     */
    Long findPagerCount(Long subjectId);

    /**
     * @descrption 检测指定的姓名+单位的组合是否已存在
     * @author haihu
     * @create 2015年8月4日下午3:02:31
     * @version 1.0
     * @param name
     *            名称
     * @param department
     *            部门
     * @param subjectId
     *            主题ID
     * @return int
     */
    int isUserExist(String name, String department, Long subjectId);

    /**
     * @descrption 通过编号查找主题中的与会人员信息
     * @author haihu
     * @create 2015年7月3日下午5:05:57
     * @version 1.0
     * @param pager 分页参数
     * @param faceName 人员姓名
     * @return List<Face>
     */
    List<Face> findPagerList(Pager<Subject> pager,
            @Param("faceName") String faceName);

    /**
     * @descrption 通过id查找对应的与会人员信息
     * @author haihu
     * @create 2015年6月30日下午5:10:16
     * @version 1.0
     * @param ids 编号列表
     * @return List<Face>
     */
    List<Face> findByIds(Long[] ids);

    /**
     * @descrption 通过姓名查询主题库对应信息数量
     * @author haihu
     * @create 2015年6月30日下午5:47:59
     * @version 1.0
     * @param pager 分页参数
     * @return 人脸信息总数
     */
    Long findCountByName(Pager<Face> pager);

    /**
     * @descrption 通过共享库ID查找对应的人脸信息
     * @author haihu
     * @create 2015年7月30日下午5:10:16
     * @version 1.0
     * @param sharingId 共享库人员ID
     * @return Long
     */
    Long[] findBySharingId(Long sharingId);

    /**
     * @descrption 通过人脸ID查找对应的共享库信息
     * @author haihu
     * @create 2015年7月30日下午5:10:16
     * @version 1.0
     * @param id 主题库人员ID 
     * @return Long
     */
    Long findSharingIdById(Long id);

    /**
     * @descrption 通过主题ID查找对应的人脸信息ID
     * @author haihu
     * @create 2015年7月30日下午5:10:16
     * @version 1.0
     * @param subjectId 共享库人员ID
     * @return Long
     */
    Long[] findBySubjectId(Long subjectId);
}
