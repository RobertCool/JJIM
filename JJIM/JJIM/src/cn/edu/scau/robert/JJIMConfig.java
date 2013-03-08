/**
 * 
 */
package cn.edu.scau.robert;

import cn.edu.scau.robert.controller.CommonController;
import cn.edu.scau.robert.controller.CrowdController;
import cn.edu.scau.robert.controller.FriendController;
import cn.edu.scau.robert.controller.GroupController;
import cn.edu.scau.robert.controller.ImageController;
import cn.edu.scau.robert.controller.MessageController;
import cn.edu.scau.robert.controller.UserController;
import cn.edu.scau.robert.model.Crowd;
import cn.edu.scau.robert.model.CrowdFriend;
import cn.edu.scau.robert.model.Friend;
import cn.edu.scau.robert.model.GroupFriend;
import cn.edu.scau.robert.model.Image;
import cn.edu.scau.robert.model.JGroup;
import cn.edu.scau.robert.model.Message;
import cn.edu.scau.robert.model.User;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

/**
 * @author robert
 * 
 */
public class JJIMConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configInterceptor(Interceptors me) {
		//me.add(new Restful());
		//me.add(new LoginInterceptor());
	}

	@Override
	public void configPlugin(Plugins me) {
		C3p0Plugin c3p0Plugin = new C3p0Plugin("jdbc:mysql://localhost/jjim","root", "ming");
		me.add(c3p0Plugin);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		
		arp.addMapping("User","UserId", User.class);
		arp.addMapping("Crowd", "CrowdId",Crowd.class);
		arp.addMapping("Friend","FriendId", Friend.class);
		arp.addMapping("Message","MessageId", Message.class);
		arp.addMapping("JGroup","GroupId", JGroup.class);
		arp.addMapping("Image", "ImageId", Image.class);
		arp.addMapping("Crowd_Friend", "CrowdId", CrowdFriend.class);
		arp.addMapping("Group_Friend", "GroupId", GroupFriend.class);
	}

	@Override
	public void configRoute(Routes me) {
		//网页的功能，非WebService的内容
		me.add("/common",CommonController.class);
		//用户模块
		me.add("/user", UserController.class);
		//文件上传模块
		me.add("/image", ImageController.class);
		//好友模块
		me.add("/friend", FriendController.class);
		//群模块
		me.add("/crowd", CrowdController.class);
		//好友分组模块
		me.add("/group", GroupController.class);
		//消息模块
		me.add("/message", MessageController.class);
	}

}
