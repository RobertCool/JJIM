/**
 * 
 */
package cn.edu.scau.hci.robert.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

import android.os.Message;
import cn.edu.scau.hci.robert.util.HttpUtil;

/**
 * @author robert
 *
 */
public class MessageService {

	/**
	 * 获取所有未读信息
	 * */
	public boolean getUnreadMessage(int page,Message msg){
		try{
			String url = HttpUtil.host + "/message?page="+page;
			HttpUtil.get(url, msg);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 查看指定消息内容
	 * */
	public boolean getMessage(int messageId, Message msg){
		try{
			String url = HttpUtil.host + "/message/" + messageId;
			HttpUtil.get(url, msg);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 发送消息
	 * */
	public boolean sendMessage(cn.edu.scau.hci.robert.entity.Message message, android.os.Message msg){
		try{
			String url = HttpUtil.host + "/message";
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			NameValuePair p1 = new NameValuePair("MessageType", message.getMessageType().toString());
			pairs.add(p1);
			if(message.getFriendId() != null){
				NameValuePair p2 = new NameValuePair("FriendId", message.getFriendId().toString());
				pairs.add(p2);
			}
			if(message.getMessageContent() != null){
				NameValuePair p3 = new NameValuePair("MessageContent", message.getMessageContent());
				pairs.add(p3);
			}
			if(message.getMessagePic() != null){
				NameValuePair p4 = new NameValuePair("MessagePic", message.getMessagePic());
				pairs.add(p4);
			}
			
			HttpUtil.post(url, msg, pairs.toArray(new NameValuePair[pairs.size()]));
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
}
