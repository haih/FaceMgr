/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：Face.java                       
 * 
 * Description： 人脸信息与对应图片路径实体
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年6月30日下午2:01:46         Create
 */
package com.iflytek.aio.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * @author haihu 
 *
 * @create 2015年6月30日 下午2:01:46
 *
 * @version 1.0
 * 
 * @description
 * 
 */
public class FaceImg implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4930917054514439839L;
	
    /**
     * 主键
     */
    private Long id;

    /**
     * 人脸库ID
     */
    private Long faceId;

    /**
     * 图片存放路径
     */
    private String imgPath;

	/**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the faceInfoId
     */
    public Long getFaceId() {
        return faceId;
    }

    /**
     * @param faceInfoId the faceInfoId to set
     */
    public void setFaceId(Long faceId) {
        this.faceId = faceId;
    }

	/**
     * @return the imgPath
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * @param imgPath the imgPath to set
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}