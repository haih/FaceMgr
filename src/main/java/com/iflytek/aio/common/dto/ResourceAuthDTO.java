package com.iflytek.aio.common.dto;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * @desc: 菜单权限DTO封装类，用于WebService接口数据传输
 * @author: yjleng
 * @createTime: 2014-11-21 上午11:05:43
 * @history:
 * @version: v1.0
 */
public class ResourceAuthDTO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5151248322123180434L;
	/**
	 * 资源id
	 */
	private Long resourceId;
	/**
	 * 资源名称
	 */
	private String resourceName;
	/**
	 * 资源英文名称
	 */
	private String englishName;
	/**
	 * 资源链接
	 */
	private String link;
	/**
	 * 父节点id
	 */
	private Long parentId;

	/**
	 * 操作字符串
	 */
	private List<String> optAction;

	/**
	 * 是否展示 0：不展示 1：展示
	 */
	private int isDisplay;

	/**
	 * 层级
	 */
	private int hierarchy;

	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId
	 *            the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName
	 *            the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the englishName
	 */
	public String getEnglishName() {
		return englishName;
	}

	/**
	 * @param englishName
	 *            the englishName to set
	 */
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the optAction
	 */
	public List<String> getOptAction() {
		return optAction;
	}

	/**
	 * @param optAction
	 *            the optAction to set
	 */
	public void setOptAction(List<String> optAction) {
		this.optAction = optAction;
	}

	/**
	 * @return the isDisplay
	 */
	public int getIsDisplay() {
		return isDisplay;
	}

	/**
	 * @param isDisplay
	 *            the isDisplay to set
	 */
	public void setIsDisplay(int isDisplay) {
		this.isDisplay = isDisplay;
	}

	/**
	 * @return the hierarchy
	 */
	public int getHierarchy() {
		return hierarchy;
	}

	/**
	 * @param hierarchy
	 *            the hierarchy to set
	 */
	public void setHierarchy(int hierarchy) {
		this.hierarchy = hierarchy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
