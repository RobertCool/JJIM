/**
 * 
 */
package cn.edu.scau.robert.controller;

import java.util.List;

import org.apache.http.HttpStatus;

import cn.edu.scau.robert.Interceptor.LoginInterceptor;
import cn.edu.scau.robert.model.GroupFriend;
import cn.edu.scau.robert.model.JGroup;
import cn.edu.scau.robert.model.User;
import cn.edu.scau.robert.util.ConfigUtil;
import cn.edu.scau.robert.util.ResourceMessage;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.Restful;

/**
 * @author robert
 *	好友组资源
 */
@Before({Restful.class,LoginInterceptor.class})
public class GroupController extends Controller {

	/**
	 * GET		/user			--->	index
	 * GET		/user/id		--->	show  
	 * POST		/user			--->	save	
	 * PUT		/user/id		--->	update
	 * DELECT	/user/id		--->	delete
	 */
	
	/**
	 * 获取当前登录用户的所有组信息
	 * */
	public void index(){
		User user = (User)this.getRequest().getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		List<JGroup> groups = JGroup.dao.find("select * from JGroup where UserId = ?", user.get("UserId"));
		this.renderJson(ResourceMessage.success(groups));
	}
	
	/**
	 * 获取指定id组的信息
	 * */
	public void show(){
		String groupId = this.getPara(0);
		User user = (User)this.getRequest().getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		JGroup group = JGroup.dao.findById(groupId);
		
		if(group != null){
			if( ! group.getStr("UserId").equals(user.getStr("UserId"))){
				this.renderJson(ResourceMessage.unreachable());
			}else{
				this.renderJson(ResourceMessage.success(group));
			}
		}else{
			this.renderJson(ResourceMessage.error("获取不到该组信息"));
		}
			
	}
	
	/**
	 * 新建好友组
	 * */
	public void save(){
		User user = (User)this.getRequest().getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		
		String groupName = this.getPara("GroupName");
		
		if(groupName == null){
			this.renderJson(ResourceMessage.error("组名不能为空"));
			return;
		}
		
		JGroup group = new JGroup().set("GroupName", groupName).set("UserId", user.getInt("UserId"));
		
		boolean result = group.save();
		
		if(result){
			this.renderJson(ResourceMessage.success(group));
		}else{
			this.renderJson(ResourceMessage.error("创建组失败，原因未知"));
		}
	}
	
	/**
	 * 删除好友组
	 * */
	public void delete(){
		User user = (User)this.getRequest().getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		
		String groupId = this.getPara(0);
		
		JGroup group = JGroup.dao.findById(groupId);
		if(group.getStr("UserId").equals(user.getStr("UserId"))){
			this.renderJson(ResourceMessage.unreachable());
			return;
		}
		
		List<GroupFriend> groupFriends = GroupFriend.dao.find("select * from Group_Friend where GroupId = ?", groupId);
		
		if(groupFriends == null || groupFriends.size() == 0){
			if(group.delete()){
				//200 (OK) - 删除成功，同时返回已经删除的资源
				//202 (Accepted) - 删除请求已经接受，但没有被立即执行（资源也许已经被转移到了待删除区域）
				//204 (No Content) - 删除请求已经被执行，但是没有返回资源（也许是请求删除不存在的资源造成的）
				this.renderJson(ResourceMessage.success(group));
			}else{
				this.getResponse().setStatus(HttpStatus.SC_NO_CONTENT);
			}
		}else{
			this.renderJson(ResourceMessage.error("好友组有好友，不能删除"));
		}

	}
	
	/**
	 * 更新好友组信息
	 * */
	public void update(){
		User user = (User)this.getRequest().getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		
		String groupId = this.getPara(0);
		String groupName = this.getPara("GroupName");
		
		if(groupName == null){
			this.renderJson(ResourceMessage.error("分组名不能为空"));
			return;
		}
		
		JGroup group = JGroup.dao.findById(groupId);
		
		//判断用户是否有权限，用户只能修改自己的好友组信息
		if(group.getStr("UserId").equals(user.getStr("UserId"))){
			this.renderJson(ResourceMessage.unreachable());
			return;
		}
		
		if(group.set("GroupName", groupName).update()){
			this.renderJson(ResourceMessage.success(group));
		}else{
			this.renderJson(ResourceMessage.error("修改组名称失败"));
		}
	}
	
}
