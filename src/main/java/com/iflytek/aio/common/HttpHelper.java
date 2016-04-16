package com.iflytek.aio.common;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * httpClient相关类
 */

public class HttpHelper {
	static Logger log = LoggerFactory.getLogger(HttpHelper.class);
	
	private static int timeOut = 5000;

	/**
	 * 
	 * @param url
	 * @param webContent
	 * @param encoding
	 *            编码如：UTF-8
	 * @return
	 */
	public static String get(String url, String encoding,
			HashMap<String, String> headers) {
		StringBuffer content = new StringBuffer();
		HttpMethod method = new GetMethod(url);
		// 设置url连接超时
		HttpClient _httClient = new HttpClient();
		_httClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(timeOut);
		// 设置读取数据超时
		_httClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);

		// 这两行解决 too many open files问题
		_httClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		// 解决Httpclient远程请求所造成Socket没有释放
		method.addRequestHeader("Connection", "close");

		// 添加额外的Header
		if (headers != null && headers.size() > 0) {
			Set<String> keys = headers.keySet();
			for (String key : keys) {
				method.addRequestHeader(key, headers.get(key));
			}
		}

		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				encoding);

		try {
			_httClient.executeMethod(method);
			int code = method.getStatusCode();
			if (code != HttpStatus.SC_OK) {
				return null;
			}
			String respString = method.getResponseBodyAsString();
			content.append(respString);

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

	/**
	 * 
	 * @param url
	 * @param webContent
	 * @param encoding
	 *            编码如：UTF-8
	 * @return
	 */
	public static String get(String url, String encoding) {
		StringBuffer content = new StringBuffer();
		HttpMethod method = new GetMethod(url);
		// 设置url连接超时
		HttpClient _httClient = new HttpClient();
		_httClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(timeOut);
		// 设置读取数据超时
		_httClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);

		// 这两行解决 too many open files问题
		_httClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		// 解决Httpclient远程请求所造成Socket没有释放
		method.addRequestHeader("Connection", "close");

		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				encoding);
		System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(10000));// （单位：毫秒）  
		System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(10000)); // （单位：毫秒）
		try {
			_httClient.executeMethod(method);
			int code = method.getStatusCode();
			if (code != HttpStatus.SC_OK) {
				return null;
			}
			String respString = method.getResponseBodyAsString();
			content.append(respString);

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
	public static String post(String url, String contentType, Map<String, String> header,
			String sendData, String encoding) {
		StringBuffer content = new StringBuffer();
		PostMethod method = new PostMethod(url);
		// 设置url连接超时
		HttpClient _httClient = new HttpClient();
		_httClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(timeOut);
		// 设置读取数据超时
		_httClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);

		// 这两行解决 too many open files问题
		_httClient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		// 解决Httpclient远程请求所造成Socket没有释放
		method.addRequestHeader("Connection", "close");
		for (String key : header.keySet()) {
			method.addRequestHeader(key, header.get(key));
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
				InputStream in = method.getResponseBodyAsStream();
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(in, encoding));
				for (String tempstr = ""; (tempstr = buffer.readLine()) != null;)
					content = content.append(tempstr);
				buffer.close();
				in.close();
			}

			// method.setRequestBody(sendData);
			// _httClient.executeMethod(method);
			// String respString = method.getResponseBodyAsString();
			// content.append(respString);

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
	
	/**
	 * post发送byte流
	 * @param url
	 * @param header
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, Map<String, String> header,
			byte[] data){
		URL u = null;
		HttpURLConnection con = null;
		InputStream inputStream = null;
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			for (String key : header.keySet()) {
				con.setRequestProperty(key, header.get(key));
			}
			OutputStream outStream = con.getOutputStream();
			outStream.write(data);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		// 读取返回内容
		try {
			// 读取返回内容
			inputStream = con.getInputStream();
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = inputStream.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
			return out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){
		String url = "http://192.168.27.29:8080/permission/idauth/findAppList?sysid=182";
		String result = get(url, "UTF-8", null);
		System.out.println(result);
	}
}
