/**
 * 
 */
package cn.edu.scau.hci.robert.service;

import java.io.File;

import org.apache.commons.httpclient.NameValuePair;

import cn.edu.scau.hci.robert.entity.User;
import cn.edu.scau.hci.robert.util.HttpUtil;
import android.os.Message;

/**
 * @author robert
 *
 */
public class CommonService {

	/**
	 * 登录
	 * @param account 账号
	 * @param password 密码
	 * */
	public boolean login(String account, String password, Message msg){
		NameValuePair[] params = new NameValuePair[2];
		params[0] = new NameValuePair("account", account);
		params[1] = new NameValuePair("password", password);
		try {
			HttpUtil.post(HttpUtil.host + "/common/sign", msg, params);
			return true;
		}  catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 注册用户
	 * @param user 封装的用户信息
	 * */
	public boolean register(User user ,Message msg){
		String url = HttpUtil.host + "/common/add";
		NameValuePair[] params = new NameValuePair[7];
		params[0] = new NameValuePair("sex", String.valueOf(user.getSex()));		
		params[1] = new NameValuePair("password1", user.getPassword());
		params[2] = new NameValuePair("password2", user.getPassword());
		params[3] = new NameValuePair("note", user.getUserNote());
		params[4] = new NameValuePair("nickName", user.getNickName());
		params[5] = new NameValuePair("icon", user.getUserIcon());
		params[6] = new NameValuePair("email", user.getUserEmail());
		
		try{
			HttpUtil.post(url, msg, params);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 上传图片
	 * */
	public boolean uploadImg(File file, Message msg){
		String url = HttpUtil.host + "/image";
		try{
			HttpUtil.upload(url, file, msg);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
