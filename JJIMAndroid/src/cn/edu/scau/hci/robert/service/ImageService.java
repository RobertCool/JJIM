/**
 * 
 */
package cn.edu.scau.hci.robert.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import cn.edu.scau.hci.robert.common.FilePath;
import cn.edu.scau.hci.robert.util.HttpUtil;
import cn.edu.scau.hci.robert.util.Logger;

/**
 * @author robert
 *
 */
public class ImageService {

	/**
	 * @return String theFilePath
	 * */
	public String getImage(String url){
		String tagFilePath = this.tryGetImage(url);
		if(tagFilePath == null){
			HttpClient httpClient = new HttpClient();
			tagFilePath = FilePath.getFriendimagecache() +  url.substring(url.lastIndexOf("/") + 1) +  ".jpg";
			Logger.i("tagFilePath", tagFilePath);
			GetMethod getMethod = new GetMethod(HttpUtil.root + url);
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				    new DefaultHttpMethodRetryHandler());
			try {
				int status = httpClient.executeMethod(getMethod);
				if(status ==  HttpStatus.SC_OK){
					InputStream is = getMethod.getResponseBodyAsStream();
					FileOutputStream fos = new FileOutputStream(new File(tagFilePath));
					int length = 0;
					byte[] buffer = new byte[8096];
					while( -1 != (length=is.read(buffer, 0, buffer.length))){
						fos.write(buffer, 0, length);
					}
					fos.flush();
					fos.close();
					is.close();
				}
				getMethod.releaseConnection();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		Logger.i("TagerFilePath", tagFilePath);
		return tagFilePath;
	}
	
	public String tryGetImage(String url){
		String imageId = url.substring(url.lastIndexOf("/") + 1);
		Logger.i("ImageId", imageId);
		String tagFilePath = FilePath.getFriendimagecache() + imageId + ".jpg";
		if(new File(tagFilePath).exists()){
			return tagFilePath;
		}else{
			return null;
		}
	}
}
