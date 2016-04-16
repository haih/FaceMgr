/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：FaceMapper.java                     
 * 
 * Description： 人脸信息与图片路径的对应表
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年6月30日下午5:00:38         Create
 */
package com.iflytek.aio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iflytek.aio.entity.SharingFaceImg;

/**
 * @author haihu
 *
 * @create 2015年6月30日 下午2:00:38
 *
 * @version 1.0
 * 
 * @description 封装人脸信息对应图片的持久层操作
 * 
 */
public interface SharingFaceImgMapper {

    /**
     * @author: haihu
     * @createTime: 2015年7月1日 上午11:14:01
     * @description: 通过faceId获取人脸对应的图片路径信息
     * @param sharingFaceId
     *            共享库人员ID
     * @return List<String>
     */
    List<String> findImgsBySharingFaceId(
            @Param("sharingFaceId") Long sharingFaceId);

    /**
     * @author: haihu
     * @createTime: 2015年7月1日 上午12:14:01
     * @description: 插入人脸对应的图片路径信息
     * @param sharingFaceImg
     *            共享库人员图片实例
     * @return
     */
    void save(SharingFaceImg sharingFaceImg);

    /**
     * @author: haihu
     * @createTime: 2015年7月1日 上午12:14:01
     * @description: 插入人脸对应的图片路径信息
     * @param sharingFaceImgList
     *            共享库人员图片实例列表
     * @return
     */
    void saveBatch(List<SharingFaceImg> sharingFaceImgList);

    /**
     * @author: haihu
     * @createTime: 2015年7月1日 上午12:14:01
     * @description: 通过faceId删除人脸对应的图片路径信息
     * @param faceId 共享库人员ID
     * @return
     */
    void deleteBySharingFaceId(Long faceId);

    /**
     * @author: haihu
     * @createTime: 2015年7月1日 上午12:14:01
     * @description: 更新人脸对应的图片路径信息
     * @param sharingFaceImgList
     *            共享库人员图片实例列表
     * @return
     */
    void update(SharingFaceImg sharingFaceImgList);
}
