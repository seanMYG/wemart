package cn.wemart.httppost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.dom4j.DocumentException;

public class getHttpClient {
	private static Log log = LogFactory.getLog(getHttpClient.class);
	
	/**
	 * 组装请求参数
	 * @param keyVlaueList
	 */
	public static Map<Object ,Object> setPostPara(Object[][] keyValueList) throws DocumentException{
		Map<Object ,Object> map = new HashMap<Object,Object>();
		for(int i = 0;i<keyValueList.length;i++)
		{
			map.put(keyValueList[i][0],keyValueList[i][1]);
		}
		return map;
	}
	
	/**
	 * 获取接口返回字符串
	 * @param url,需要传入的参数键值对
	 */
	public static CloseableHttpClient Login(String url,Object[][] keyValueList){
		String xmlStr =null;
		String str = "";
		CloseableHttpClient httpclient = null;
		try {
			Map<Object ,Object> postMap = setPostPara(keyValueList);
			JSONObject object = JSONObject.fromObject(postMap);
			log.info(url+"?para="+object.toString()+"");
			System.out.println(url+"?para="+object.toString()+"");
			List<NameValuePair> postPara = new ArrayList<NameValuePair>();
			postPara.add(new BasicNameValuePair("para",object.toString()));
			httpclient = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);

			post.setEntity(new UrlEncodedFormEntity(postPara,HTTP.UTF_8));
			CloseableHttpResponse response = httpclient.execute(post);
		
			//解决中文编码问题
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
			StringBuffer sb = new StringBuffer();
			while ((str = reader.readLine()) != null) {
				sb.append(str).append("\n");
			}
			xmlStr = sb.toString().trim();
			log.info(xmlStr);
			System.out.println(xmlStr);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}catch (DocumentException e) {
				e.printStackTrace();
			}
		return httpclient;
	}

}