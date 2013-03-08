/**
 * 
 */
package cn.edu.scau.hci.robert.service;

import org.apache.commons.httpclient.NameValuePair;

import android.os.Message;
import cn.edu.scau.hci.robert.util.HttpUtil;

/**
 * @author robert
 * 获取好友相关的信息
 */
public class FriendService {
	
	/**
	 * 分页查找用户
	 * */
	public boolean getAllFriends(int page, Message msg){
		String url  = HttpUtil.host + "/friend?page=" + page;
		try{
			HttpUtil.get(url, msg);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据组id获得某个分组好友
	 * */
	public boolean getFriendsByGroupId(int groupId, Message msg){
		String url  = HttpUtil.host + "/friend?GroupId=" + groupId;
		try{
			HttpUtil.get(url, msg);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 查看某个好友的信息
	 * */
	public boolean getFriendById(int friendId, Message msg){
		String url  = HttpUtil.host + "/friend/" + friendId;
		try{
			HttpUtil.get(url, msg);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 添加好友进分组
	 * */
	public boolean addFriend(int friendId, int groupId, Message msg){
		String url  = HttpUtil.host + "/friend";
		try{
			NameValuePair[] params = new NameValuePair[2];
			params[0] = new NameValuePair("GroupId", String.valueOf(groupId));
			params[1] = new NameValuePair("FriendId", String.valueOf(friendId));
			HttpUtil.post(url, msg, params);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**删除好友*/
	public boolean deleteFriend(int friendId, Message msg){
		String url  = HttpUtil.host + "/friend/" + friendId;
		try{
			HttpUtil.delete(url, msg);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**移动好友进其他分组*/
	public boolean moveFriend(int friendId, int groupId, Message msg){
		String url  = HttpUtil.host + "/friend";
		try{
			NameValuePair[] params = new NameValuePair[2];
			params[0] = new NameValuePair("GroupId", String.valueOf(groupId));
			params[1] = new NameValuePair("FriendId", String.valueOf(friendId));
			HttpUtil.put(url, msg, params);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**查找指定账号的好友*/
	public boolean findFriend(String account, Message msg){
		String url = HttpUtil.host + "/friend?FriendAccount=" + account;
		try{
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
