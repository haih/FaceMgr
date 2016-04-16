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

import com.iflytek.aio.common.util.Pager;
import com.iflytek.aio.entity.SharingFace;

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
public interface SharingFaceMapper {
    /**
     * @author: haihu
     * @createTime: 2015年6月30日 下午5:09:42
     * @description: 增加共享库信息
     * @param sharingFace
     *            共享库人员实例
     */
    void save(SharingFace sharingFace);

    /**
     * @author: haihu
     * @createTime: 2015年6月30日 下午5:09:42
     * @description: 更新共享库信息
     * @param sharingFace
     *            共享库人员实例
     */
    void update(SharingFace sharingFace);

    /**
     * @descrption 通过编号删除人脸信息
     * @author haihu
     * @create 2015年6月30日下午5:05:57
     * @version 1.0
     * @param id
     *            编号参数
     */
    void deleteById(Long id);

    /**
     * @author: haihu
     * @createTime: 2015年7月1日 上午12:14:01
     * @description: 通过Id逻辑删除(即将删除标记修改为已删除)
     * @param id
     *            编号参数
     * @return
     */
    void updateDelStatus(Long id);

    /**
     * @author: haihu
     * @createTime: 2015年6月30日下午5:09:57
     * @description:分页查询共享库信息集合
     * @param pager
     *            分页条件
     * @return List<SharingFace>
     */
    List<SharingFace> findPagerList(Pager<SharingFace> pager);

    /**
     * @descrption 统计所有共享库信息的数量
     * @author haihu
     * @create 2015年6月30日下午5:47:59
     * @version 1.0
     * @param pager
     *            分页参数
     * @return 人脸信息总数
     */
    Long findPagerCount(Pager<SharingFace> pager);

    /**
     * @descrption 通过姓名查找对应的公共库人脸信息
     * @author haihu
     * @create 2015年6月30日下午5:10:16
     * @version 1.0
     * @param pager 分页参数
     * @return List<SharingFace>
     */
    List<SharingFace> findByName(Pager<SharingFace> pager);

    /**
     * @descrption 通过id查找对应的公共库人脸信息
     * @author haihu
     * @create 2015年6月30日下午5:10:16
     * @version 1.0
     * @param ids 编号列表
     * @return List<SharingFace>
     */
    List<SharingFace> findByIds(Long[] ids);

    /**
     * @descrption 通过姓名查询共享库对应信息数量
     * @author haihu
     * @create 2015年6月30日下午5:47:59
     * @version 1.0
     * @param pager 分页参数
     * @return 人脸信息总数
     */
    Long findCountByName(Pager<SharingFace> pager);

    /**
     * @descrption 通过姓名查找对应的人脸信息
     * @author haihu
     * @create 2015年6月30日下午5:10:16
     * @version 1.0
     * @param pager 分页参数
     * @return List<SharingFace>
     */
    List<SharingFace> findByJob(Pager<SharingFace> pager);

    /**
     * @descrption 通过姓名查找对应的人脸信息
     * @author haihu
     * @create 2015年6月30日下午5:10:16
     * @version 1.0
     * @param pager 分页参数
     * @return List<SharingFace>
     */
    List<SharingFace> findByDepartment(Pager<SharingFace> pager);

}
