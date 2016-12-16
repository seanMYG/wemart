package cn.wemart.mobileBuyerManagment;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

import org.apache.http.impl.client.CloseableHttpClient;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import cn.wemart.httppost.ExecutePut;
import cn.wemart.util.LoadAPIInfo;
import cn.wemart.util.getCurrent;

@Listeners({com.TestNG.AssertionListener.class})
public class UpdateAddress {
	public String response;
	@Test
	public String test(String scenId,String BuyerId,Object[][] keyValueList) throws UnsupportedEncodingException{
		BuyerLogin buyerLogin = new BuyerLogin();
		CloseableHttpClient httpClient = buyerLogin.Do(scenId, BuyerId);
		String url = LoadAPIInfo.url + "/api/usermng/buyer/address";
		Reporter.log(getCurrent.Time());
		response = ExecutePut.getPutMethodResponse(httpClient, url, keyValueList);
		String returnValue = JSONObject.fromObject(response).getString("returnValue");
		return returnValue;
	}

}