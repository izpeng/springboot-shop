package com.jt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
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

/**
 * httpclient
 * @author 000
 *https://www.baidu.com/
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHttpClient {
	/**
	 * 1.实例化
	 * 2.url
	 * 3.封装请求对象
	 * 4.发起请求
	 * 5.判断状态码200
	 * 6.获取返回值
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Test
	public void testGet() throws ClientProtocolException, IOException {

		CloseableHttpClient client = HttpClients.createDefault();
		String url = "https://www.baidu.com/";
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = client.execute(get);
		if(response.getStatusLine().getStatusCode()==200) {
			//请求正确
			HttpEntity entity = response.getEntity();
			
			String string = EntityUtils.toString(entity, "utf-8");
			System.out.println(string);
			
		}

	}
	@Autowired
	private HttpClientService httpClient ;
//	/http://manage.jt.com/web/item/findItemById?itemId=562379
	@Test
	public void doGet() {

		Map<String,String> map = new HashMap<>();
		map.put("itemId","562379");
		String doGet = httpClient.doGet("http://manage.jt.com/web/item/findItemById", map);
		System.out.println(doGet);
		
	}
	
	
}
