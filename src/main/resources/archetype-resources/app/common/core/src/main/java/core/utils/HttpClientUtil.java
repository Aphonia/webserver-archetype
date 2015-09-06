/**
 * @date 2014年6月20日 下午5:24:38
 * @author lim
 * @version V1.0 
 */
package ${package}.core.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * 
 * Module: HttpClientUtil.java Description:
 * 以get/post的方式发送数据到指定的http接口---利用httpclient.jar包---HTTP接口的调用 Company: Author:
 * ptp Date: Feb 22, 2012
 */

public class HttpClientUtil {

	// private static final Log log = LogFactory
	// .getLog(HttpClientUtil.class);

	private static final String DEFAULT_ENCODING = "UTF-8";
	/**
	 * get方式
	 * 
	 * @param param1
	 * @param param2
	 * @return
	 */
	public static String getHttp(String url, Map<String, Object> params) {
		String responseMsg = "";
		// 1.构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 用于测试的http接口的url
		// String url = "http://www.zgwsw.cn:8080/login.shtml";
		if (params != null) {
			url += "?";
			for (Entry<String, Object> param : params.entrySet()) {
				try {
					String value=URLEncoder.encode(param.getValue().toString(), "utf-8");
					url += param.getKey() + "=" + value + "&";
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			url = url.substring(0, url.length() - 1);
		}
		// 2.创建GetMethod的实例
		GetMethod getMethod = new GetMethod(url);
		// 使用系统系统的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			// 3.执行getMethod,调用http接口
			httpClient.executeMethod(getMethod);
			// 4.读取内容
			byte[] responseBody = getMethod.getResponseBody();
			// 5.处理返回的内容
			responseMsg = new String(responseBody,DEFAULT_ENCODING);
			// log.info(responseMsg);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 6.释放连接
			getMethod.releaseConnection();
		}
		return responseMsg;
	}

	/**
	 * post方式
	 * 
	 * @param param1
	 * @param param2
	 * @return
	 */
	public static String postHttp(String targetUrl, Map<String, Object> params) {
		String responseMsg = "";
		// 1.构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");

		// 2.构造PostMethod的实例
		PostMethod postMethod = new PostMethod(targetUrl);
		postMethod.addRequestHeader("Content-Type", "text/html;charset=UTF-8");

		// 3.把参数值放入到PostMethod对象中
		// 方式1：
		/*
		 * NameValuePair[] data = { new NameValuePair("param1", param1), new
		 * NameValuePair("param2", param2) }; postMethod.setRequestBody(data);
		 */

		// 方式2：
		if (params != null) {
			for (Entry<String, Object> param : params.entrySet()) {
				postMethod.addParameter(param.getKey(), param.getValue() + "");
			}
		}

		try {
			// 4.执行postMethod,调用http接口
			httpClient.executeMethod(postMethod);// 200
			// 5.读取内容
			responseMsg = postMethod.getResponseBodyAsString().trim();
			// log.info(responseMsg);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 7.释放连接
			postMethod.releaseConnection();
		}
		return responseMsg;
	}

	public static String postSend(String strUrl, String param) {

		URL url = null;
		HttpURLConnection connection = null;

		try {
			url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.connect();

			// POST方法时使用
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			out.writeBytes(param);
			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

	}

	public static void main(String[] args) {
		String uri = "http://120.27.96.105:8080/alipay/rechargeAmount.json";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("category", "CLINET_FEE");
		params.put("trade_no", "123123");
		System.out.println(HttpClientUtil.postHttp(uri, params));
	}

}