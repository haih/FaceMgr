/**	
 * Copyright 2014 IFlyTek. All rights reserved.<br>
 */
package com.iflytek.aio.common.msg;

/**
 * <pre>
 * *****************************************************************************
 * Copyrights @ 2015，Iflytek Information Co., Ltd All rights reserved.
 * Filename：MsgKey
 * Description：获取配置文件中提示信息的key
 * Entity Author: ycli5
 * Finished：2015年5月4日 下午4:26:06
 * ****************************************************************************
 * </pre>
 */
public final class MsgKey {

	/**
	 * 成功前缀
	 */
	public static final String SUCC_PRFIX = "success.";

	/**
	 * 失败前缀
	 */
	public static final String ERROR_PRFIX = "error.";

	/**
	 * 不合法参数
	 */
	public static final String ERROR_PARAM_ILLEGAL = "error.param.illegal";
	
    /**
     * 无操作权限
     */
    public static final String ERROR_NO_PERMISSION = "error.no.permission";
    
	/**
	 * 操作成功
	 */
	public static final String SUCCESS_OPERATION = "success.operation";

	/**
	 * 操作失败
	 */
	public static final String ERROR_OPERATION = "error.operation";

	/**
	 * 搜索结果为空
	 */
	public static final String ERROR_SEARCH_RESULT_EMPTY = "error.empty.result";

	/**
	 * 应用名称已经存在
	 */
	public static final String ERROR_APPINFO_NAME_REPETITION = "error.appinfo.name.repetition";

	/**
	 * 数据已经删除
	 */
	public static final String ERROR_RESULT_IS_DELETE = "error.result.is.delete";

	/**
	 * 下载数据过多
	 */
	public static final String ERROR_USERMODEL_DOWNLOAD_SIZE = "error.usermodel.download.size";
	/**
	 * 获取excel中的数据异常
	 */
	public static final String ERROR_GET_EXCEL_DATA = "error.get.exceldata";

	/**
     * 上传文件类型非法
     */
    public static final String ERROR_UPLOAD_TYPE_ILLEGAL = "error.uploadtype.illegal";
	/**
     * 获取图片数据异常
     */
    public static final String IMG_NOT_AVAILABLE = "error.img.not.available";
    /**
      * 获取主题是否存在
      */
    public static final String SUBJECT_IS_EXIST = "success.subject.isexist";
    
    /**
     * 获取用户是否存在
     */
   public static final String USER_IS_EXIST = "success.user.isexist";
}
