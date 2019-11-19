package com.jt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.util.HttpClientService;
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHttpClient {
	
	/**
	 * 1.实例化httpClient对象
	 * 2.准备url请求地址  https://www.baidu.com/
	 * 3.封装请求方式对象   GET/POST/PUT/DELETE
	 * 4.发起http请求.获取服务器响应.
	 * 5.判断返回值状态码信息 200.
	 * 6.从响应对象中获取服务器返回值数据.
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	
	@Test
	public void testGet() throws ClientProtocolException, IOException {
		CloseableHttpClient client = 
							HttpClients.createDefault();
		String url = "https://www.baidu.com";
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = 
							client.execute(get);
		if(response.getStatusLine().getStatusCode() == 200) {
			//表示请求服务正确
			HttpEntity entity = response.getEntity();//返回值实体对象
			String result = 
					EntityUtils.toString(entity, "UTF-8");
			System.out.println(result);
		}
	}
	
	
	@Autowired
	private HttpClientService httpClient;
	
	@Test
	public void doGet() {
		//http://manage.jt.com/web/item/findItemById?itemId=1474391969
		String url = 
		"http://manage.jt.com/web/item/findItemById";
		Map<String,String> params = new HashMap<>();
		params.put("itemId", "562379");
		String json = httpClient.doGet(url, params);
		System.out.println(json);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
