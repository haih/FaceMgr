package com.iflytek.aio.common.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * 
 * @desc: RestEasy客户端调用封装
 * @author: cheney
 * @createTime: 2014-12-6
 * @version: 1.0
 */
public class HttpHelper {
	private int timeOut = 5000;
	private HttpClient httClient = null;

	private String message;

	/**
	 * @return the timeOut
	 */
	public int getTimeOut() {
		return timeOut;
	}

	/**
	 * @param timeOut
	 *            the timeOut to set
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 */
	public HttpHelper() {
		httClient = new HttpClient();
	}

	/**
	 * 
	 * @param timeOut
	 */
	public HttpHelper(int timeOut) {
		this();
		this.timeOut = timeOut;
	}

	/**
	 * 初始化HttpMethod元素
	 * 
	 * @param url
	 *            访问地址
	 * @param encoding
	 *            编码
	 * @param params
	 *            参数
	 * @return HttpMethod元素
	 */
	private HttpMethod initHttpMethod(String url, String encoding,
			Map<String, String> params) {
		HttpMethod method = new GetMethod(url);
		// 设置url连接超时
		httClient.getHttpConnectionManager().getParams().setConnectionTimeout(
				timeOut);
		// 设置读取数据超时
		httClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
		// 这两行解决 too many open files问题
		httClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		// 解决Httpclient远程请求所造成Socket没有释放
		method.addRequestHeader("Connection", "close");
		// 添加额外的Header
		if (params != null && params.size() > 0) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				method.addRequestHeader(key, params.get(key));
			}
		}
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				encoding);
		return method;
	}

	/**
	 * GET方式获取内容
	 * 
	 * @param url
	 *            访问地址
	 * @param encoding
	 *            编码
	 * @param params
	 *            参数
	 * @return String 结果内容
	 */
	public String get(String url, String encoding, Map<String, String> params) {
		StringBuffer content = new StringBuffer();
		HttpMethod method = initHttpMethod(url, encoding, params);
		try {
			httClient.executeMethod(method);
			String respString = method.getResponseBodyAsString();
			content.append(respString);
		} catch (ConnectTimeoutException e) {
			message = "Connect Timeout:" + url;
		} catch (SocketTimeoutException e) {
			message = "Socket Timeout:" + url;
		} catch (IOException e) {
			message = e.toString();
		} finally {
			method.releaseConnection();
		}
		return content.toString();
	}

	/**
	 * GET方式获取内容
	 * 
	 * @param url
	 *            访问地址
	 * @param encoding
	 *            编码
	 * @return String 结果内容
	 */
	public String get(String url, String encoding) {
		return get(url, encoding, null);
	}

	/**
	 * 与上面post区别在于返回值有StringBuffer改为String了
	 * 
	 * @param url
	 * @param sendData
	 *            ： post的内容
	 * @param content
	 *            ：返回的内容
	 * @param encoding
	 *            ：编码方式 ， utf-8
	 * @return
	 */
	public String postNew(String url, String contentType, String sendData,
			String encoding) {
		StringBuffer content = new StringBuffer();
		PostMethod method = new PostMethod(url);
		// 设置url连接超时
		httClient.getHttpConnectionManager().getParams().setConnectionTimeout(
				timeOut);
		// 设置读取数据超时
		httClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
		// 这两行解决 too many open files问题
		httClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		// 解决Httpclient远程请求所造成Socket没有释放
		method.addRequestHeader("Connection", "close");
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				encoding);
		InputStream in = null;
		BufferedReader buffer = null;
		try {
			RequestEntity entity = new StringRequestEntity(sendData,
					contentType, encoding);
			method.setRequestEntity(entity);
			httClient.executeMethod(method);
			int code = method.getStatusCode();
			if (code == HttpStatus.SC_OK) {
				in = method.getResponseBodyAsStream();
				buffer = new BufferedReader(new InputStreamReader(in, encoding));
				for (String tempstr = ""; (tempstr = buffer.readLine()) != null;) {
					content = content.append(tempstr);
				}
			}
		} catch (ConnectTimeoutException e) {
			message = "Connect Timeout:" + url;
		} catch (SocketTimeoutException e) {
			message = "Socket Timeout:" + url;
		} catch (IOException e) {
			message = e.toString();
		} finally {
			try {
				buffer.close();
				in.close();
			} catch (IOException e) {
				message = "IOException:" + url;
			}
			method.releaseConnection();
		}
		return content.toString();
	}
}
