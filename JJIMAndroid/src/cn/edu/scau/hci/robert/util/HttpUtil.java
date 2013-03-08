package cn.edu.scau.hci.robert.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;

import android.os.Message;

public class HttpUtil {
	
	public static final String host = "http://172.16.19.219:8080/JJIM";
	public static final String root = "http://172.16.19.219:8080";
	
	private static HttpClient httpClient;
	private static Object obj = new Object();

	public static HttpClient getInstance(){
		synchronized(obj) {
			if(httpClient == null){
				httpClient = new HttpClient();
			}
		}
		return httpClient;
	}
	
	public static void get(String url, Message message) throws HttpException, IOException{
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
			    new DefaultHttpMethodRetryHandler());
		
		int statusCode = HttpUtil.getInstance().executeMethod(getMethod);
		if(statusCode == HttpStatus.SC_OK){
			message.obj = getMethod.getResponseBodyAsString();
		}
		getMethod.releaseConnection();
	}
	
	public static void post(String url, Message message, NameValuePair[] params) throws HttpException, IOException{
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestBody(params);
		postMethod.getParams().setContentCharset("UTF-8");
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		
		int statusCode = HttpUtil.getInstance().executeMethod(postMethod);
		if(statusCode == HttpStatus.SC_OK){
			message.obj = postMethod.getResponseBodyAsString();
		}
		postMethod.releaseConnection();
	}
	
	public static void put(String url, Message message, NameValuePair[] params) throws HttpException, IOException{
		PutMethod putMethod = new PutMethod(url);
		putMethod.setQueryString(params);
		putMethod.getParams().setContentCharset("UTF-8");
		putMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		
		int statusCode = HttpUtil.getInstance().executeMethod(putMethod);
		if(statusCode == HttpStatus.SC_OK){
			message.obj = putMethod.getResponseBodyAsString();
		}
		putMethod.releaseConnection();
	}
	
	public static void delete(String url, Message message) throws HttpException, IOException{
		DeleteMethod deleteMethod = new DeleteMethod(url);
		
		int statusCode = HttpUtil.getInstance().executeMethod(deleteMethod);
		message.obj = statusCode;
		
		deleteMethod.releaseConnection();
	}
	
	public static void upload(String url,File file,Message message) throws HttpException, IOException{
		Part[] parts = { new FilePart("imgFile", file) };
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestEntity(new MultipartRequestEntity(parts, postMethod.getParams()));
		
		int statusCode = HttpUtil.getInstance().executeMethod(postMethod);
		if(statusCode == HttpStatus.SC_OK){
			message.obj = postMethod.getResponseBodyAsString();
		}
		
		postMethod.releaseConnection();
	}
}
