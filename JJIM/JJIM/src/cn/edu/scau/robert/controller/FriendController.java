/**
 * 
 */
package cn.edu.scau.robert.controller;


import java.util.List;

import org.apache.http.HttpStatus;

import cn.edu.scau.robert.Interceptor.LoginInterceptor;
import cn.edu.scau.robert.model.Friend;
import cn.edu.scau.robert.model.GroupFriend;
import cn.edu.scau.robert.model.JGroup;
import cn.edu.scau.robert.model.User;
import cn.edu.scau.robert.util.ConfigUtil;
import cn.edu.scau.robert.util.PublicLib;
import cn.edu.scau.robert.util.ResourceMessage;
import cn.edu.scau.robert.util.UserStatus;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.Restful;
import com.jfinal.plugin.activerecord.Page;

/**
 * @author robert
 * 好友信息管理
 */
@Before({Restful.class,LoginInterceptor.class})
public class FriendController extends Controller {

	/**
	 * GET		/user			--->	index
	 * GET		/user/id		--->	show  
	 * POST		/user			--->	save	
	 * PUT		/user/id		--->	update
	 * DELECT	/user/id		--->	delete
	 */
	
	/**
	 * 分页查看所有用户信息 或查看登录用户的某组的用户信息
	 * */
	public void index(){
		int page = PublicLib.ifNullToInt(this.getPara("page"), 1);
		String groupId = this.getPara("GroupId");
		String friendAccount = this.getPara("FriendAccount");
		if(groupId != null){
			//查看登录用户的某组用户信息
			User user = (User)this.getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
			List<Friend> friends = Friend.dao.find("select * from Friend f, Group_Friend g, JGroup j where j.UserId = ?" +
					" and g.GroupId = j.GroupId and f.FriendId = g.FriendId and g.GroupId = ?"
					, user.get("UserId"), groupId);
			this.renderJson(ResourceMessage.success(friends));
		}else if(friendAccount != null){
			Friend friend = Friend.dao.findFirst("select * from Friend where FriendAccount=?", friendAccount);
			this.renderJson(ResourceMessage.success(friend));
		}else{
			//查看所有用户信息
			if(page <= 0) page = 1;
			Page<Friend> friends = Friend.dao.paginate(page, ConfigUtil.PAGE_SIZE, "select *", "from Friend");
			this.renderJson(ResourceMessage.success(friends));
		}

		
	}
	
	/**
	 * 显示单个用户的信息
	 * */
	public void show(){
		String friendId = this.getPara(0);
		Friend friend = Friend.dao.findById(friendId);
		this.renderJson(ResourceMessage.success(friend));
	}
	
	/**
	 * 保存好友信息
	 * */
	public void save(){
		//添加好友到分组中
		User user = (User)this.getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		
		String groupId = this.getPara("GroupId");
		String friendId = this.getPara("FriendId");
		
		GroupFriend tempGroup = GroupFriend.dao.findFirst("select * from Group_Friend where FriendId=? and GroupId = ?", friendId,groupId);
		if(tempGroup != null){
			this.renderJson(ResourceMessage.error("该好友已在好友列表中"));
			return;
		}
		
		JGroup group = JGroup.dao.findById(groupId);
		
		if(group == null || !group.get("UserId").equals(user.get("UserId"))){
			this.renderJson(ResourceMessage.unreachable());
			return;
		}
		
		GroupFriend groupFriend = new GroupFriend().set("GroupId", groupId).set("FriendId", friendId)
				.set("FriendStatus", UserStatus.ACTIVE.ordinal());
		
		if(groupFriend.save()){
			this.renderJson(ResourceMessage.success(groupFriend));
		}else{
			this.renderJson(ResourceMessage.error("添加好友失败"));
		}
	}
	
	/**
	 * 删除好友信息
	 * */
	public void delete(){
		//从好友分组中删除好友
		//TODO:需要多多测试
		User user = (User)this.getRequest().getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		String friendId = this.getPara(0);
		
		GroupFriend groupFriend = GroupFriend.dao.findFirst("select * from JGroup j,Group_Friend g " +
				"where g.GroupId=j.GroupId and j.UserId = ? and  g.FriendId = ?", user.getStr("UserId"), friendId);
		
		if(groupFriend == null || !groupFriend.delete()){
			this.getResponse().setStatus(HttpStatus.SC_NO_CONTENT);
		}else{
			this.getResponse().setStatus(HttpStatus.SC_OK);
		}
	}
	
	/**
	 * 更新好友信息
	 * */
	public void update(){
		//TODO:将好友移动到其他好友分组
		User user = (User)this.getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		
		String friendId = this.getPara("FriendId");
		String groupId = this.getPara("GroupId");
		
		JGroup group = JGroup.dao.findById(groupId);
		
		if(group == null){
			this.renderJson(ResourceMessage.unreachable());
			return;
		}
		
		//TODO:使用原子操作
		//从旧的好友分组删除好友
		GroupFriend.dao.findFirst("select * from JGroup j,Group_Friend g " +
				"where g.GroupId=j.GroupId and j.UserId = ? and  g.FriendId = ?", user.getStr("UserId"), friendId).delete();
		
		GroupFriend groupFriend = new GroupFriend().set("FriendId", friendId).set("GroupId", groupId)
				.set("FriendStatus", UserStatus.ACTIVE.ordinal());
		
		//保存好友到新的分组
		if(groupFriend.save()){
			this.renderJson(ResourceMessage.success(groupFriend));
		}else{
			this.renderJson(ResourceMessage.error("移动好友失败，原因未知"));
		}
	}
	
	
	
}
