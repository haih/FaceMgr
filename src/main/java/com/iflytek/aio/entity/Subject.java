/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：Face.java                       
 * 
 * Description： 会议信息实体
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年6月30日下午2:01:46         Create
 */

package com.iflytek.aio.entity;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class Subject implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4930917054514439839L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 会议主题名称
     */
    private String subjectName;
    
    /**
     * 与会人数
     */
    private int participateCount;
    
    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 创建人员编号
     */
    private Long createUserId;
    
    /**
     * 用户组织机构
     */
    private String orgCode;
    
    /**
     * 更新时间
     */
    private String updateTime;
    
    /**
     * 与会人员列表
     */
    private List<Face> faceList;
    /**
     * 删除标记
     */
    private String delFlag;
    
    /**
     * 二维码存放路径
     */
    private String qrcodePath;
    
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
     * @return the participateCount
     */
    public int getParticipateCount() {
        return participateCount;
    }
    /**
     * @param participateCount the participateCount to set
     */
    public void setParticipateCount(int participateCount) {
        this.participateCount = participateCount;
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
     * @return the createUserId
     */
    public Long getCreateUserId() {
        return createUserId;
    }
    /**
     * @param createUserId the createUserId to set
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
    
    /**
     * @return the orgCode
     */
    public String getOrgCode() {
        return orgCode;
    }
    /**
     * @param orgCode the orgCode to set
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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
     * @return the subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }
    /**
     * @param subjectName the subjectName to set
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    /**
     * @return the faceList
     */
    public List<Face> getFaceList() {
        return faceList;
    }
    /**
     * @param faceList the faceList to set
     */
    public void setFaceList(List<Face> faceList) {
        this.faceList = faceList;
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
    
    /**
     * @return the qrcodePath
     */
    public String getQrcodePath() {
        return qrcodePath;
    }
    /**
     * @param qrcodePath the qrcodePath to set
     */
    public void setQrcodePath(String qrcodePath) {
        this.qrcodePath = qrcodePath;
    }
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
