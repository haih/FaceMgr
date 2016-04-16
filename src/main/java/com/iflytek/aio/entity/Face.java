/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：Face.java						
 * 
 * Description：	人脸信息实体
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年6月30日下午2:01:46         Create
 */
package com.iflytek.aio.entity;

import java.io.Serializable;
import java.util.List;

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
public class Face implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4930917054514439839L;
    
    /**
     * 主键
     */
    private Long id;
    
    /**
     * 导入列表中的编号
     */
    private String inputId;

    /**
     * 会议主题编号
     */
    private Long subjectId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 职务
     */
    private String job;

    /**
     * 单位
     */
    private String department;

    /**
     * 手机
     */
    private String mobile;
    
    /**
     * 认证次数
     */
    private int verifyCount;
    
    /**
     * 图片路径
     */
    private List<String> imgPath;

    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 操作标记（0：编辑；1：插入；-1：删除）
     */
    private String actionFlag;
    
    /**
     * 来源标记（0：用户添加；1：从共享库添加）
     */
    private String inputFrom;
    
    /**
     * 公共库编号
     */
    private Long sharingId;   
    
    private int syncFlag;
    
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
     * @return the inputId
     */
    public String getInputId() {
        return inputId;
    }

    /**
     * @param inputId the inputId to set
     */
    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    /**
     * @param subjectId the subjectId to set
     */
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }
    
    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * @return the job
     */
    public String getJob() {
        return job;
    }
    /**
     * @param job the job to set
     */
    public void setJob(String job) {
        this.job = job;
    }
    
    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }
    
    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }
    
    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }
    
    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
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
     * @return the imgPath
     */
    public List<String> getImgPath() {
        if(null == imgPath){
            return null;
        }
        return imgPath;
    }

    /**
     * @param imgPath the imgPath to set
     */
    public void setImgPath(List<String> imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * @return the createTime
     */
    public String getCreateTime() {
        return createTime;
    }
    
    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    /**
     * @return the updateTime
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the actionFlag
     */
    public String getActionFlag() {
        return actionFlag;
    }

    /**
     * @param actionFlag the actionFlag to set
     */
    public void setActionFlag(String actionFlag) {
        this.actionFlag = actionFlag;
    }

    /**
     * @return the inputFrom
     */
    public String getInputFrom() {
        return inputFrom;
    }

    /**
     * @param inputFrom the inputFrom to set
     */
    public void setInputFrom(String inputFrom) {
        this.inputFrom = inputFrom;
    }

    /**
     * @return the sharingId
     */
    public Long getSharingId() {
        return sharingId;
    }

    /**
     * @param sharingId the sharingId to set
     */
    public void setSharingId(Long sharingId) {
        this.sharingId = sharingId;
    }

    /**
     * @return the syncFlag
     */
    public int getSyncFlag() {
        return syncFlag;
    }

    /**
     * @param syncFlag the syncFlag to set
     */
    public void setSyncFlag(int syncFlag) {
        this.syncFlag = syncFlag;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
