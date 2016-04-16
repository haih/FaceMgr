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
public class SharingFace implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4930917054514439839L;
    
    /**
     * 主键
     */
    private Long id;

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
     * 图片路径
     */
    private List<String> imgPath;

    /**
     * 创建时间
     */
    private String createTime;

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
