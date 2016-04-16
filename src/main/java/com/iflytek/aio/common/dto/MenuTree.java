package com.iflytek.aio.common.dto;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @desc: 树节点的DTO对象
 * @author: yhsu
 * @createTime: 2014-12-6 下午03:45:50
 * @version: 2.1
 */
public class MenuTree implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5477872862159991839L;

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 是否为父节点
	 */
	private boolean isParent;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 父节点
	 */
	private String parentId;

	/**
	 * 链接
	 */
	private String turl;

	/**
	 * 是否展示
	 */
	private int isDisplay;

	/**
	 * 英文名称
	 */
	private String englishName;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	@JSONField(name = "resourceId")
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the isParent
	 */
	public boolean isParent() {
		return isParent;
	}

	/**
	 * @param isParent
	 *            the isParent to set
	 */
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	@JSONField(name = "resourceName")
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parentId
	 */
	@JSONField(name = "pId")
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the turl
	 */
	public String getTurl() {
		return turl;
	}

	/**
	 * @param turl
	 *            the turl to set
	 */
	@JSONField(name = "link")
	public void setTurl(String turl) {
		this.turl = turl;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MenuTree [id=" + id + ", isDisplay=" + isDisplay
				+ ", isParent=" + isParent + ", name=" + name + ", parentId="
				+ parentId + ", turl=" + turl + "]";
	}

}
