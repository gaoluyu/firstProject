package androidServer.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpPool {
	private CloseableHttpClient httpClient;
	private static HttpPool pool;

	private HttpPool() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		// 将最大连接数增加到200
		cm.setMaxTotal(200);
		// 将每个路由基础的连接增加到20
		cm.setDefaultMaxPerRoute(20);
		// 将目标主机的最大连接数增加到50
		HttpHost localhost = new HttpHost("api.map.baidu.com", 80);
		cm.setMaxPerRoute(new HttpRoute(localhost), 50);
		httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}

	public static HttpPool getInstance() {
		if (pool == null)
			pool = new HttpPool();
		return pool;
	}

	public Map<String, Object> post(String url, Map<String, String> params) {
		HttpClientContext context = HttpClientContext.create();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> p = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			p.add(new BasicNameValuePair(key, params.get(key)));
		}

		String result = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(p, HTTP.UTF_8));
			CloseableHttpResponse response = httpClient.execute(post, context);
			try {

				if (response.getStatusLine().getStatusCode() == 200) {
					// saveCookies(response);
					result = new String(EntityUtils.toString(response.getEntity()).getBytes("ISO_8859_1"), "UTF-8");// ISO_8859_1
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException ex) {
			// Handle protocol errors
		} catch (IOException ex) {
			// Handle I/O errors
		}
		 System.out.println(result);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (result != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				resultMap = mapper.readValue(result, Map.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultMap;
	}

	public void closePool() {
		try {
			httpClient.close();
			pool = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
