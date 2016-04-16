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

import com.iflytek.aio.entity.FaceImg;

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
public interface FaceImgMapper {

    /**
     * @author: haihu
     * @createTime: 2015年7月1日 上午11:14:01
     * @description: 通过faceId获取人脸对应的图片路径信息
     * @param faceId 人脸编号
     * @return List<String>
     */
    List<String> findImgsByFaceId(@Param("faceId") Long faceId);

    /**
     * @author: haihu
     * @createTime: 2015年7月1日 上午12:14:01
     * @description: 插入人脸对应的图片路径信息
     * @param faceImg 参数实例
     * @return
     */
    void save(FaceImg faceImg);

    /**
     * @author: haihu
     * @createTime: 2015年7月1日 上午12:14:01
     * @description: 批量插入人脸对应的图片路径信息
     * @param faceImg 参数实例
     * @return
     */
    void saveBatch(List<FaceImg> faceImg);

    /**
     * @author: haihu
     * @createTime: 2015年7月1日 上午12:14:01
     * @description: 更新人脸对应的图片路径信息
     * @param faceImg 参数实例
     * @return
     */
    void update(FaceImg faceImg);

    /**
     * @author: haihu
     * @createTime: 2015年7月1日 上午12:14:01
     * @description: 通过faceId删除人脸对应的图片路径信息
     * @param faceId 人脸编号
     * @return
     */
    void deleteByFaceId(Long faceId);

    /**
     * @author: haihu
     * @createTime: 2015年7月1日 上午12:14:01
     * @description: 通过ImgPath删除人脸对应的图片路径信息
     * @param imgPath 图片路径
     * @return
     */
    void deleteByImgPath(String imgPath);
}
