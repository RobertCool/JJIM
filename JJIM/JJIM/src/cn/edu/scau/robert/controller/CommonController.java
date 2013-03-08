/**
 * 
 */
package cn.edu.scau.robert.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.edu.scau.robert.entity.JsonResult;
import cn.edu.scau.robert.model.Friend;
import cn.edu.scau.robert.model.JGroup;
import cn.edu.scau.robert.model.Message;
import cn.edu.scau.robert.model.User;
import cn.edu.scau.robert.util.ConfigUtil;
import cn.edu.scau.robert.util.FriendStatus;
import cn.edu.scau.robert.util.MD5Util;
import cn.edu.scau.robert.util.MessageStatus;
import cn.edu.scau.robert.util.MessageType;
import cn.edu.scau.robert.util.ResourceMessage;
import cn.edu.scau.robert.util.UserStatus;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.aop.ClearLayer;
import cn.edu.scau.robert.util.AccountUtil;

;

/**
 * @author robert
 * 
 */
@ClearInterceptor(ClearLayer.ALL)
public class CommonController extends Controller {
	


	/**
	 * 用户登录 for json
	 * */
	public void sign() {
		String account = this.getPara("account");
		String password = this.getPara("password");
		User user = User.dao.findFirst(
				"select * from User where UserAccount = ?", account);

		JsonResult result = new JsonResult();
		if (user != null
				&& MD5Util.getMD5String(password).equals(
						user.getStr("Password"))) {
			this.getSession().setAttribute(ConfigUtil.SESSION_USER_KEY, user);
			
			Friend friend = Friend.dao.findById(user.get("UserId"));
			friend.set("FriendStatus", FriendStatus.Normal.ordinal()).update();//状态为已登录
			Message message = new Message().set("MessageType", MessageType.FriendStatus.ordinal())
					.set("MessageStatus", MessageStatus.UnRead.ordinal())
					.set("MessageContent",friend.getStr("FriendAccount") + ":" + friend.get("FriendStatus"))
					.set("MessageTime", new Date())
					.set("UserId", friend.get("FriendId"));
			message.save();
			
			result.setSuccess(true);
			result.setResult(user);
		} else if (user == null) {
			result.setSuccess(false);
			result.setResult("账号不存在");
		} else {
			result.setSuccess(false);
			result.setResult("密码错误");
		}
		this.renderJson(result);
	}

	
	/**
	 * 用户注册 for json
	 * */
	public void add(){
		int sex = this.getParaToInt("sex");
		String pwd1 = this.getPara("password1");
		String pwd2 = this.getPara("password2");
		String note = this.getPara("note");
		String nickName = this.getPara("nickName");
		String icon = this.getPara("icon");
		String email = this.getPara("email");

		if (!pwd1.equals(pwd2)) {
			this.renderJson(ResourceMessage.error("两次输入的密码不相同"));
		} else if (pwd1.length() < 6 || pwd1.length() > 16 || pwd2.length() < 6
				|| pwd2.length() > 16) {
			this.renderJson(ResourceMessage.error("密码长度应为6~16位"));
		} else if (User.dao.findFirst("select * from User where UserEmail = ?",
				email) != null) {
			this.renderJson(ResourceMessage.error("邮箱已被使用，请使用其他邮箱"));
		} else {
			String account = new AccountUtil().getAccount();
			while (User.dao.findFirst(
					"select * from User where UserAccount = ?", account) != null) {
				account = new AccountUtil().getAccount();
			}
			String password = MD5Util.getMD5String(pwd1);
			if (nickName == null)
				nickName = "未知";
			final User user = new User()
					.set("UserAccount", account)
					.set("Password", password)
					.set("UserNote", note)
					.set("NickName", nickName)
					.set("UserStatus", UserStatus.ACTIVE.ordinal())
					.set("UserRegisterTime",
							new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
									.format(new Date())).set("UserIcon", icon)
					.set("UserEmail", email).set("Sex", sex);
			boolean result = user.save();
			new Friend().set("FriendAccount", account).set("FriendIcon", icon)
					.set("FriendNote", note).set("FriendNickName", nickName)
					.set("FriendId", user.get("UserId")).set("FriendSex", sex)
					.set("FriendStatus", FriendStatus.UnderLine.ordinal())
					.save();

			if (result) {
				// 为该用户创建默认两个好友分组，和发送一条消息
				int userId = user.getInt("UserId");
				new JGroup().set("UserId", userId).set("GroupName", "我的好友")
						.save();
				new JGroup().set("UserId", userId).set("GroupName", "黑名单")
						.save();
				new Message().set("FriendId", userId)
						.set("MessageContent", "欢迎加入JJIM即时通信系统")
						.set("MessageTime", new Date())
						.set("MessageStatus", MessageStatus.UnRead.ordinal())
						.set("MessageType", MessageType.System.ordinal())
						.save();
		
				this.renderJson(ResourceMessage.success(user));
			} else {
				this.renderJson(ResourceMessage.error("注册失败，请重试"));
			}
		}
	}

