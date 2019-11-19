package com.jt.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HttpClientService {
	
	@Autowired
	private CloseableHttpClient httpClient;
	@Autowired
	private RequestConfig requestConfig;
	
	/**
	 *    编辑工具API目的简化代码,实现松耦合
	 *   
	 *    作用:帮助用户发起http请求,获取正确的结果返回给用户
	 *    参数设计:	1.用户url地址   2.Map<参数名,参数值>  3.字符编码
	 * 
	 * get请求方式:
	 * 	1.没有参数时: http://www.baidu.com
	 * 	2.有参数时:     http://www.baidu.com?key1=value1&key2=value2...
	 * @param url
	 * @param params
	 * @param charset
	 * @return
	 */
	public String doGet(String url,Map<String,String> params,String charset) {
		//1.校验用户是否传递字符编码
		if(StringUtils.isEmpty(charset)) {
			
			charset = "UTF-8";
		}
		
		//2.封装URL地址
		//  http://www.baidu.com?key1=value1&key2=value2&...
		if(params != null) {
			url = url + "?";
			//map遍历
			for (Map.Entry<String,String> entry : params.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				url += key+"="+value+"&";
			}
			//截去多余的&
			url = url.substring(0, url.length()-1);
		}
		
		//3.定义httpGet请求对象
		HttpGet httpGet = new HttpGet(url);
		//设定请求的超时时间
		httpGet.setConfig(requestConfig);
		
		//定义返回值数据
		String result = null;
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == 200) {
				//表示请求正确
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity,charset);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	//重载方法
	public String doGet(String url) {
		
		return doGet(url, null, null);
	}
	
	public String doGet(String url,Map<String,String> params) {
		
		return doGet(url, params, null);
	}
	
	
	
	//实现httpClient POST提交
	public String doPost(String url,Map<String,String> params,String charset){
		String result = null;

		//1.定义请求类型
		HttpPost post = new HttpPost(url);
		post.setConfig(requestConfig);  	//定义超时时间

		//2.判断字符集是否为null
		if(StringUtils.isEmpty(charset)){

			charset = "UTF-8";
		}

		//3.判断用户是否传递参数
		if(params !=null){
			//3.2准备List集合信息
			List<NameValuePair> parameters = 
					new ArrayList<>();

			//3.3将数据封装到List集合中
			for (Map.Entry<String,String> entry : params.entrySet()) {

				parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}

			//3.1模拟表单提交
			try {
				UrlEncodedFormEntity formEntity = 
						new UrlEncodedFormEntity(parameters,charset); //采用u8编码

				//3.4将实体对象封装到请求对象中
				post.setEntity(formEntity);
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}
		}

		//4.发送请求
		try {
			CloseableHttpResponse response = 
					httpClient.execute(post);

			//4.1判断返回值状态
			if(response.getStatusLine().getStatusCode() == 200) {

				//4.2表示请求成功
				result = EntityUtils.toString(response.getEntity(),charset);
			}else{
				System.out.println("获取状态码信息:"+response.getStatusLine().getStatusCode());
				throw new RuntimeException();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}



	public String doPost(String url){

		return doPost(url, null, null);
	}

	public String doPost(String url,Map<String,String> params){

		return doPost(url, params, null);
	}

	public String doPost(String url,String charset){

		return doPost(url, null, charset);
	}
}
