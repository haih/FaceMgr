/* 
 * 
 * Copyright (C) 1999-2012 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：ExcelData.java						
 * 
 * Description：	
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0       haihu       2015年7月13日下午6:06:24         Create
 */
package com.iflytek.aio.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * @author haihu 
 *
 * @create 2015年7月13日 下午6:06:24
 *
 * @version 1.0
 * 
 * @description
 * 
 */
public class ExcelData implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4930917054514439839L;

    private boolean availableFlag = true;
    
    private String col0;
    
    private String col1;
    
    private String col2;
    
    private String col3;
    
    private String col4;
    
    private String col5;
    /**
     * @return the availableFlag
     */
    public boolean isAvailableFlag() {
        return availableFlag;
    }

    /**
     * @param availableFlag the availableFlag to set
     */
    public void setAvailableFlag(boolean availableFlag) {
        this.availableFlag = availableFlag;
    }

    /**
     * @return the col0
     */
    public String getCol0() {
        return col0;
    }

    /**
     * @param col0 the col0 to set
     */
    public void setCol0(String col0) {
        this.col0 = col0;
    }

    /**
     * @return the col1
     */
    public String getCol1() {
        return col1;
    }

    /**
     * @param col1 the col1 to set
     */
    public void setCol1(String col1) {
        this.col1 = col1;
    }


    /**
     * @return the col2
     */
    public String getCol2() {
        return col2;
    }


    /**
     * @param col2 the col2 to set
     */
    public void setCol2(String col2) {
        this.col2 = col2;
    }


    /**
     * @return the col3
     */
    public String getCol3() {
        return col3;
    }


    /**
     * @param col3 the col3 to set
     */
    public void setCol3(String col3) {
        this.col3 = col3;
    }


    /**
     * @return the col4
     */
    public String getCol4() {
        return col4;
    }


    /**
     * @param col4 the col4 to set
     */
    public void setCol4(String col4) {
        this.col4 = col4;
    }


    /**
     * @return the col5
     */
    public String getCol5() {
        return col5;
    }


    /**
     * @param col5 the col5 to set
     */
    public void setCol5(String col5) {
        this.col5 = col5;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