	/**
	 * 用户登录
	 * */
 	public void login() {
		String account = this.getPara("account");
		String password = this.getPara("password");
		User user = User.dao.findFirst(
				"select * from User where UserAccount = ?", account);
		if (user != null
				&& MD5Util.getMD5String(password).equals(
						user.getStr("Password"))) {
			this.setAttr("user", user);
			this.getSession().setAttribute(ConfigUtil.SESSION_USER_KEY, user);
			this.renderJsp("/WEB-INF/jsp/UserInfo.jsp");
			return;
		} else if (user == null) {
			this.setAttr("message", "账号不存在");
		} else {
			this.setAttr("message", "密码错误");
		}
		this.renderJsp("/index.jsp");
	}

 	
	/**
	 * 访问系统首页
	 * */
	@ActionKey("/")
	public void appIndex() {
		this.renderJsp("/index.jsp");
	}

	/**
	 * 注册用户
	 * */
	public void register() {
		int sex = this.getParaToInt("sex");
		String pwd1 = this.getPara("password1");
		String pwd2 = this.getPara("password2");
		String note = this.getPara("note");
		String nickName = this.getPara("nickName");
		String icon = this.getPara("icon");
		String email = this.getPara("email");

		if (!pwd1.equals(pwd2)) {
			this.setAttr("message", "两次输入的密码不相同");
			this.renderJsp("/Register.jsp");

		} else if (pwd1.length() < 6 || pwd1.length() > 16 || pwd2.length() < 6
				|| pwd2.length() > 16) {
			this.setAttr("message", "密码长度应为6~16位");
			this.renderJsp("/Register.jsp");

		} else if (User.dao.findFirst("select * from User where UserEmail = ?",
				email) != null) {
			this.setAttr("message", "邮箱已被使用，请使用其他邮箱");
			this.renderJsp("/Register.jsp");
		} else {
			String account = new AccountUtil().getAccount();
			while (User.dao.findFirst(
					"select * from User where UserAccount = ?", account) != null) {
				account = new AccountUtil().getAccount();
			}
			String password = MD5Util.getMD5String(pwd1);
			if (nickName == null)
				nickName = "未知";
			final User user = new User()
					.set("UserAccount", account)
					.set("Password", password)
					.set("UserNote", note)
					.set("NickName", nickName)
					.set("UserStatus", UserStatus.ACTIVE.ordinal())
					.set("UserRegisterTime",
							new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
									.format(new Date())).set("UserIcon", icon)
					.set("UserEmail", email).set("Sex", sex);
			boolean result = user.save();
			new Friend().set("FriendAccount", account).set("FriendIcon", icon)
					.set("FriendNote", note).set("FriendNickName", nickName)
					.set("FriendId", user.get("UserId")).set("FriendSex", sex)
					.set("FriendStatus", FriendStatus.UnderLine.ordinal())
					.save();

			if (result) {
				// 为该用户创建默认两个好友分组，和发送一条消息
				int userId = user.getInt("UserId");
				new JGroup().set("UserId", userId).set("GroupName", "我的好友")
						.save();
				new JGroup().set("UserId", userId).set("GroupName", "黑名单")
						.save();
				new Message().set("FriendId", userId)
						.set("MessageContent", "欢迎加入JJIM即时通信系统")
						.set("MessageTime", new Date())
						.set("MessageStatus", MessageStatus.UnRead.ordinal())
						.set("MessageType", MessageType.System.ordinal())
						.save();
				// new Thread(
				// new Runnable(){
				//
				// @Override
				// public void run() {
				// // 为该用户创建默认两个好友分组，和发送一条消息
				// String userId = user.getStr("UserId");
				// new JGroup().set("UserId", userId).set("GroupName",
				// "我的好友").save();
				// new JGroup().set("UserId", userId).set("GroupName",
				// "黑名单").save();
				// new Message().set("FriendId",userId).set("MessageContent",
				// "欢迎加入JJIM即时通信系统")
				// .set("MessageTime", new Date()).set("MessageStatus",
				// MessageStatus.UnRead.ordinal())
				// .set("MessageType", MessageType.System).save();
				// }
				//
				// }).start();

				this.setAttr("message", "注册成功，您的账号为：" + account + ",请妥善保管");
				this.setAttr("user", user);
				this.renderJsp("/WEB-INF/jsp/UserInfo.jsp");
			} else {
				this.setAttr("message", "注册失败，请重试");
				this.setAttr("nickName", nickName);
				this.setAttr("note", note);
				this.setAttr("sex", sex);
				this.setAttr("email", email);
				this.renderJsp("/Register.jsp");
			}
		}
	}
	public void searchFriend() {
		List<Friend> friends = Friend.dao.find("select * from Friend");
		this.setAttr("myFriends", friends);
		this.renderJsp("/WEB-INF/jsp/SearchFriend.jsp");
	}
}
