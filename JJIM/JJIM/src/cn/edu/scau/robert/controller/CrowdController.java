/**
 * 
 */
package cn.edu.scau.robert.controller;

import java.util.List;

import org.apache.http.HttpStatus;

import cn.edu.scau.robert.Interceptor.LoginInterceptor;
import cn.edu.scau.robert.model.Crowd;
import cn.edu.scau.robert.model.CrowdFriend;
import cn.edu.scau.robert.model.Friend;
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
 *	群组资源
 */
@Before({Restful.class,LoginInterceptor.class})
public class CrowdController extends Controller{

	/**
	 * GET		/user			--->	index
	 * GET		/user/id		--->	show  
	 * POST		/user			--->	save	
	 * PUT		/user/id		--->	update
	 * DELECT	/user/id		--->	delete
	 */
	
	/**
	 * 查看所有群
	 * */
	public void index(){
		if(this.getPara("page") == null){
			//查看自己加入的群
			User user = (User)this.getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
			List<Crowd> crowds = Crowd.dao.find("select * from Crowd c, Crowd_Friend f " +
					"where f.FriendId = ? and f.CrowdId = c.CrowdId", user.get("UserId"));
			this.renderJson(ResourceMessage.success(crowds));
		}else{
			//查看所有的群
			int page = PublicLib.ifNullToInt(this.getPara("page"), 1);
			Page<Crowd> crowds = Crowd.dao.paginate(page, ConfigUtil.PAGE_SIZE, "select *", "from Crowd");
			this.renderJson(ResourceMessage.success(crowds));
		}
	}
	
	/**
	 * 查看指定ID的群 或 允许加入群
	 * */
	public void show(){
		String crowdId = this.getPara(0);
		Crowd crowd = Crowd.dao.findById(crowdId);
		
		String friendId = this.getPara("FriendId");
		
		if(crowd == null){
			this.renderJson(ResourceMessage.unreachable());
			return;
		}	
		
		if(friendId != null){
			
			Friend friend = Friend.dao.findById(friendId);
			
			if(friend == null){
				this.renderJson(ResourceMessage.unreachable());
				return;
			}else{
				CrowdFriend crowdFriend = new CrowdFriend().set("CrowdId", crowdId)
						.set("FriendId",friendId).set("UserGroupNickName", friend.getStr("FriendNickName"))
						.set("UserGroupPermission", UserStatus.ACTIVE.ordinal());
				if(crowdFriend.save()){
					this.renderJson(ResourceMessage.success(crowdFriend));
				}else{
					this.renderJson(ResourceMessage.error("加入群失败"));
				}
			}
			
		}else{
			this.renderJson(ResourceMessage.success(crowd));
		}
	}
	
	/**
	 * 创建群
	 * */
	public void save(){
		User user = (User)this.getRequest().getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		
		String crowdName = this.getPara("CrowdName");
		String crowdNote = this.getPara("CrowdNote");
		String crowdIcon = this.getPara("CrowdIcon");
		
		Crowd crowd = new Crowd().set("CrowdName", crowdName).set("CrowdNote", crowdNote)
				.set("CrowdIcon", crowdIcon).set("UserId", user.getStr("UserId"));

		boolean result = crowd.save();
		
		//将群创建者加入到群中，并设置为管理员
		CrowdFriend crowdFriend = new CrowdFriend().set("FriendId", user.getStr("UserId"))
				.set("CrowdId", crowd.getStr("CrowdId"))
				.set("UserGroupNickName", user.getStr("NickName"))
				.set("UserGroupPermission", UserStatus.ADMIN.ordinal());
		
		result = result && crowdFriend.save();
		
		if(result){
			this.renderJson(ResourceMessage.success(crowd));
		}else{
			this.renderJson(ResourceMessage.error("创建群失败，原因未知"));
		}
	}
	
	/**
	 * 删除群
	 * */
	public void delete(){
		User user = (User)this.getRequest().getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		
		final Crowd crowd = Crowd.dao.findById(this.getPara(0));
		if(crowd.getStr("UserId").equals(user.getStr("UserId"))){
			//返回204，表示资源不可用
			this.getResponse().setStatus(HttpStatus.SC_NO_CONTENT);
			return;
		}
		
		//从该群中移除所有用户，然后再删除群
		new Thread(new Runnable(){

			@Override
			public void run() {
				List<CrowdFriend> crowdFriends = CrowdFriend.dao.find("select * from Crowd_Friend where CrowdId = ?", crowd.getStr("CrowdId"));
				for(int i = 0; i < crowdFriends.size(); i++){
					crowdFriends.get(i).delete();
				}
			}
			
		}).start();
		
		//返回202，表示已接受处理
		this.getResponse().setStatus(HttpStatus.SC_ACCEPTED);
	}
	
	/**
	 * 更新群信息
	 * */
	public void update(){
		User  user = (User)this.getRequest().getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		
		String crowdId = this.getPara(0);
		String crowdName = this.getPara("CrowdName");
		String crowdNote = this.getPara("CrowdNote");
		String crowdIcon = this.getPara("CrowdIcon");
		
		Crowd crowd = Crowd.dao.findById(crowdId);
		
		//只有创建者才能修改群信息
		if(!user.get("UserId").equals(crowd.get("UserId"))){
			this.renderJson(ResourceMessage.unreachable());
			return;
		}
		
		if(crowdName != null){
			crowd.set("CrowdName", crowdName);
		}
		
		if(crowdNote != null){
			crowd.set("CrowdNote", crowdNote);
		}
		
		if(crowdIcon != null){
			crowd.set("CrowdIcon", crowdIcon);
		}
		
		if(crowd.update()){
			this.renderJson(ResourceMessage.success(crowd));
		}else{
			this.renderJson(ResourceMessage.error("修改群信息失败"));
		}
	}
}
