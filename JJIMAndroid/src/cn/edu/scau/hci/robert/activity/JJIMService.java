/**
 * 
 */
package cn.edu.scau.hci.robert.activity;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONObject;

import cn.edu.scau.hci.robert.common.Action;
import cn.edu.scau.hci.robert.common.ImCache;
import cn.edu.scau.hci.robert.entity.Message;
import cn.edu.scau.hci.robert.util.HttpUtil;
import cn.edu.scau.hci.robert.util.Logger;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author robert
 *	负责网络连接部分，并实时更新前台Activity
 */
public class JJIMService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO 提示离线,并且进行离线操作
		super.onDestroy();
		isStop = true;
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(msgThread == null){
			msgThread = new Thread(new MessageRunnable());
			msgThread.start();
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	private Thread msgThread = null;
	private boolean isStop = false;

	class MessageRunnable  implements Runnable{
		
		HttpClient client; 
		
		public MessageRunnable(){
			client = new HttpClient();
			client.getState().addCookies(HttpUtil.getInstance().getState().getCookies());	
		}

		public void run() {
			while(!isStop){
				GetMethod getMethod = new GetMethod(HttpUtil.host + "/message");
				try {
					if(HttpStatus.SC_OK == client.executeMethod(getMethod)){
						JSONObject jsObj = new JSONObject(getMethod.getResponseBodyAsString());
						if(jsObj.getBoolean("success")){
							JSONArray jsArray = jsObj.getJSONObject("result").getJSONArray("list");
							//消息还未处理，等待
							while(ImCache.unreadMsgs.size()!=0){
								Thread.sleep(1000);
							}
							
							for(int i = 0; i < jsArray.length(); i++){
								Logger.i("message", jsArray.getString(i));
								Message msg = Message.toMessage(jsArray.getString(i));
								ImCache.unreadMsgs.add(msg);
//								List<Message> msgs;
//								if((msgs=ImCache.messages.get(msg.getFriendId())) == null){
//									msgs = new ArrayList<Message>();
//									ImCache.messages.put(msg.getUserId(), msgs);
//								}else{
//									msgs.add(msg);
//								}
						
							}
							
							if(jsArray.length() > 0){
								Intent intent = new Intent(Action.JJIMService_New_Message);
								JJIMService.this.sendBroadcast(intent);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					getMethod.releaseConnection();
				}
				
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
