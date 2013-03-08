/**
 * 
 */
package cn.edu.scau.hci.robert.service;


import org.apache.commons.httpclient.NameValuePair;

import android.os.Message;
import cn.edu.scau.hci.robert.util.HttpUtil;

/**
 * @author robert
 *
 */
public class GroupService {

	/**
	 * 获取分组信息
	 * */
	public boolean getGroup(Message msg){
		String url = HttpUtil.host + "/group";
		try{
			HttpUtil.get(url, msg);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 创建分组
	 * @param groupName 分组的名称
	 * */
	public boolean saveGroup(String groupName, Message msg){
		String url = HttpUtil.host + "/group";
		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("GroupName", groupName);
		
		try {
			HttpUtil.post(url, msg, params);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		
	}
	
	/**
	 * 修改分组的名称
	 * @param groupId 		组编号
	 * @param groupName 新的组名称
	 * */
	public boolean updateGroup(Long groupId, String groupName, Message msg){
		String url = HttpUtil.host + "/group/" + groupId;
		NameValuePair[] params = new NameValuePair[1];
		params[0] = new NameValuePair("GroupName", groupName);
		
		try {
			HttpUtil.put(url, msg, params);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	/**
	 * 删除组
	 * @param groupId
	 * */
	public boolean deleteGroup(Long groupId, Message msg){
		String url = HttpUtil.host + "/group/" + groupId;
		try{
			HttpUtil.delete(url, msg);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
