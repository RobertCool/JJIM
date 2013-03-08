/**
 * 
 */
package cn.edu.scau.hci.robert.service;

import org.apache.commons.httpclient.NameValuePair;

import cn.edu.scau.hci.robert.entity.Crowd;
import cn.edu.scau.hci.robert.util.HttpUtil;
import android.os.Message;

/**
 * @author robert
 *
 */
public class CrowdService {

	/**获取自己加入的群的列表*/
	public boolean getMyCrowd(Message msg){
		try{
			String url = HttpUtil.host + "/crowd";
			HttpUtil.get(url, msg);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**查看所有的群*/
	public boolean getCrowdList(int page, Message msg){
		try{
			String url = HttpUtil.host + "/crowd?page=" + page;
			HttpUtil.get(url, msg);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**查看指定群的信息*/
	public boolean getCrowd(Long crowdId, Message msg){
		try{
			String url = HttpUtil.host + "/crowd/" + crowdId;
			HttpUtil.get(url, msg);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**创建群*/
	public boolean createCrowd(Crowd crowd, Message msg){
		try{
			String url = HttpUtil.host + "/crowd";
			NameValuePair[] params = new NameValuePair[3];
			params[0] = new NameValuePair("CrowdName", crowd.getCrowdName());
			params[1] = new NameValuePair("CrowdNote", crowd.getCrowdNote());
			params[2] = new NameValuePair("CrowdIcon", crowd.getCrowdNote());
			HttpUtil.post(url, msg, params);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
