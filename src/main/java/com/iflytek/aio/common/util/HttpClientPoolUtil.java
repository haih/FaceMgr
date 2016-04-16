/**	
 * <br>
 * Copyright 2015 IFlyTek. All rights reserved.<br>
 * <br>			 
 * Package: com.iflytek.pq.idauth.common.util <br>
 * FileName: HttpClientPoolUtil.java <br>
 * <br>
 * @version
 * @author lschen
 * @created 2015年5月31日
 * @last Modified 
 * @history
 */
package com.iflytek.aio.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.Map;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 * 
 * @author lschen
 * @createTime 2015年5月31日 上午10:38:02
 * @lastModified
 * @history
 */
public class HttpClientPoolUtil {
	static Logger log = LoggerFactory.getLogger(HttpClientPoolUtil.class);
	
	private static HttpConnectionManager connectionManager;
	
	private static HttpClient _httClient;

	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 400;
	/**
	 * 获取连接的最大等待时间
	 */
	public final static int WAIT_TIMEOUT = 60000;
	/**
	 * 每个路由最大连接数
	 */
	public final static int MAX_ROUTE_CONNECTIONS = 200;
	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 10000;
	/**
	 * 读取超时时间
	 */
	public final static int SO_TIMEOUT = 60000;

	static {
		connectionManager = new MultiThreadedHttpConnectionManager();
		
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setConnectionTimeout(CONNECT_TIMEOUT);
		params.setSoTimeout(SO_TIMEOUT);
		params.setDefaultMaxConnectionsPerHost(MAX_ROUTE_CONNECTIONS);//very important!! 
		params.setMaxTotalConnections(MAX_TOTAL_CONNECTIONS);//very important!! 
				
        connectionManager.setParams(params);
        
        _httClient = new HttpClient(connectionManager);
        
        // 这两行解决 too many open files问题
 		_httClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
 		
 		// 设置连接超时不再重试，默认重试3次
 		HttpMethodRetryHandler retryHandler = new DefaultHttpMethodRetryHandler(0, false);
 		_httClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryHandler);
	}

	/**
	 * post发送String参数
	 * @param url
	 * @param contentType
	 * @param header
	 * @param sendData
	 * @param encoding
	 * @return
	 */
	public static String post(String url, String contentType, Map<String, String> header,
			String sendData, String encoding) {
//		StringBuffer content = new StringBuffer();
		String content = "";
		PostMethod method = new PostMethod(url);
		
		// 解决Httpclient远程请求所造成Socket没有释放
		method.addRequestHeader("Connection", "close");
				
		if(header != null){
			for (String key : header.keySet()) {
				method.addRequestHeader(key, header.get(key));
			}
		}
		
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				encoding);

		try {
			RequestEntity entity = new StringRequestEntity(sendData,
					contentType, encoding);
			method.setRequestEntity(entity);
			_httClient.executeMethod(method);
			int code = method.getStatusCode();
			if (code == HttpStatus.SC_OK) {
				content = method.getResponseBodyAsString();
			}

		} catch (IOException e) {
			log.info("IO异常,请求地址为：" + url + "\n异常信息：" + e.getMessage());
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return content;
	}
	
	/**
	 * post发送流
	 * @param url
	 * @param header
	 * @param sendData
	 * @param encoding
	 * @return
	 */
	public static String post(String url, Map<String, String> header,
			byte[] sendData, String encoding) {
		StringBuffer content = new StringBuffer();
		PostMethod method = new PostMethod(url);

		// 解决Httpclient远程请求所造成Socket没有释放
		method.addRequestHeader("Connection", "close");
		if(header != null){
			for (String key : header.keySet()) {
				method.addRequestHeader(key, header.get(key));
			}
		}

		try {
			InputStream requestIn = new ByteArrayInputStream(sendData);
			method.setRequestBody(requestIn);
			
			_httClient.executeMethod(method);
			int code = method.getStatusCode();
			if (code == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(in, encoding));
				for (String tempstr = ""; (tempstr = buffer.readLine()) != null;)
					content = content.append(tempstr);
				buffer.close();
				in.close();
			}
		} catch (ConnectTimeoutException e) {
			log.info("ConnectTimeout连接超时,请求地址为：" + url);
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			log.info("SocketTimeout连接超时,请求地址为：" + url);
			e.printStackTrace();
		} catch (IOException e) {
			log.info("IO异常,请求地址为：" + url);
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return content.toString();
	}
}
