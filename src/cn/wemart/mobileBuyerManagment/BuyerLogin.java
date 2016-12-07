package cn.wemart.mobileBuyerManagment;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.TestNG.Assertion;

import cn.wemart.httppost.ExecutePost;
import cn.wemart.httppost.ExecutePut;
import cn.wemart.util.LoadAPIInfo;
import cn.wemart.util.LoadSignString;
import cn.wemart.util.RSASignature;
import cn.wemart.util.getCurrent;

@Listeners({com.TestNG.AssertionListener.class})
public class BuyerLogin {
	

	/**
	 * 对应20510号接口（买家登录）
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public static CloseableHttpClient Do(String scenId,String buyerId) throws UnsupportedEncodingException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String url = LoadAPIInfo.url+"/api/shopping/buyer";
		String scenType = "1";
		String sign = RSASignature.getSign(scenId, buyerId);
		if(null != sign){
			Object[][] keyValueList = new Object[][]{
					{"scenId" , scenId},
					{"scenType" , scenType},
					{"buyerId" , buyerId},
					{"sign" , sign}
					}; 
			
			Reporter.log(getCurrent.Time()); 
			String response = ExecutePost.getPostMethodResponse(httpClient,url,keyValueList);
			String returnValue = JSONObject.fromObject(response).getString("returnValue");
			if (Assertion.verifyEqual(returnValue, "0")) {
				Reporter.log("买家登录成功！");
			}
			else{
				Reporter.log("买家登录失败！\n Response："+response+"");
			}
			Reporter.log(getCurrent.Time()); 
		}
		return httpClient;
	}
}