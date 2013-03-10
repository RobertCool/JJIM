/**
 * 
 */
package cn.edu.scau.robert.controller;

import java.util.Date;
import java.util.List;

import cn.edu.scau.robert.Interceptor.LoginInterceptor;
import cn.edu.scau.robert.model.Friend;
import cn.edu.scau.robert.model.Message;
import cn.edu.scau.robert.model.User;
import cn.edu.scau.robert.util.ConfigUtil;
import cn.edu.scau.robert.util.MessageStatus;
import cn.edu.scau.robert.util.MessageType;
import cn.edu.scau.robert.util.PublicLib;
import cn.edu.scau.robert.util.ResourceMessage;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.Restful;
import com.jfinal.plugin.activerecord.Page;

/**
 * @author robert
 * 消息资源
 */
@Before({Restful.class,LoginInterceptor.class})
public class MessageController extends Controller{

	/**
	 * GET		/user			--->	index
	 * GET		/user/id		--->	show  
	 * POST		/user			--->	save	
	 * PUT		/user/id		--->	update
	 * DELECT	/user/id		--->	delete
	 */
	
	/**
	 * 查看未读消息
	 * */
	public void index(){
		User user = (User)this.getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		int pageNumber = PublicLib.ifNullToInt(this.getPara("Page"), 1);
		Page<Message> messages = Message.dao.paginate(pageNumber, ConfigUtil.PAGE_SIZE, "select *", "from Message where FriendId = ? " +
				"and MessageStatus = ?", user.get("UserId"), MessageStatus.UnRead.ordinal());
		this.renderJson(ResourceMessage.success(messages));
		
		for(int i = 0; i< messages.getList().size();i++){
			messages.getList().get(i).set("MessageStatus", MessageStatus.Read.ordinal()).update();
		}
	}
	
	/**
	 * 获取指定消息内容
	 * */
	public void show(){
		User user = (User)this.getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		String messageId = this.getPara(0);
		
		Message message = Message.dao.findById(messageId);
		
		if(message == null || !message.get("UserId").equals(user.get("UserId"))){
			this.renderJson(ResourceMessage.unreachable());
		}else{
			this.renderJson(ResourceMessage.success(message));
		}
	}
	
	/**
	 * 发送消息
	 * */
	public void save(){
		User user = (User)this.getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		int type = this.getParaToInt("MessageType");
		
		String friendId = this.getPara("FriendId");
		String messageContent = this.getPara("MessageContent");
		String messagePic = this.getPara("MessagePic");
		Date messageTime = new Date();
		MessageStatus messageStatus = MessageStatus.UnRead;
		
		if(type == MessageType.Crowd.ordinal()){
			//发送给群的消息
			List<Friend> friends = Friend.dao.find("select * from Friend f,Crowd_Friend cf where cf.CrowdId = ? and cf.FriendId=f.FriendId", friendId);
			for(int i = 0; i<friends.size();i++){
				if(friends.get(i).get("FriendId").equals(user.get("UserId"))) continue;
				Message message = new Message().set("FriendId",friends.get(i).get("FriendId")).set("MessageContent", messageContent)
						.set("MessagePic", messagePic).set("MessageTime", messageTime)
						.set("MessageType", type).set("MessageStatus", messageStatus.ordinal())
						.set("UserId", friendId);//其实这个是群编号
				message.save();
			}
			this.renderJson(ResourceMessage.success("success"));
		}
		else{
			Message message = new Message().set("FriendId", friendId).set("MessageContent", messageContent)
					.set("MessagePic", messagePic).set("MessageTime", messageTime)
					.set("MessageType", type).set("MessageStatus", messageStatus.ordinal())
					.set("UserId", user.get("UserId"));
		
			if(message.save()){
				this.renderJson(ResourceMessage.success(message));
			}else{
				this.renderJson(ResourceMessage.error("发送消息失败"));
			}
		}
//		switch(type){
//		case	Friend:
//			break;
//		case FriendAdd:
//			break;
//		case System:
//			break;
//		case Crowd:
//			break;
//		case CrowdAdd:
//			break;
//		default:
//			break;
//		}
	}
	
	/**
	 * 更新消息内容
	 * */
	public void update(){
		User user = (User)this.getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		
		String messageId = this.getPara(0);
		MessageStatus messageStatus = MessageStatus.valueOf(this.getPara("MessageStatus"));
		
		Message message = Message.dao.findById(messageId);
		if(message == null || !message.get("FriendId").equals(user.get("UserId"))){
			this.renderJson(ResourceMessage.unreachable());
			return;
		}
		
		message.set("MessageStatus", messageStatus.ordinal());
		
		if(message.update()){
			this.renderJson(ResourceMessage.success(message));
		}else{
			this.renderJson(ResourceMessage.error("更新消息失败"));
		}

	}
	
	/**删除消息*/
	public void delete(){
		this.renderJson(ResourceMessage.unreachable());
	}

}
