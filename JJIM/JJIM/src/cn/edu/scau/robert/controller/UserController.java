/**
 * 
 */
package cn.edu.scau.robert.controller;


import cn.edu.scau.robert.Interceptor.LoginInterceptor;
import cn.edu.scau.robert.entity.JsonResult;
import cn.edu.scau.robert.model.Friend;
import cn.edu.scau.robert.model.User;
import cn.edu.scau.robert.util.ConfigUtil;
import cn.edu.scau.robert.util.ResourceMessage;
import cn.edu.scau.robert.util.MD5Util;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.Restful;

/**
 * @author robert
 * 用户相关资源，主要用于个人信息的维护
 * delete、save方法不可使用
 */
@Before({Restful.class,LoginInterceptor.class})
public class UserController extends Controller {

	/**
	 * GET		/user			--->	index
	 * GET		/user/id		--->	show  
	 * POST		/user			--->	save	
	 * PUT		/user/id		--->	update
	 * DELECT	/user/id		--->	delete
	 */
	
	/**
	 * 返回当前已登录的用户信息
	 * */
	public void index(){
		User user = (User)this.getRequest().getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		this.renderJson(user);
	}

	/**
	 * 通过用户ID获取用户信息
	 * */
	public void show(){
		String userId = this.getPara(0);
		User user = User.dao.findById(userId);
		JsonResult result = new JsonResult();
		if(user == null){
			result.setSuccess(false);
			result.setResult("系统找不到该用户");
		}else{
			result.setSuccess(true);
			result.setResult(user);
		}
		this.renderJson(result);
	}
	
	/**
	 * 保存用户信息
	 * 不实现该方法
	 * */
	public void save(){
		this.renderJson(ResourceMessage.unreachable());
	}
	
	/**
	 * 更新当前用户信息
	 * */
	public void update(){
		User user = (User)this.getRequest().getSession().getAttribute(ConfigUtil.SESSION_USER_KEY);
		
		String sex = this.getPara("sex");
		String oldPwd = this.getPara("oldPwd");
		String pwd1 = this.getPara("password1");
		String pwd2 = this.getPara("password2");
		String note = this.getPara("note");
		String nickName = this.getPara("nickName");
		String icon = this.getPara("icon");
		
		@SuppressWarnings("unused")
		String email = this.getPara("email");
		
		Friend friend = Friend.dao.findById(user.getLong("UserId"));
		
		if(oldPwd != null){
			if(pwd1 == null || pwd2 == null){
				this.renderJson(ResourceMessage.error("新密码不能为空"));
				return;
			}else if(!pwd1.equals(pwd2)){
				this.renderJson(ResourceMessage.error("两次输入的新密码不相同"));
				return;
			}else if(!MD5Util.getMD5String(oldPwd).equals(user.getStr("Password"))){
				this.renderJson(ResourceMessage.error("旧密码不正确"));
				return;
			}else{
				user.set("Password", MD5Util.getMD5String(oldPwd));
			}
		}
		
		if(note != null){
			user.set("UserNote", note);
			friend.set("FriendNote", note);
		}
		
		//TODO:实现修改注册邮箱的方法
		if(icon != null){
			user.set("UserIcon", icon);
			friend.set("FriendIcon", icon);
		}
		
		if(sex != null){
			try{
				Integer.getInteger(sex);
				user.set("Sex", sex);
				friend.set("FriendSex", sex);
			}catch(NumberFormatException e){
				this.renderJson(ResourceMessage.error("输入的性别参数不正确"));
				return;
			}
		}
		
		if(nickName != null){
			user.set("NickName", nickName);
			friend.set("FriendNickName", nickName);
		}
		
		boolean result = user.update() && friend.update();
		
		if(result){
			this.renderJson(ResourceMessage.success(user));
		}else{
			this.renderJson(ResourceMessage.error("更新失败，未知错误"));
		}
	}
	
	/**
	 * 删除用户
	 * 不实现该方法
	 * */
	public void delete(){
		this.renderJson(ResourceMessage.unreachable());
	}
}
