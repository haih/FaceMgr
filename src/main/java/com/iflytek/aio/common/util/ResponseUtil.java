package com.iflytek.aio.common.util;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * *****************************************************************************
 * Copyrights @ 2014，Iflytek Information Co., Ltd All rights reserved.
 * Filename：ResponseUtil Description：处理响应信息 Entity Author: ycli5
 * Finished：2014年12月31日 下午3:55:13
 * ****************************************************************************
 */
public class ResponseUtil {

	/**
	 * 日志记录器
	 */
	private static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

	/**
	 * @author: ycli5
	 * @createTime: 2015年4月14日 上午10:07:52
	 * @description: 响应信息
	 * @param msg
	 *            信息
	 * @param response
	 *            响应 void
	 */
	public static void handleErrMsg(String msg, HttpServletResponse response) {
		logger.info("handleErrMsg method : 响应处理错误信息");
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			out.write(msg.getBytes());
			out.flush();
		} catch (Exception e) {
			logger.error("handleErrMsg method : 响应处理错误信息 异常", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("handleErrMsg method : 响应处理错误信息 异常", e);
				}
			}
		}

	}

	/**
	 * @author: ycli5
	 * @createTime: 2015年4月14日 上午10:09:09
	 * @description: 创建下载响应头
	 * @param response
	 *            响应
	 * @param fileName
	 *            文件名 void
	 */
	public static void downloadResponseHeader(
			HttpServletResponse response, String fileName) {
		
		response.setContentType("application/x-download");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ fileName);
	}

}
