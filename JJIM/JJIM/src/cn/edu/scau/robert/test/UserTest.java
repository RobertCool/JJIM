/**
 * 
 */
package cn.edu.scau.robert.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Test;

/**
 * @author robert
 *
 */
public class UserTest {
	
    private DefaultHttpClient httpClient;
    private String url = "http://localhost:8080/JJIM/user";
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		httpClient = new DefaultHttpClient();
	//	testLogin();
	}
	
	public void testLogin() throws Exception{
		 String account = "root";
	        String password = "root";
	        HttpGet getMethod = new HttpGet(url + "/" + account + "/" + password);
	        HttpResponse response = httpClient.execute(getMethod);
	        HttpEntity entity = response.getEntity();

	        List<Cookie> cookies = httpClient.getCookieStore().getCookies();
	        if (cookies.isEmpty()) {
	            System.out.println("None");
	        } else {
	            for (int i = 0; i < cookies.size(); i++) {
//	                System.out.println("- " + cookies.get(i).toString());
	            }
	        }

	        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));

	        String result = "";
	        while ((result = br.readLine()) != null) {
	            System.out.println("testLogin:"+result);
	        }

	        br.close();
	        getMethod.releaseConnection();
	}

	@Test
	public void testIndex() throws Exception{
		 HttpGet getMethod = new HttpGet(url);
	     HttpResponse response = httpClient.execute(getMethod);
	     HttpEntity entity = response.getEntity();
	     
	     BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));

	        String result = "";
	        while ((result = br.readLine()) != null) {
	            System.out.println("testIndex:"+result);
	        }

	        br.close();
	        getMethod.releaseConnection();
	}
	
	@Test
	public void testShow() throws Exception{
		String result = HttpUtilTest.doGet(url + "/1", httpClient);
		System.out.println("testShow:" + result);
	}

}
