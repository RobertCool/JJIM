/**
 * 
 */
package cn.edu.scau.robert.Interceptor;

import javax.servlet.http.HttpSession;

import cn.edu.scau.robert.model.User;
import cn.edu.scau.robert.util.ConfigUtil;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

/**
 * @author robert
 *用于拦截用户请求，查看用户是否已经登录
 */
public class LoginInterceptor implements Interceptor {

	/* (non-Javadoc)
	 * @see com.jfinal.aop.Interceptor#intercept(com.jfinal.core.ActionInvocation)
	 */
	@Override
	public void intercept(ActionInvocation ai) {
		HttpSession session = ai.getController().getSession();
		User user = (User)session.getAttribute(ConfigUtil.SESSION_USER_KEY);
		if(user == null){
			ai.getController().setAttr("message", "请先登录");
			ai.getController().renderJsp("/index.jsp");
		}else{
			ai.invoke();
		}
	}

}
