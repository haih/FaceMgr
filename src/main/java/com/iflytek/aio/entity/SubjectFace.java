/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：SubjectFace.java						
 * 
 * Description：	
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年7月3日下午3:22:14         Create
 *                            _ooOoo_  
 *                           o8888888o  
 *                           88" . "88  
 *                           (| -_- |)  
 *                            O\ = /O  
 *                        ____/`---'\____  
 *                      .   ' \\| | * `.  
 *                       / \\||| : ||| * \  
 *                     / _||||| -:- |||||- \  
 *                       | | \\\ -  /// | |  
 *                     | \_| ''\---/'' | |  
 *                      \ .-\__ `-` ___/-. /  
 *                   ___`. .' /--.--\ `. . __  
 *                ."" '< `.___\_<|>_/___.' >'"".  
 *               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
 *                 \ \ `-. \_ __\ /__ _/ .-` / /  
 *         ======`-.____`-.___\_____/___.-`____.-'======   
 *         
 */
package com.iflytek.aio.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * @author haihu 
 *
 * @create 2015年7月3日 下午3:22:14
 *
 * @version 1.0
 * 
 * @description
 * 
 */
public class SubjectFace implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4930917054514439839L;
    /**
     * 主键
     */
    private Long id;
    
    /**
     * 会议主题编号
     */
    private Long subjectId;
    
    /**
     * 人脸编号
     */
    private Long faceId;
    
    /**
     * 认证次数
     */
    private int verifyCount;
    
    /**
     * 删除标记
     */
    private String delFlag;

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
     * @return the subjectId
     */
    public Long getSubjectId() {
        return subjectId;
    }

    /**
     * @param subjectId the subjectId to set
     */
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * @return the faceId
     */
    public Long getFaceId() {
        return faceId;
    }

    /**
     * @param faceId the faceId to set
     */
    public void setFaceId(Long faceId) {
        this.faceId = faceId;
    }

    /**
     * @return the verifyCount
     */
    public int getVerifyCount() {
        return verifyCount;
    }

    /**
     * @param verifyCount the verifyCount to set
     */
    public void setVerifyCount(int verifyCount) {
        this.verifyCount = verifyCount;
    }

    /**
     * @return the delFlag
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag the delFlag to set
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
