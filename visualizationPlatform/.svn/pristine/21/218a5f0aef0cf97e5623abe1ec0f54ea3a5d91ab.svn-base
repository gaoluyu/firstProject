package androidServer.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import androidServer.bean.TemplateSMS;

public class SMSUtil {

	public static String version = "2014-06-30";
	public static String server = "api.ucpaas.com";
	public static String accountSid = "5de6ec56ed69febb28275ab165cdefad";
	public static String authToken = "3db85c8528cd681539ccb07f6b4b390e";
	public static String appId = "1fb6fab7ec1e42b9bca170a5e8db56de";


	public static String getSignature(String accountSid, String authToken,
			String timestamp, EncryptUtil encryptUtil) throws Exception {
		String sig = accountSid + authToken + timestamp;
		String signature = encryptUtil.md5Digest(sig);
		return signature;
	}

	public static HttpResponse get(String cType, String accountSid,
			String authToken, String timestamp, String url,
			DefaultHttpClient httpclient, EncryptUtil encryptUtil)
			throws Exception {
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("Accept", cType);//
		httpget.setHeader("Content-Type", cType + ";charset=utf-8");
		String src = accountSid + ":" + timestamp;
		String auth = encryptUtil.base64Encoder(src);
		httpget.setHeader("Authorization", auth);
		HttpResponse response = httpclient.execute(httpget);
		return response;
	}

	public static HttpResponse post(String cType, String accountSid,
			String authToken, String timestamp, String url,
			DefaultHttpClient httpclient, EncryptUtil encryptUtil, String body)
			throws Exception {
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Accept", cType);
		httppost.setHeader("Content-Type", cType + ";charset=utf-8");
		String src = accountSid + ":" + timestamp;
		String auth = encryptUtil.base64Encoder(src);
		httppost.setHeader("Authorization", auth);
		BasicHttpEntity requestBody = new BasicHttpEntity();
		requestBody
				.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
		requestBody.setContentLength(body.getBytes("UTF-8").length);
		httppost.setEntity(requestBody);
		// 查看返回值
		HttpResponse response = httpclient.execute(httppost);
		return response;
	}

	// 转换时间格式
	public static String dateToStr(Date date, String pattern) {
		if (date == null || date.equals(""))
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}

	public static String templateSMS(String to, String param, String templateId) {
		// TODO Auto-generated method stub
		String result = "";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			// MD5加密
			EncryptUtil encryptUtil = new EncryptUtil();
			// 获取时间戳
			String timestamp = dateToStr(new Date(), "yyyyMMddHHmmss");// 获取时间戳
			String signature = getSignature(accountSid, authToken, timestamp,
					encryptUtil);
			StringBuffer sb = new StringBuffer("https://");
			sb.append(server);
			String url = sb.append("/").append(version).append("/Accounts/")
					.append(accountSid).append("/Messages/templateSMS")
					.append("?sig=").append(signature).toString();
			TemplateSMS templateSMS = new TemplateSMS();
			templateSMS.setAppId(appId);
			templateSMS.setTemplateId(templateId);
			templateSMS.setTo(to);
			templateSMS.setParam(param);
			ObjectMapper mapper = new ObjectMapper();
			String body = mapper.writeValueAsString(templateSMS);
			body = "{\"templateSMS\":" + body + "}";
			// System.out.println("post bpdy is: " + body);

			HttpResponse response = post("application/json", accountSid,
					authToken, timestamp, url, httpclient, encryptUtil, body);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
				System.out
						.println("templateSMS Response content is: " + result);
			}
			// 确保HTTP响应内容全部被读出或者内容流被关闭
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	/**
	 * 发送请求，返回响应
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private static String getResponseString(String url, String params) {
		String getUrl = "";
		try {
			getUrl = url + URLEncoder.encode(params, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // 将参数转为url编码

		/** 发送httpget请求 */
		HttpGet request = new HttpGet(getUrl);
		String result = "";
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				// System.out.println(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpClient != null)
				httpClient.getConnectionManager().shutdown();
		}
		return result;
	}

	private static String getValidateCode(int length) {
		char[] array = new char[length];
		for (int i = 0; i < array.length; i++) {
			array[i] = (char) (48 + (Math.random() * 10));
		}
		return new String(array);
	}

	public static String sendHandleMes(String telephone, String position, String netType, String startTime, String strength) {
		String templateId = "15686";
		String mes =  position + "," + netType + "," + startTime + "," + strength;
		templateSMS(telephone, mes, templateId);
		return mes;
	}
	
	
	public static String sendValidateCode(String phone) {
		String templateId = "15701";
		String val = getValidateCode(6);
		templateSMS(phone, val + ",1", templateId);
		return val;
	}

	public static void main(String[] args) {
		System.out.println(sendValidateCode("13718393533"));
	}
}
