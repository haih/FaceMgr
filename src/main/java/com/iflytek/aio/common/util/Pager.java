/**	
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 */

package com.iflytek.aio.common.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @desc: 分页代码的封装
 * @author: cheney
 * @createTime: 2014-11-4
 * @version: 1.0
 */
public class Pager<T> {
	public static int DEFAULT_PAGE_SIZE = 10;

	protected int curPage = 1;
	protected int pageSize = DEFAULT_PAGE_SIZE;
	protected List<T> data = Collections.emptyList();
	protected long totalRows = -1L;
	protected boolean autoCount = true;
	protected T entity;
	protected Map<String, Object> params;

	protected String formName;
	protected String sortName;
	protected String sortOrder;
	private long start;
	private boolean success = true;

	public Pager(int pageSize) {
		setPageSize(pageSize);
	}

	public Pager(int pageSize, boolean autoCount) {
		setPageSize(pageSize);
		setAutoCount(autoCount);
	}

	public Pager() {
		this(DEFAULT_PAGE_SIZE);
	}

	public Pager(long start, long totalSize, int pageSize, List<T> data) {
		this.pageSize = pageSize;
		this.start = start;
		this.totalRows = totalSize;
		this.data = data;
	}

	public long getTotalPages() {
		Assert.isTrue(this.pageSize > 0);
		if (this.totalRows % this.pageSize == 0L) {
			return this.totalRows / this.pageSize;
		}
		return this.totalRows / this.pageSize + 1L;
	}

	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(this.sortName))
				&& (StringUtils.isNotBlank(this.sortOrder));
	}

	public int getStartRow() {
		return (this.curPage - 1) * this.pageSize + 1;
	}

	public int getEndRow() {
		return this.curPage * this.pageSize;
	}

	public static int getDEFAULT_PAGE_SIZE() {
		return DEFAULT_PAGE_SIZE;
	}

	public static void setDEFAULT_PAGE_SIZE(int dEFAULTPAGESIZE) {
		DEFAULT_PAGE_SIZE = dEFAULTPAGESIZE;
	}

	public int getCurPage() {
		return this.curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getData() {
		return this.data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * @return the totalRows
	 */
	public long getTotalRows() {
		return totalRows;
	}

	/**
	 * @param totalRows
	 *            the totalRows to set
	 */
	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	/**
	 * @return the sortName
	 */
	public String getSortName() {
		return sortName;
	}

	/**
	 * @param sortName
	 *            the sortName to set
	 */
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	/**
	 * @return the sortOrder
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder
	 *            the sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

    public boolean isAutoCount() {
		return this.autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	public long getStart() {
		return this.start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public String getFormName() {
		return this.formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
//	@Override
//	public String toString() {
//		return "Pager [curPage=" + curPage + ", pageSize=" + pageSize
//				+ ", success=" + success + ", totalRows=" + totalRows
//				+ ", getEndRow()=" + getEndRow() + ", getStartRow()="
//				+ getStartRow() + "]";
//	}
    
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}